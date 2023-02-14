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
    private final BufferedReader reader;
    private final ConfigurableApplicationContext context;
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
                case "get souvenirs" -> manufacturerHandler.getSouvenirs();
                case "get full souvenirs" -> manufacturerHandler.getFullSouvenirs();
                case "get souvenir" -> manufacturerHandler.getSouvenir();
                case "add souvenir" -> manufacturerHandler.addSouvenir();
                case "update souvenir" -> manufacturerHandler.updateSouvenir();
                case "remove souvenir" -> manufacturerHandler.removeSouvenir();
                case "get souvenirs by years" -> manufacturerHandler.getSouvenirsByYears();
                default -> menu();
            }
        }
        SpringApplication.exit(context);
    }

    private void menu() {
        System.out.println("""
                +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                + get manufacturers - for getting all manufacturers                                       +
                + get full manufacturers - for getting manufacturers with souvenirs                       +
                + get manufacturer - for getting manufacturer with souvenirs                              +
                + remove manufacturer - for removing manufacturers                                        +
                + update manufacturer - for updating manufacturers                                        +
                + add manufacturer - for adding manufacturers                                             +
                + get manufacturer by name and year - for getting manufacturers by souvenir name and year +
                + get cheapest - for getting manufacturers that makes souvenirs cheaper then price        +
                + get souvenirs - for getting souvenirs                                                   +
                + get full souvenirs - for getting souvenirs with manufacturers                           +
                + get souvenir - for getting souvenir                                                     +
                + add souvenir - for adding souvenir                                                      +
                + update souvenir - for updating souvenir                                                 +
                + remove souvenir - remove souvenir                                                       +
                + get souvenirs by years - for getting souvenirs by year                                  +
                + menu - reprints menu                                                                    +
                + exit - exit                                                                             +
                +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                """);
    }
}
