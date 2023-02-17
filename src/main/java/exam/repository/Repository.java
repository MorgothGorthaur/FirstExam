package exam.repository;

import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Repository {
    List<Manufacturer> getManufacturers();

    List<Souvenir> getSouvenirs();

    void removeManufacturer(long id);

    void removeSouvenir(long id);

    void updateManufacturer(Manufacturer manufacturer);

    void updateSouvenir(Souvenir souvenir);

    void addManufacturer(Manufacturer manufacturer);

    void addSouvenir(long id, Souvenir souvenir);

    Manufacturer getManufacturerById(long id);

    Souvenir getSouvenirById(long id);

    List<Manufacturer> getManufacturersBySouvenirNameAndYear(String name, int year);

    List<Souvenir> getSouvenirsByCountry(String country);

    List<Manufacturer> getManufacturersThatMakesSouvenirsCheaperThenValue(double price);

    Map<Integer, List<Souvenir>> getSouvenirsByYears();

}
