package exam.handler;

import exam.dto.SouvenirDto;
import exam.exception.SouvenirValidationException;
import exam.model.Souvenir;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.time.LocalDate;
@Component
@RequiredArgsConstructor
public class SouvenirCreatorUpdaterImpl implements SouvenirCreatorUpdater {
    private BufferedReader reader;

    @Override
    @SneakyThrows
    public SouvenirDto create() {
        try {
            var name = reader.readLine();
            var price = getPrice();
            var date = getDate();
            return new SouvenirDto(null, name, price, date);
        } catch (SouvenirValidationException ex) {
            System.out.println(ex.getMessage());
            return create();
        }
    }


    @Override
    @SneakyThrows
    public SouvenirDto update(Souvenir souvenir) {
        try {
            menu();
            var name = souvenir.getName();
            var price = souvenir.getPrice();
            var date = souvenir.getDate();
            var line = "";
            while (!(line = reader.readLine()).equals("update")) {
                switch (line) {
                    case "name" -> name = reader.readLine();
                    case "price" -> price = getPrice();
                    case "date" -> date = getDate();
                    default -> menu();
                }
            }
            return new SouvenirDto(null, name, price, date);
        } catch (SouvenirValidationException ex) {
            System.out.println(ex.getMessage());
            return update(souvenir);
        }
    }

    private void menu() {
        System.out.println("""
                ++++++++++++++++++++++++++++++
                + name - for updating name   +
                + price - for updating price +
                + date - for updating date   +
                + update - updates           +
                ++++++++++++++++++++++++++++++
                """);
    }
    private LocalDate getDate() {
        try {
            System.out.print("print date ");
            return LocalDate.parse(reader.readLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return getDate();
        }
    }

    private double getPrice() {
        try {
            System.out.print("print price");
            return Double.parseDouble(reader.readLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return getPrice();
        }
    }
}
