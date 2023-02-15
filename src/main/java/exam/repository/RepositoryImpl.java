package exam.repository;

import exam.dto.SouvenirDto;
import exam.exception.ManufacturedNotFoundException;
import exam.exception.SouvenirNotFoundException;
import exam.model.Souvenir;
import exam.model.Manufacturer;
import exam.repository.dao.Dao;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RepositoryImpl implements Repository {

    private final Dao dao;
    private final Map<Long, Manufacturer> manufacturers;
    private final Map<Long, Souvenir> souvenirs;

    public RepositoryImpl(Dao dao) {
        this.dao = dao;
        manufacturers = new HashMap<>();
        souvenirs = new HashMap<>();
        dao.readAll(manufacturers, souvenirs);
    }

    @Override
    public List<Manufacturer> getManufacturers() {
        return manufacturers.values().stream().toList();
    }

    @Override
    public List<Souvenir> getSouvenirs() {
        return souvenirs.values().stream().toList();
    }

    @Override
    public List<Souvenir> getManufacturersSouvenirs(Long id) {
        return getManufacturerById(id).getSouvenirs();
    }

    @Override
    public void removeManufacturer(Long id) {
        var removed = manufacturers.remove(id);
        if (removed != null) {
            removed.getSouvenirs().forEach(souvenir -> souvenirs.remove(souvenir.getId()));
            dao.removeManufacturer(removed);
        } else throw new ManufacturedNotFoundException(id);
    }

    @Override
    public void removeSouvenir(Long id) {
        var removed = souvenirs.remove(id);
        if (removed != null) {
            var manufacturer = removed.getManufacturer();
            manufacturer.removeSouvenir(removed);
            dao.saveManufacturer(manufacturer);
        } else throw new SouvenirNotFoundException(id);
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        var updated = getManufacturerById(manufacturer.getId());
        updated.setCountry(manufacturer.getCountry());
        updated.setName(manufacturer.getName());
        dao.saveManufacturer(updated);
        return updated;
    }

    @Override
    public Souvenir updateSouvenir(Souvenir souvenir) {
        var updated = getSouvenirById(souvenir.getId());
        updated.setDate(souvenir.getDate());
        updated.setName(souvenir.getName());
        updated.setPrice(souvenir.getPrice());
        dao.saveManufacturer(updated.getManufacturer());
        return updated;
    }

    @Override
    public Manufacturer addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(generateId(manufacturers.keySet()));
        manufacturers.put(manufacturer.getId(), manufacturer);
        dao.saveManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Souvenir addSouvenir(Long id, Souvenir souvenir) {
        souvenir.setId(generateId(souvenirs.keySet()));
        var manufacturer = getManufacturerById(id);
        manufacturer.addSouvenir(souvenir);
        souvenirs.put(souvenir.getId(), souvenir);
        dao.saveManufacturer(manufacturer);
        return souvenir;
    }

    @Override
    public Manufacturer getManufacturerById(Long id) {
        var manufacturer = manufacturers.get(id);
        if (manufacturer != null) return manufacturer;
        else throw new ManufacturedNotFoundException(id);
    }

    @Override
    public Souvenir getSouvenirById(Long id) {
        var souvenir = souvenirs.get(id);
        if (souvenir != null) return souvenir;
        else throw new SouvenirNotFoundException(id);
    }

    @Override
    public List<Souvenir> getSouvenirsBySouvenirNameAndYear(String name, int year) {
        return souvenirs.values().stream()
                .filter(souvenir ->souvenir.getName().equals(name) && souvenir.getDate().getYear() == year).toList();
    }

    @Override
    public List<Souvenir> getSouvenirsByCountry(String country) {
        return souvenirs.values().stream().filter(souvenir -> souvenir.getManufacturer().getCountry().equals(country)).toList();
    }

    @Override
    public List<Manufacturer> getManufacturersThatMakesSouvenirsCheaperThenValue(double price) {
        return manufacturers.values().stream().filter(manufacturer -> manufacturer.isMakesSouvenirsCheaperThanValue(price)).toList();
    }

    @Override
    public Map<Integer, List<Souvenir>> getSouvenirsByYears() {
        var map = new TreeMap<Integer, List<Souvenir>>();
        souvenirs.values().forEach(souvenir ->  map.computeIfAbsent(souvenir.getDate().getYear(), ArrayList::new).add(souvenir));
        return map;
    }


    private Long generateId(Set<Long> ids) {
        return ids.stream().max(Long::compare).map(id -> id + 1).orElse(0L);
    }
}
