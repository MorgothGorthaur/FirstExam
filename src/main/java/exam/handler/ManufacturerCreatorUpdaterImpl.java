package exam.handler;

import exam.dto.ManufacturerDto;
import exam.exception.ManufacturerValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;

@Component
@RequiredArgsConstructor
public class ManufacturerCreatorUpdaterImpl implements ManufacturerCreatorUpdater {
    private final BufferedReader reader;

    @Override
    @SneakyThrows
    public ManufacturerDto create() {
        try {
            var name = reader.readLine();
            var country = reader.readLine();
            return new ManufacturerDto(null, name, country);
        } catch (ManufacturerValidationException ex) {
            System.out.println(ex.getMessage());
            return create();
        }
    }

    @SneakyThrows
    @Override
    public ManufacturerDto update(ManufacturerDto dto) {
        try {
            var name = dto.name();
            var country = dto.country();
            var line = "";
            while (!(line = reader.readLine()).equals("update")) {
                switch (line) {
                    case "name" -> name = reader.readLine();
                    case "country" -> country = reader.readLine();
                    default -> menu();
                }
            }
            return new ManufacturerDto(dto.id(), name, country);

        } catch (ManufacturerValidationException ex) {
            System.out.println(ex.getMessage());
            return update(dto);
        }
    }

    private void menu() {
        System.out.println("""
                ++++++++++++++++++++++++++++++++++
                + name - for updating name       + 
                + country - for updating country +
                + update - updates               +
                ++++++++++++++++++++++++++++++++++
                """);
    }
}
