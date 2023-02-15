package exam.repository;

import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Repository {
    List<Manufacturer> getManufacturers();

    List<Souvenir> getSouvenirs();

    Set<Souvenir> getManufacturersSouvenirs(Long id);

    void removeManufacturer(Long id);

    void removeSouvenir(Long id);

    Manufacturer updateManufacturer(Manufacturer manufacturer);

    Souvenir updateSouvenir(Souvenir souvenir);

    Manufacturer addManufacturer(Manufacturer manufacturer);

    Souvenir addSouvenir(Long id, Souvenir souvenir);

    Manufacturer getManufacturerById(Long id);

    Souvenir getSouvenirById(Long id);

    Set<Manufacturer> getManufacturersBySouvenirNameAndYear(String name, int year);

    List<Souvenir> getSouvenirsByCountry(String country);

    List<Manufacturer> getManufacturersThatMakesSouvenirsCheaperThenValue(double price);

    Map<Integer, List<Souvenir>> getSouvenirsByYears();

}
