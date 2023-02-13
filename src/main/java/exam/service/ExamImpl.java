package exam.service;

import exam.dao.Dao;
import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;
import exam.dto.mapper.Mapper;
import exam.exception.ManufacturedNotFoundException;
import exam.exception.SouvenirNotFoundException;
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
        manufacturersMap = (HashMap<Long, Manufacturer>) dao.readManufacturers();
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
        var removed = getManufacturerById(id);
        removed.getSouvenirs().forEach(souvenir -> souvenirsMap.remove(souvenir.getId()));
        dao.saveManufactures(manufacturersMap.values());
    }

    @Override
    public void removeSouvenir(Long id) {
        var removed = getSouvenirById(id);
        manufacturersMap.get(id).removeSouvenir(removed);
        dao.saveManufactures(manufacturersMap.values());
    }

    @Override
    public void updateSouvenir(SouvenirDto souvenir) {
        var updated = getSouvenirById(souvenir.id());
        updated.setDate(souvenir.date());
        updated.setName(souvenir.name());
        updated.setPrice(souvenir.price());
        dao.saveManufactures(manufacturersMap.values());
    }

    @Override
    public void updateManufacturer(ManufacturerDto manufacturer) {
        var updated = getManufacturerById(manufacturer.id());
        updated.setCountry(manufacturer.country());
        updated.setName(manufacturer.name());
        dao.saveManufactures(manufacturersMap.values());
    }

    @Override
    public ManufacturerFullDto addSouvenir(Long id, SouvenirDto dto) {
        var souvenir = dto.toSouvenir();
        souvenir.setId(generateId(souvenirsMap.keySet()));
        var manufacturer = getManufacturerById(id);
        manufacturer.addSouvenir(souvenir);
        souvenirsMap.put(souvenir.getId(), souvenir);
        dao.saveManufactures(manufacturersMap.values());
        return mapper.toManufacturerFullDto(manufacturer);
    }

    @Override
    public ManufacturerFullDto addManufacturer(ManufacturerDto dto) {
        var manufacturer = dto.toManufacturer();
        manufacturer.setId(generateId(manufacturersMap.keySet()));
        manufacturersMap.put(manufacturer.getId(), manufacturer);
        dao.saveManufactures(manufacturersMap.values());
        return mapper.toManufacturerFullDto(manufacturer);
    }

    @Override
    public List<SouvenirDto> getSouvenirsByManufacturerId(Long manufacturerId) {
        return getManufacturerById(manufacturerId).getSouvenirs().stream().map(mapper::toSouvenirDto).toList();
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
    public List<ManufacturerDto> getManufacturersBySouvenirNameAndYear(String name, LocalDate date) {
        return souvenirsMap.values().stream()
                .filter(souvenir -> souvenir.getName().equals(name) && souvenir.getDate().getYear() == date.getYear())
                .map(souvenir -> mapper.toManufacturerDto(souvenir.getManufacturer())).toList();
    }

    @Override
    public Map<Integer, List<SouvenirFullDto>> getSouvenirsByYear() {
        var map = new HashMap<Integer, List<SouvenirFullDto>>();
        for (var s : souvenirsMap.values()) {
            var list = map.get(s.getDate().getYear());
            if (list != null) list.add(mapper.toSouvenirFullDto(s));
            else map.put(s.getDate().getYear(), new ArrayList<>(List.of(mapper.toSouvenirFullDto(s))));
        }
        return map;
    }

    private Manufacturer getManufacturerById(Long id) {
        var manufacturer = manufacturersMap.get(id);
        if (manufacturer != null) return manufacturer;
        else throw new ManufacturedNotFoundException(id);
    }

    private Souvenir getSouvenirById(Long id) {
        var souvenir = souvenirsMap.get(id);
        if (souvenir != null) return souvenir;
        else throw new SouvenirNotFoundException(id);
    }


    private Long generateId(Set<Long> ids) {
        return ids.stream().max(Long::compare).map(id -> id + 1).orElse(0L);
    }
}
