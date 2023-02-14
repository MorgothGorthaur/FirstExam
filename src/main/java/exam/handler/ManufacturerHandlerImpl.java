package exam.handler;

import exam.dao.Dao;
import exam.dto.mapper.Mapper;
import exam.exception.ManufacturedNotFoundException;
import exam.model.Manufacturer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;

@RequiredArgsConstructor
@Component
public class ManufacturerHandlerImpl implements ManufacturerHandler {
    private final Dao dao;
    private final Mapper mapper;
    private final BufferedReader reader;

    @Override
    public void getManufacturers() {
        dao.getManufacturers().forEach(manufacturer -> System.out.println(mapper.toManufacturerDto(manufacturer)));
    }

    @Override
    public void getManufacturer() {
        try {
            System.out.println(mapper.toManufacturerFullDto(dao.getManufacturerById(setId())));
        } catch (ManufacturedNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void getFullManufacturers() {
        dao.getManufacturers().forEach(manufacturer -> System.out.println(mapper.toManufacturerFullDto(manufacturer)));
    }

    @Override
    public void removeManufacturer() {
        try {
            dao.removeManufacturer(setId());
        } catch (ManufacturedNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public void updateManufacturer() {
        try {
            var manufacturer = dao.getManufacturerById(setId());
            menu();
            var line = "";
            while (!(line = reader.readLine()).equals("update")) {
                switch (line) {
                    case "name" -> manufacturer.setName(setName());
                    case "country" -> manufacturer.setCountry(setCountry());
                    default -> menu();
                }
            }
            System.out.println("your manufacturer: " + mapper.toManufacturerDto(dao.updateManufacturer(manufacturer)));
        } catch (ManufacturedNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void addManufacturer() {
        var name = setName();
        var country = setCountry();
        System.out.println("your manufacturer " + mapper.toManufacturerDto(dao.addManufacturer(new Manufacturer(name, country))));
    }

    @Override
    public void getManufacturersBySouvenirNameAndYear() {
        var name = setName();
        System.out.print("print year: ");
        var year = setIntValue();
        dao.getSouvenirs().stream().filter(souvenir -> souvenir.getName().equals(name) && souvenir.getDate().getYear() == year)
                .forEach(souvenir -> System.out.println(mapper.toManufacturerDto(souvenir.getManufacturer())));
    }

    @Override
    public void getManufacturersThatMakesSouvenirsCheapestThenValue() {
        System.out.print("print price: ");
        var price = setIntValue();
        dao.getManufacturers().stream().filter(manufacturer -> manufacturer.isMakesSouvenirsCheaperThanValue(price))
                .forEach(manufacturer -> System.out.println(mapper.toManufacturerFullDto(manufacturer)));
    }

    private Long setId() {
        try {
            System.out.print("print id: ");
            return Long.valueOf(reader.readLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return setId();
        }
    }

    @SneakyThrows
    private String setName() {
        System.out.print("print name: ");
        var name = reader.readLine();
        if (name.equals("")) {
            System.out.println("name must be not empty!");
            return setName();
        }
        return name;

    }

    private int setIntValue() {
        try {
            return Integer.parseInt(reader.readLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return setIntValue();
        }
    }

    @SneakyThrows
    private String setCountry() {
        System.out.print("print country: ");
        var country = reader.readLine();
        if (country.equals("")) {
            System.out.println("country must be not empty");
            return setCountry();
        }
        return country;
    }

    private void menu() {
        System.out.println("""
                ++++++++++++++++++++++++++++++++++
                + name - for changing name       +
                + country - for changing country +
                + update - updates               +
                ++++++++++++++++++++++++++++++++++
                """);
    }

}
