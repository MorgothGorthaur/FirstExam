package exam.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import exam.model.Manufacturer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class DaoImpl implements Dao {
    private final String FILE_NAME;
    private final ObjectMapper mapper;

    public DaoImpl(@Value("${file.name}") String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public Map<Long, Manufacturer> readManufacturers() {
        try (var reader = new BufferedReader(new FileReader(FILE_NAME))) {
            var manufactures = new HashMap<Long, Manufacturer>();
            var line = "";
            while ((line = reader.readLine()) != null) {
                var manufacturer = mapper.readValue(line, Manufacturer.class);
                manufactures.put(manufacturer.getId(), manufacturer);
            }
            return manufactures;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new HashMap<>();
        }
    }


    @SneakyThrows
    @Override
    public void saveManufactures(Collection<Manufacturer> manufacturers) {
        try (var writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (var manufacturer : manufacturers) writer.append(mapper.writeValueAsString(manufacturer)).append("\n");
        }
    }

}
