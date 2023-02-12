package exam.dao;

import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.util.Collection;
import java.util.Map;

public interface Dao {
    Map<Long, Manufacturer> getManufacturers();
    Map<Long, Souvenir> getSouvenirs();
    void saveManufactures(Collection<Manufacturer> manufacturers);
    void saveSouvenirs(Collection<Souvenir> souvenirs);
}