package exam.service;

import exam.dao.Dao;
import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;
import exam.dto.mapper.Mapper;
import exam.dto.mapper.MapperImpl;
import exam.model.Manufacturer;
import exam.model.Souvenir;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamImpl implements Exam {
    private final HashMap<Long, Manufacturer> manufacturersMap;
    private final HashMap<Long, Souvenir> souvenirsMap;

    private final Dao dao;
    private final Mapper mapper;

    public ExamImpl(Dao dao, Mapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
        manufacturersMap = (HashMap<Long, Manufacturer>) dao.getManufacturers();
        souvenirsMap = new HashMap<>();
        manufacturersMap.values().forEach(manufacturer ->
                souvenirsMap.putAll(manufacturer.getSouvenirs().stream().collect(Collectors.toMap(Souvenir::getId, souvenir -> souvenir))));
    }

    @Override
    public List<ManufacturerDto> getManufactures() {
        return manufacturersMap.values().stream().map(mapper::toManufacturerDto).toList();
    }

    @Override
    public List<SouvenirFullDto> getSouvenirs() {
        return souvenirsMap.values().stream().map(mapper::toSouvenirFullDto).toList();
    }

    @Override
    public void removeManufacturer(Long id) {
        var removed = manufacturersMap.remove(id);
        if (removed == null) throw new RuntimeException();
        else{
            removed.getSouvenirs().forEach(souvenir -> souvenirsMap.remove(souvenir.getId()));
            dao.saveManufactures(manufacturersMap.values());
        }
    }

    @Override
    public void removeSouvenir(Long id) {
        var removed = souvenirsMap.remove(id);
        if (removed == null) throw new RuntimeException();
        else {
            manufacturersMap.get(id).removeSouvenir(removed);
            dao.saveManufactures(manufacturersMap.values());
        }
    }

    @Override
    public void updateSouvenir(SouvenirDto souvenir) {
        var updated = souvenirsMap.get(souvenir.id());
        if (updated == null) throw new RuntimeException();
        else {
            updated.setDate(souvenir.date());
            updated.setName(souvenir.name());
            updated.setPrice(souvenir.price());
            dao.saveManufactures(manufacturersMap.values());
        }
    }

    @Override
    public void updateManufacturer(ManufacturerDto manufacturer) {
        var updated = manufacturersMap.get(manufacturer.id());
        if (updated == null) throw new RuntimeException();
        else {
            updated.setCountry(manufacturer.country());
            updated.setName(manufacturer.name());
            dao.saveManufactures(manufacturersMap.values());
        }
    }

    @Override
    public void addSouvenir(Long id, SouvenirDto dto) {
        var souvenir = dto.toSouvenir();
        souvenir.setId(generateId(souvenirsMap.keySet()));
        var manufacturer = manufacturersMap.get(id);
        if (manufacturer == null) throw new RuntimeException();
        else {
            souvenir.setId(generateId(souvenirsMap.keySet()));
            manufacturer.addSouvenir(souvenir);
            souvenirsMap.put(souvenir.getId(), souvenir);
            dao.saveManufactures(manufacturersMap.values());
        }
    }

    @Override
    public void addManufacturer(ManufacturerDto dto) {
        var manufacturer = dto.toManufacturer();
        manufacturer.setId(generateId(manufacturersMap.keySet()));
        manufacturersMap.put(manufacturer.getId(), manufacturer);
        dao.saveManufactures(manufacturersMap.values());
    }

    @Override
    public List<SouvenirDto> getSouvenirsByManufacturerId(Long manufacturerId) {
        var manufacturer = manufacturersMap.get(manufacturerId);
        if (manufacturer == null) throw new RuntimeException();
        else return manufacturer.getSouvenirs().stream().map(mapper::toSouvenirDto).toList();
    }

    @Override
    public List<SouvenirFullDto> getSouvenirsByCountry(String country) {
        return souvenirsMap.values().stream().filter(souvenir -> souvenir.getManufacturer().getCountry().equals(country))
                .map(mapper::toSouvenirFullDto).toList();
    }

    @Override
    public List<ManufacturerFullDto> getManufacturerWithSouvenirsCheaperThatPrice(double price) {
        return manufacturersMap.values().stream()
                .filter(manufacturer -> manufacturer.makesSouvenirsCheaperThanValue(price))
                .map(mapper::toManufacturerFullDto).toList();
    }

    @Override
    public List<ManufacturerDto> getManufacturersBySouvenirNameThatWasMadeThisYear(String name) {
        return souvenirsMap.values().stream()
                .filter(souvenir -> souvenir.getName().equals(name) && souvenir.getDate().getYear() == LocalDate.now().getYear())
                .map(souvenir -> mapper.toManufacturerDto(souvenir.getManufacturer())).toList();
    }

    @Override
    public Map<LocalDate, List<SouvenirFullDto>> getSouvenirsByYear() {
        var map = new HashMap<LocalDate, List<SouvenirFullDto>>();
        for(var s : souvenirsMap.values()){
            var list = map.get(s.getDate());
            if(list != null) list.add(mapper.toSouvenirFullDto(s));
            else map.put(s.getDate(), new ArrayList<>(List.of(mapper.toSouvenirFullDto(s))));
        }
        return map;
    }


    private Long generateId(Set<Long> ids) {
        return ids.stream().max(Long::compare).map(id -> id + 1).orElse(0L);
    }
}
