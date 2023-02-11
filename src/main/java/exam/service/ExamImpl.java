package exam.service;

import exam.dao.Dao;
import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExamImpl implements Exam {
    private final HashMap<Long, Manufacturer> manufacturersMap;
    private final HashMap<Long, Souvenir> souvenirsMap;

    private final Dao dao;

    public ExamImpl(Dao dao) {
        this.dao = dao;
        manufacturersMap = (HashMap<Long, Manufacturer>) dao.getManufacturers();
        souvenirsMap = (HashMap<Long, Souvenir>) dao.getSouvenirs();
    }

    @Override
    public List<Manufacturer> getManufactures() {
        return manufacturersMap.values().stream().toList();
    }

    @Override
    public List<Souvenir> getSouvenirsByManufacturerId() {
        return souvenirsMap.values().stream().toList();
    }

    @Override
    public void removeManufacturer(Long id) {
        var removed = manufacturersMap.remove(id);
        if (removed == null) throw new RuntimeException();
        else{
            removed.getSouvenirs().forEach(souvenir -> souvenirsMap.remove(souvenir.getId()));
            dao.saveManufactures(manufacturersMap.values());
            dao.saveSouvenirs(souvenirsMap.values());
        }
    }

    @Override
    public void removeSouvenir(Long id) {
        var removed = souvenirsMap.remove(id);
        if (removed == null) throw new RuntimeException();
        else {
            manufacturersMap.get(id).removeSouvenir(removed);
            dao.saveSouvenirs(souvenirsMap.values());
        }
    }

    @Override
    public Souvenir updateSouvenir(Souvenir souvenir) {
        var updated = souvenirsMap.get(souvenir.getId());
        if (updated == null) throw new RuntimeException();
        else {
            updated.setDate(souvenir.getDate());
            updated.setName(souvenir.getName());
            updated.setPrice(souvenir.getPrice());
            dao.saveSouvenirs(souvenirsMap.values());
            return updated;
        }
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        var updated = manufacturersMap.get(manufacturer.getId());
        if (updated == null) throw new RuntimeException();
        else {
            updated.setCountry(manufacturer.getCountry());
            updated.setName(manufacturer.getName());
            dao.saveManufactures(manufacturersMap.values());
            return updated;
        }
    }

    @Override
    public void addSouvenir(Long id, Souvenir souvenir) {
        var manufacturer = manufacturersMap.get(id);
        if (manufacturer == null) throw new RuntimeException();
        else {
            souvenir.setId(generateId(souvenirsMap.keySet()));
            manufacturer.addSouvenir(souvenir);
            souvenirsMap.put(souvenir.getId(), souvenir);
        }
    }

    @Override
    public void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(generateId(manufacturersMap.keySet()));
        manufacturersMap.put(manufacturer.getId(), manufacturer);
    }

    @Override
    public List<Souvenir> getSouvenirsByManufacturerId(Long manufacturerId) {
        var manufacturer = manufacturersMap.get(manufacturerId);
        if (manufacturer == null) throw new RuntimeException();
        else return manufacturer.getSouvenirs();
    }

    @Override
    public List<Souvenir> getSouvenirsByCountry(String country) {
        return souvenirsMap.values().stream().filter(souvenir -> souvenir.getManufacturer().getCountry().equals(country)).toList();
    }

    @Override
    public List<Manufacturer> getManufacturerWithSouvenirsCheaperThatPrice(double price) {
        return manufacturersMap.values().stream()
                .filter(manufacturer -> manufacturer.makesSouvenirsCheaperThanValue(price)).toList();
    }

    @Override
    public List<Manufacturer> getManufacturersBySouvenirNameThatWasMadeThisYear(String name) {
        return souvenirsMap.values().stream()
                .filter(souvenir -> souvenir.getName().equals(name) && souvenir.getDate().getYear() == LocalDate.now().getYear())
                .map(Souvenir::getManufacturer).toList();
    }

    @Override
    public Map<LocalDate, Souvenir> getSouvenirsByYear(LocalDate date) {
        return souvenirsMap.values().stream().filter(souvenir -> souvenir.getDate().getYear() == date.getYear())
                .collect(Collectors.toMap(Souvenir::getDate, souvenir -> souvenir));
    }


    private Long generateId(Set<Long> ids) {
        return ids.stream().max(Long::compare).map(id -> id + 1).orElse(0L);
    }
}
