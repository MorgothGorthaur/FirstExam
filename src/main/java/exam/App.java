package exam;

import exam.handler.ManufacturerHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.time.LocalDate;


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
        var line = "";
        menu();
        while (!(line = reader.readLine()).equals("exit")) {
            switch (line) {
                case "get manufacturers" -> manufacturerHandler.getManufacturers();
                case "get full manufacturers" -> manufacturerHandler.getFullManufacturers();
                case "get manufacturer" -> manufacturerHandler.getManufacturer();
                case "remove manufacturer" -> manufacturerHandler.removeManufacturer();
                case "update manufacturer" -> manufacturerHandler.updateManufacturer();
                case "add manufacturer" -> manufacturerHandler.addManufacturer();
                case "get manufacturer by name and year" -> manufacturerHandler.getManufacturersBySouvenirNameAndYear();
                case "get cheapest" -> manufacturerHandler.getManufacturersThatMakesSouvenirsCheapestThenValue();
                default -> menu();
            }
        }
    }

    private void menu() {
        System.out.println("""
                get manufacturers - for getting all manufacturers
                get full manufacturers - for getting manufacturers with souvenirs
                get manufacturer - for getting manufacturer with souvenirs
                remove manufacturer - for removing manufacturers
                update manufacturer - for updating manufacturers
                add manufacturer - for adding manufacturers
                get manufacturer by name and year - for getting manufacturers by souvenir name and year
                get cheapest - for getting manufacturers that makes souvenirs cheaper then price
                """);
    }
}
