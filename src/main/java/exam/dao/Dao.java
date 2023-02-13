package exam.dao;

import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Dao {
    //Map<Long, Manufacturer> readManufacturers();

    //void saveManufactures(Collection<Manufacturer> manufacturers);

    List<Manufacturer> getManufacturers();

    List<Souvenir> getSouvenirs();

    List<Souvenir> getManufacturersSouvenirs(Long id);

    void removeManufacturer(Long id);

    void removeSouvenir(Long id);

    void updateManufacturer(Manufacturer manufacturer);

    void updateSouvenir(Souvenir souvenir);

    Manufacturer addManufacturer(Manufacturer manufacturer);

    Manufacturer addSouvenir(Long id, Souvenir souvenir);

    Manufacturer getManufacturerById(Long id);

    Souvenir getSouvenirById(Long id);

}
