package exam.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exam.exception.ManufacturedNotFoundException;
import exam.exception.SouvenirNotFoundException;
import exam.model.Souvenir;
import lombok.SneakyThrows;
import exam.model.Manufacturer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DaoImpl implements Dao {
    private final String FILE_NAME;
    private final ObjectMapper mapper;

    private Map<Long, Manufacturer> manufacturers;

    private Map<Long, Souvenir> souvenirs;

    public DaoImpl(@Value("${file.name}") String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        manufacturers = readManufacturers();
        souvenirs = new HashMap<>();
        manufacturers.values().forEach(manufacturer ->
                souvenirs.putAll(manufacturer.getSouvenirs().stream().collect(Collectors.toMap(Souvenir::getId, souvenir -> souvenir))));
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
        if(removed != null) {
            removed.getSouvenirs().forEach(souvenir -> souvenirs.remove(souvenir.getId()));
            saveManufacturers(manufacturers.values());
        } else throw new ManufacturedNotFoundException(id);
    }

    @Override
    public void removeSouvenir(Long id) {
        var removed = souvenirs.remove(id);
        if(removed != null) {
            getManufacturerById(id).removeSouvenir(removed);
            saveManufacturers(manufacturers.values());
        } else throw new SouvenirNotFoundException(id);
    }

    @Override
    public void updateManufacturer(Manufacturer manufacturer) {
        var updated = getManufacturerById(manufacturer.getId());
        updated.setCountry(manufacturer.getCountry());
        updated.setName(manufacturer.getName());
        saveManufacturers(manufacturers.values());
    }

    @Override
    public void updateSouvenir(Souvenir souvenir) {
        var updated = getSouvenirById(souvenir.getId());
        updated.setDate(souvenir.getDate());
        updated.setName(souvenir.getName());
        updated.setPrice(souvenir.getPrice());
        saveManufacturers(manufacturers.values());
    }

    @Override
    public Manufacturer addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(generateId(manufacturers.keySet()));
        manufacturers.put(manufacturer.getId(), manufacturer);
        saveManufacturers(manufacturers.values());
        return manufacturer;
    }

    @Override
    public Manufacturer addSouvenir(Long id, Souvenir souvenir) {
        souvenir.setId(generateId(souvenirs.keySet()));
        var manufacturer = getManufacturerById(id);
        manufacturer.addSouvenir(souvenir);
        souvenirs.put(souvenir.getId(), souvenir);
        saveManufacturers(manufacturers.values());
        return manufacturer;
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
    private Long generateId(Set<Long> ids) {
        return ids.stream().max(Long::compare).map(id -> id + 1).orElse(0L);
    }
    private Map<Long, Manufacturer> readManufacturers() {
        manufacturers = new HashMap<>();
        try (var reader = new BufferedReader(new FileReader(FILE_NAME))) {
            var line = "";
            while ((line = reader.readLine()) != null) {
                var manufacturer = mapper.readValue(line, Manufacturer.class);
                manufacturers.put(manufacturer.getId(), manufacturer);
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return manufacturers;
    }


    @SneakyThrows
    private void saveManufacturers(Collection<Manufacturer> manufacturers) {
        try (var writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (var manufacturer : manufacturers) writer.append(mapper.writeValueAsString(manufacturer)).append("\n");
        }
    }
}
