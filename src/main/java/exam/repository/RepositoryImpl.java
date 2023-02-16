package exam.repository;

import exam.exception.ManufacturedNotFoundException;
import exam.exception.SouvenirNotFoundException;
import exam.model.Souvenir;
import exam.model.Manufacturer;
import exam.repository.filehandler.FileHandler;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RepositoryImpl implements Repository {

    private final FileHandler fileHandler;
    private final Map<Long, Manufacturer> manufacturers;
    private final Map<Long, Souvenir> souvenirs;

    public RepositoryImpl(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        manufacturers = fileHandler.readAll();
        souvenirs = manufacturers.values().stream()
                .flatMap(manufacturer -> manufacturer.getSouvenirs().stream())
                .collect(Collectors.toMap(Souvenir::getId, Function.identity()));
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
    public void removeManufacturer(long id) {
        var removed = manufacturers.remove(id);
        if (removed != null) {
            removed.getSouvenirs().forEach(souvenir -> souvenirs.remove(souvenir.getId()));
            fileHandler.removeManufacturer(removed);
        } else throw new ManufacturedNotFoundException(id);
    }

    @Override
    public void removeSouvenir(long id) {
        var removed = souvenirs.remove(id);
        if (removed != null) {
            var manufacturer = removed.getManufacturer();
            manufacturer.removeSouvenir(removed);
            fileHandler.saveManufacturer(manufacturer);
        } else throw new SouvenirNotFoundException(id);
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        var updated = getManufacturerById(manufacturer.getId());
        updated.setCountry(manufacturer.getCountry());
        updated.setName(manufacturer.getName());
        fileHandler.saveManufacturer(updated);
        return updated;
    }

    @Override
    public Souvenir updateSouvenir(Souvenir souvenir) {
        var updated = getSouvenirById(souvenir.getId());
        updated.setDate(souvenir.getDate());
        updated.setName(souvenir.getName());
        updated.setPrice(souvenir.getPrice());
        fileHandler.saveManufacturer(updated.getManufacturer());
        return updated;
    }

    @Override
    public Manufacturer addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(generateId(manufacturers.keySet()));
        manufacturers.put(manufacturer.getId(), manufacturer);
        fileHandler.saveManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Souvenir addSouvenir(long id, Souvenir souvenir) {
        souvenir.setId(generateId(souvenirs.keySet()));
        var manufacturer = getManufacturerById(id);
        manufacturer.addSouvenir(souvenir);
        souvenirs.put(souvenir.getId(), souvenir);
        fileHandler.saveManufacturer(manufacturer);
        return souvenir;
    }

    @Override
    public Manufacturer getManufacturerById(long id) {
        var manufacturer = manufacturers.get(id);
        if (manufacturer != null) return manufacturer;
        else throw new ManufacturedNotFoundException(id);
    }

    @Override
    public Souvenir getSouvenirById(long id) {
        var souvenir = souvenirs.get(id);
        if (souvenir != null) return souvenir;
        else throw new SouvenirNotFoundException(id);
    }

    @Override
    public List<Manufacturer> getManufacturersBySouvenirNameAndYear(String name, int year) {
        return souvenirs.values().stream()
                .filter(souvenir ->souvenir.getName().equals(name) && souvenir.getDate().getYear() == year)
                .map(Souvenir::getManufacturer).distinct().toList();
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
