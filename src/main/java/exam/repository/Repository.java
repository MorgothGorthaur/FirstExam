package exam.repository;

import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Repository {
    List<Manufacturer> getManufacturers();

    List<Souvenir> getSouvenirs();

    void removeManufacturer(long id);

    void removeSouvenir(long id);

    void addManufacturer(Manufacturer manufacturer);

    void addSouvenir(Souvenir souvenir);

    Optional<Manufacturer> getManufacturerById(long id);

    Optional<Souvenir> getSouvenirById(long id);

    List<Manufacturer> getManufacturersBySouvenirNameAndYear(String name, int year);

    List<Souvenir> getSouvenirsByCountry(String country);

    List<Manufacturer> getManufacturersThatMakesSouvenirsCheaperThenValue(BigDecimal price);

    Map<Integer, List<Souvenir>> getSouvenirsByYears();

}
