package exam;

import exam.handler.ManufacturerHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.time.LocalDate;


@RequiredArgsConstructor
@SpringBootApplication
public class App implements CommandLineRunner {
    private final ManufacturerHandler manufacturerHandler;
    private final ConfigurableApplicationContext context;
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Override
    @SneakyThrows
    public void run(String... args) {
        manufacturerHandler.menuHandler();
        SpringApplication.exit(context);
    }


}
