package exam.handler;

import exam.repository.Repository;
import exam.dto.mapper.Mapper;
import exam.exception.ManufacturedNotFoundException;
import exam.exception.SouvenirNotFoundException;
import exam.model.Manufacturer;
import exam.model.Souvenir;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.time.LocalDate;


@RequiredArgsConstructor
@Component
public class ManufacturerHandlerImpl implements ManufacturerHandler {
    private final Repository repository;
    private final Mapper mapper;
    private final BufferedReader reader;

    @SneakyThrows
    @Override
    public void menuHandler() {
        var line = "";
        menu();
        while (!(line = reader.readLine()).equals("exit")) {
            switch (line) {
                case "get manufacturers" -> printManufacturers();
                case "get full manufacturers" -> printFullManufacturers();
                case "get manufacturer" -> printManufacturer();
                case "remove manufacturer" -> removeManufacturer();
                case "update manufacturer" ->updateManufacturer();
                case "add manufacturer" -> addManufacturer();
                case "get manufacturer by name and year" -> printManufacturersBySouvenirNameAndYear();
                case "get cheapest" -> printManufacturersThatMakesSouvenirsCheapestThenValue();
                case "get souvenirs" -> printSouvenirs();
                case "get full souvenirs" -> printFullSouvenirs();
                case "get souvenir" -> printSouvenir();
                case "add souvenir" -> addSouvenir();
                case "update souvenir" ->updateSouvenir();
                case "remove souvenir" -> removeSouvenir();
                case "get souvenirs by years" -> printSouvenirsByYears();
                case "get souvenirs by country" -> printSouvenirsByCountry();
                default -> menu();
            }
        }
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
                + get souvenirs by country - for getting souvenirs by country                             +
                + menu - reprints menu                                                                    +
                + exit - exit                                                                             +
                +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                """);
    }

    public void printManufacturers() {
        repository.getManufacturers().forEach(manufacturer -> System.out.println(mapper.toManufacturerDto(manufacturer)));
    }

    public void printManufacturer() {
        try {
            System.out.println(mapper.toManufacturerFullDto(repository.getManufacturerById(setId())));
        } catch (ManufacturedNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void printFullManufacturers() {
        repository.getManufacturers().forEach(manufacturer -> System.out.println(mapper.toManufacturerFullDto(manufacturer)));
    }


    public void removeManufacturer() {
        try {
            repository.removeManufacturer(setId());
        } catch (ManufacturedNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @SneakyThrows
    public void updateManufacturer() {
        try {
            var manufacturer = repository.getManufacturerById(setId());
            updateManufacturerMenu();
            var line = "";
            while (!(line = reader.readLine()).equals("update")) {
                switch (line) {
                    case "name" -> manufacturer.setName(setName());
                    case "country" -> manufacturer.setCountry(setCountry());
                    default -> updateManufacturerMenu();
                }
            }
            System.out.println("your manufacturer: " + mapper.toManufacturerDto(repository.updateManufacturer(manufacturer)));
        } catch (ManufacturedNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void addManufacturer() {
        var name = setName();
        var country = setCountry();
        System.out.println("your manufacturer " + mapper.toManufacturerDto(repository.addManufacturer(new Manufacturer(name, country))));
    }

    public void printManufacturersBySouvenirNameAndYear() {
        repository.getManufacturersBySouvenirNameAndYear(setName(), setYear())
                .forEach(manufacturer -> System.out.println(mapper.toManufacturerDto(manufacturer)));
    }


    public void printManufacturersThatMakesSouvenirsCheapestThenValue() {
        repository.getManufacturersThatMakesSouvenirsCheaperThenValue(setPrice())
                .forEach(manufacturer -> System.out.println(mapper.toManufacturerFullDto(manufacturer)));
    }


    public void printSouvenirs() {
        repository.getSouvenirs().forEach(souvenir -> System.out.println(mapper.toSouvenirDto(souvenir)));
    }


    public void printFullSouvenirs() {
        repository.getSouvenirs().forEach(souvenir -> System.out.println(mapper.toSouvenirFullDto(souvenir)));
    }


    public void printSouvenir() {
        System.out.println("you must set souvenir id: ");
        System.out.println(mapper.toSouvenirFullDto(repository.getSouvenirById(setId())));
    }


    public void addSouvenir() {
        try {
            System.out.println("you must set manufacturer id: ");
            var manufacturerId = setId();
            var name = setName();
            var price = setPrice();
            var date = setDate();
            System.out.println("your souvenir: " + mapper.toSouvenirDto(repository.addSouvenir(manufacturerId, new Souvenir(name, price, date))));
        } catch (ManufacturedNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @SneakyThrows
    public void updateSouvenir() {
        try {
            System.out.println("you must set souvenir id: ");
            var souvenir = repository.getSouvenirById(setId());
            var line = "";
            updateSouvenirMenu();
            while (!(line = reader.readLine()).equals("update")) {
                switch (line) {
                    case "name" -> souvenir.setName(setName());
                    case "price" -> souvenir.setPrice(setPrice());
                    case "date" -> souvenir.setDate(setDate());
                    default -> updateSouvenirMenu();
                }
            }
            System.out.println("your souvenir: " + mapper.toSouvenirDto(repository.updateSouvenir(souvenir)));
        } catch (SouvenirNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeSouvenir() {
        try {
            System.out.println("you must print souvenir name: ");
            repository.removeSouvenir(setId());
        } catch (SouvenirNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void printSouvenirsByYears() {
        repository.getSouvenirsByYears().forEach((key, value) -> System.out.println(key + "\n\t" + value.stream().map(mapper::toSouvenirDto).toList()));
    }

    public void printSouvenirsByCountry() {
        repository.getSouvenirsByCountry(setCountry())
                .forEach(souvenir -> System.out.println(mapper.toSouvenirDto(souvenir)));
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

    private LocalDate setDate() {
        try {
            System.out.print("print date: ");
            return LocalDate.parse(reader.readLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return setDate();
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

    private int setYear() {
        try {
            System.out.print("print year: ");
            return Integer.parseInt(reader.readLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return setYear();
        }
    }

    private double setPrice() {
        try {

            System.out.print("print price: ");
            var price = Double.parseDouble(reader.readLine());
            if (price < 0) {
                System.out.println("price must be higher then 0");
                return setPrice();
            }
            return price;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return setPrice();
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

    private void updateManufacturerMenu() {
        System.out.println("""
                ++++++++++++++++++++++++++++++++++
                + name - for changing name       +
                + country - for changing country +
                + update - updates               +
                ++++++++++++++++++++++++++++++++++
                """);
    }

    private void updateSouvenirMenu() {
        System.out.println("""
                ++++++++++++++++++++++++++++++
                + name - for changing name   +
                + price - for changing price +
                + date - for changing date   +
                + update - updates           +
                ++++++++++++++++++++++++++++++
                """);
    }

}
