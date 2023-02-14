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

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DaoImpl implements Dao {
    private final File storage;
    private final ObjectMapper mapper;
    private final Map<Long, Manufacturer> manufacturers;
    private final Map<Long, Souvenir> souvenirs;

    public DaoImpl(@Value("${storage.folder.name}") String FILE_FOLDER_NAME) {
        this.storage = new File(FILE_FOLDER_NAME);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        manufacturers = new HashMap<>();
        souvenirs = new HashMap<>();
        readDataFromStorage();
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
            new File(storage + File.separator + removed.getId()).delete();
        } else throw new ManufacturedNotFoundException(id);
    }

    @Override
    public void removeSouvenir(Long id) {
        var removed = souvenirs.remove(id);
        if (removed != null) {
            var manufacturer = removed.getManufacturer();
            manufacturer.removeSouvenir(removed);
            saveManufacturer(manufacturer);
        } else throw new SouvenirNotFoundException(id);
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        var updated = getManufacturerById(manufacturer.getId());
        updated.setCountry(manufacturer.getCountry());
        updated.setName(manufacturer.getName());
        saveManufacturer(updated);
        return updated;
    }

    @Override
    public Souvenir updateSouvenir(Souvenir souvenir) {
        var updated = getSouvenirById(souvenir.getId());
        updated.setDate(souvenir.getDate());
        updated.setName(souvenir.getName());
        updated.setPrice(souvenir.getPrice());
        saveManufacturer(updated.getManufacturer());
        return updated;
    }

    @Override
    public Manufacturer addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(generateId(manufacturers.keySet()));
        manufacturers.put(manufacturer.getId(), manufacturer);
        saveManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Souvenir addSouvenir(Long id, Souvenir souvenir) {
        souvenir.setId(generateId(souvenirs.keySet()));
        var manufacturer = getManufacturerById(id);
        manufacturer.addSouvenir(souvenir);
        souvenirs.put(souvenir.getId(), souvenir);
        saveManufacturer(manufacturer);
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

    private Long generateId(Set<Long> ids) {
        return ids.stream().max(Long::compare).map(id -> id + 1).orElse(0L);
    }

    @SneakyThrows
    private void readDataFromStorage() {
        for (var file : Objects.requireNonNull(storage.listFiles())) {
            try (var reader = new BufferedReader(new FileReader(file))) {
                var manufacturer = mapper.readValue(reader.readLine(), Manufacturer.class);
                manufacturers.put(manufacturer.getId(), manufacturer);
                souvenirs.putAll(manufacturer.getSouvenirs().stream().collect(Collectors.toMap(Souvenir::getId, souvenir -> souvenir)));
            }
        }
    }


    @SneakyThrows
    private void saveManufacturer(Manufacturer manufacturers) {
        try (var writer = new BufferedWriter(new FileWriter(storage + File.separator + manufacturers.getId()))) {
            writer.append(mapper.writeValueAsString(manufacturers));
        }
    }
}
