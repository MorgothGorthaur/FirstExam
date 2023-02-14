package exam.handler;

import exam.dao.Dao;
import exam.dto.ManufacturerDto;
import exam.dto.SouvenirDto;
import exam.dto.mapper.Mapper;
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

    private final ManufacturerCreatorUpdater manufacturerCreatorUpdater;

    private final SouvenirCreatorUpdater souvenirCreatorUpdater;

    @Override
    public void getManufacturers() {
        dao.getManufacturers().forEach(manufacturer -> System.out.println(mapper.toManufacturerDto(manufacturer)));
    }


    @Override
    public void getFullManufacturer() {
        System.out.println(dao.getManufacturerById(getId()));
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

    private Long getId() {
        try {
            System.out.print("print id");
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
