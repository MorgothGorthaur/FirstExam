package exam.repository.filehandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import exam.model.Manufacturer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FileHandlerImpl implements FileHandler {
    private final File storage;
    private final ObjectMapper mapper;


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
