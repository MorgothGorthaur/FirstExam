package exam;

import exam.handler.ManufacturerHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;


@RequiredArgsConstructor
@SpringBootApplication
public class App implements CommandLineRunner {
    private final ManufacturerHandler manufacturerHandler;
    private final BufferedReader reader;
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Override
    @SneakyThrows
    public void run(String... args) {
        System.out.println(reader.readLine());
    }
}
