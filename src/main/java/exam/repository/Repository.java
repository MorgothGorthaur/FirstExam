package exam.repository;

import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.util.List;

public interface Repository {
    List<Manufacturer> getManufacturers();

    List<Souvenir> getSouvenirs();

    List<Souvenir> getManufacturersSouvenirs(Long id);

    void removeManufacturer(Long id);

    void removeSouvenir(Long id);

    Manufacturer updateManufacturer(Manufacturer manufacturer);

    Souvenir updateSouvenir(Souvenir souvenir);

    Manufacturer addManufacturer(Manufacturer manufacturer);

    Souvenir addSouvenir(Long id, Souvenir souvenir);

    Manufacturer getManufacturerById(Long id);

    Souvenir getSouvenirById(Long id);

}
