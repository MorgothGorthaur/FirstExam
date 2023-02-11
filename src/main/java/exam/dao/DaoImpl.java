package exam.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DaoImpl implements Dao {
    private final String MANUFACTURER_FILE_NAME = "manufacturers.json";
    private final String SOUVENIRS_FILE_NAME = "souvenirs.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public Map<Long, Manufacturer> getManufacturers() {
        try (var reader = new BufferedReader(new FileReader(MANUFACTURER_FILE_NAME))) {
            var manufactures = new HashMap<Long, Manufacturer>();
            var line = "";
            while ((line = reader.readLine()) != null) {
                var manufacturer = mapper.readValue(line, Manufacturer.class);
                manufactures.put(manufacturer.getId(), manufacturer);
            }
            return manufactures;
        }
    }

    @SneakyThrows
    @Override
    public Map<Long, Souvenir> getSouvenirs() {
        try (var reader = new BufferedReader(new FileReader(SOUVENIRS_FILE_NAME))) {
            var souvenirs = new HashMap<Long, Souvenir>();
            var line = "";
            while ((line = reader.readLine()) != null) {
                var souvenir = mapper.readValue(line, Souvenir.class);
                souvenirs.put(souvenir.getId(), souvenir);
            }
            return souvenirs;
        }
    }

    @SneakyThrows
    @Override
    public void saveManufactures(Collection<Manufacturer> manufacturers) {
        try (var writer = new BufferedWriter(new FileWriter(MANUFACTURER_FILE_NAME))) {
            for (var manufacturer : manufacturers) writer.append(mapper.writeValueAsString(manufacturer)).append("\n");
        }
    }

    @SneakyThrows
    @Override
    public void saveSouvenirs(Collection<Souvenir> souvenirs) {
        try (var writer = new BufferedWriter(new FileWriter(SOUVENIRS_FILE_NAME))) {
            for (var souvenir : souvenirs) writer.append(mapper.writeValueAsString(souvenir)).append("\n");
        }
    }
}
