package exam.handler;

import exam.dao.Dao;
import exam.dto.ManufacturerDto;
import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;
import exam.dto.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ManufacturerHandlerImpl implements ManufacturerHandler {
    private final Dao dao;
    private final Mapper mapper;

    private final BufferedReader reader;

    private final ManufacturerCreatorUpdater manufacturerCreatorUpdater;

    private final SouvenirCreatorUpdater souvenirCreatorUpdater;

    @Override
    public void getManufacturers() {
        dao.getManufacturers().forEach(manufacturer -> System.out.println(mapper.toManufacturerDto(manufacturer)));
    }


    @Override
    public void getFullManufacturer() {
        System.out.println(mapper.toManufacturerFullDto(dao.getManufacturerById(getId())));
    }


    @SneakyThrows
    @Override
    public void removeManufacturer() {
        dao.removeManufacturer(getId());

    }

    @Override
    public void addManufacturer() {
        System.out.println(mapper.toManufacturerDto(dao.addManufacturer(manufacturerCreatorUpdater.create().toManufacturer())));
    }

    @Override
    public void updateManufacturer() {
        var updated = manufacturerCreatorUpdater.update(mapper.toManufacturerDto(dao.getManufacturerById(getId())));
        System.out.println(mapper.toManufacturerDto(dao.updateManufacturer(updated.toManufacturer())));
    }

    @Override
    public void addSouvenir() {
        System.out.println(mapper.toSouvenirDto(dao.addSouvenir(getId(), souvenirCreatorUpdater.create().toSouvenir())));
    }

    @Override
    public void updateSouvenir() {
        System.out.println(mapper.toSouvenirDto(dao.updateSouvenir(souvenirCreatorUpdater.update(dao.getSouvenirById(getId())).toSouvenir())));
    }

    @Override
    public void getCheapest() {
        var price = getPrice();
        dao.getManufacturers().stream()
                .filter(manufacturer -> manufacturer.isMakesSouvenirsCheaperThanValue(price))
                .forEach(m -> System.out.println(mapper.toManufacturerDto(m)));
    }

    @Override
    public void getSouvenirs() {
        dao.getSouvenirs().forEach(souvenir -> System.out.println(mapper.toSouvenirDto(souvenir)));
    }

    @Override
    public void getSouvenirById() {
        System.out.println(mapper.toSouvenirFullDto(dao.getSouvenirById(getId())));
    }

    @Override
    public void removeSouvenir() {
        dao.removeSouvenir(getId());
    }

    @Override
    public void getSouvenirsByYears() {
        var map = new HashMap<Integer, List<SouvenirFullDto>>();
        for (var s : dao.getSouvenirs()) {
            var list = map.get(s.getDate().getYear());
            if (list != null) list.add(mapper.toSouvenirFullDto(s));
            else map.put(s.getDate().getYear(), new ArrayList<>(List.of(mapper.toSouvenirFullDto(s))));
        }
        map.keySet().forEach(key -> System.out.println(key + " " + map.get(key)));
    }

    @Override
    @SneakyThrows
    public void getSouvenirsByCountry() {
        var country = reader.readLine();
        dao.getSouvenirs().stream()
                .filter(souvenir -> souvenir.getManufacturer().getCountry().equals(country))
                .forEach(souvenir -> System.out.println(mapper.toSouvenirFullDto(souvenir)));
    }

    @SneakyThrows
    @Override
    public void getSouvenirsByNameAndYear() {
        var name = reader.readLine();
        var year = getYear();
        dao.getSouvenirs().stream()
                .filter(souvenir -> souvenir.getName().equals(name) && souvenir.getDate().getYear() == year)
                .forEach(souvenir -> System.out.println(mapper.toSouvenirDto(souvenir)));
    }

    private int getYear() {
        try {
            System.out.println("print year");
            return LocalDate.parse(reader.readLine()).getYear();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return getYear();
        }
    }


    private Long getId() {
        try {
            System.out.print("print id ");
            return Long.valueOf(reader.readLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return getId();
        }
    }

    private double getPrice() {
        try {
            System.out.print("get price");
            return Double.parseDouble(reader.readLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return getPrice();
        }
    }
}
