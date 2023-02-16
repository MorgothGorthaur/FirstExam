package exam.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Configuration
public class ExamConfig {
    @Value("${storage.folder.name}")
    String FILE_FOLDER_NAME;

    @Bean
    BufferedReader reader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    File storage() {
        var storage = new File(FILE_FOLDER_NAME);
        if (!storage.exists()) storage.mkdir();
        return storage;
    }
}
