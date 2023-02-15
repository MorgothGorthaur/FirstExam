package exam.repository.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exam.model.Manufacturer;
import exam.model.Souvenir;
import exam.repository.dao.Dao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DaoImpl implements Dao {
    private final File storage;
    private final ObjectMapper mapper;

    public DaoImpl(@Value("${storage.folder.name}") String FILE_FOLDER_NAME) {
        this.storage = new File(FILE_FOLDER_NAME);
        if (!storage.exists()) new File(FILE_FOLDER_NAME).mkdir();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    @SneakyThrows
    public Map<Long, Manufacturer> readAll() {
        var manufacturers = new HashMap<Long, Manufacturer>();
        for (var file : Objects.requireNonNull(storage.listFiles())) {
            try (var reader = new BufferedReader(new FileReader(file))) {
                var manufacturer = mapper.readValue(reader.readLine(), Manufacturer.class);
                manufacturers.put(manufacturer.getId(), manufacturer);
            }
        }
        return manufacturers;
    }

    @Override
    @SneakyThrows
    public void saveManufacturer(Manufacturer manufacturer) {
        try (var writer = new BufferedWriter(new FileWriter(storage + File.separator + manufacturer.getId()))) {
            writer.append(mapper.writeValueAsString(manufacturer));
        }
    }

    @Override
    public void removeManufacturer(Manufacturer manufacturer) {
        new File(storage + File.separator + manufacturer.getId()).delete();
    }
}
