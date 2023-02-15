package exam.handler;

import exam.dao.Dao;
import exam.dto.SouvenirDto;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

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
            updateManufacturerMenu();
            var line = "";
            while (!(line = reader.readLine()).equals("update")) {
                switch (line) {
                    case "name" -> manufacturer.setName(setName());
                    case "country" -> manufacturer.setCountry(setCountry());
                    default -> updateManufacturerMenu();
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
        var year = setYear();
        dao.getSouvenirs().stream().filter(souvenir -> souvenir.getName().equals(name) && souvenir.getDate().getYear() == year)
                .forEach(souvenir -> System.out.println(mapper.toManufacturerDto(souvenir.getManufacturer())));
    }

    @Override
    public void getManufacturersThatMakesSouvenirsCheapestThenValue() {
        var price = setPrice();
        dao.getManufacturers().stream().filter(manufacturer -> manufacturer.isMakesSouvenirsCheaperThanValue(price))
                .forEach(manufacturer -> System.out.println(mapper.toManufacturerFullDto(manufacturer)));
    }

    @Override
    public void getSouvenirs() {
        dao.getSouvenirs().forEach(souvenir -> System.out.println(mapper.toSouvenirDto(souvenir)));
    }

    @Override
    public void getFullSouvenirs() {
        dao.getSouvenirs().forEach(souvenir -> System.out.println(mapper.toSouvenirFullDto(souvenir)));
    }

    @Override
    public void getSouvenir() {
        System.out.println("you must set souvenir id: ");
        System.out.println(mapper.toSouvenirFullDto(dao.getSouvenirById(setId())));
    }

    @Override
    public void addSouvenir() {
        System.out.println("you must set manufacturer id: ");
        var manufacturerId = setId();
        var name = setName();
        var price = setPrice();
        var date = setDate();
        System.out.println("your souvenir: " + mapper.toSouvenirDto(dao.addSouvenir(manufacturerId, new Souvenir(name, price, date))));
    }

    @Override
    @SneakyThrows
    public void updateSouvenir() {
        try {
            System.out.println("you must set souvenir id: ");
            var souvenir = dao.getSouvenirById(setId());
            var line = "";
            updateSouvenirMenu();
            while (!(line = reader.readLine()).equals("update")){
                switch (line){
                    case "name" -> souvenir.setName(setName());
                    case "price" -> souvenir.setPrice(setPrice());
                    case "date" -> souvenir.setDate(setDate());
                    default -> updateSouvenirMenu();
                }
            }
            System.out.println("your souvenir: " + mapper.toSouvenirDto(dao.updateSouvenir(souvenir)));
        } catch (SouvenirNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void removeSouvenir() {
        try{
            System.out.println("you must print souvenir name: ");
            dao.removeSouvenir(setId());
        } catch (SouvenirNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void getSouvenirsByYears() {
        var map = new TreeMap<Integer, List<SouvenirDto>>();
        dao.getSouvenirs().forEach(souvenir -> {
            var lst = map.get(souvenir.getDate().getYear());
            var dto = mapper.toSouvenirDto(souvenir);
            if(lst != null) lst.add(dto);
            else map.put(souvenir.getDate().getYear(), new ArrayList<>(List.of(dto)));
        });
        System.out.println(map);
    }

    @Override
    public void getSouvenirsByCountry() {
        var country = setCountry();
        dao.getSouvenirs().stream().filter(souvenir -> souvenir.getManufacturer().getCountry().equals(country))
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

    private void updateSouvenirMenu(){
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
