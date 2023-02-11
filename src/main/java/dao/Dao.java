package dao;

import model.Manufacturer;
import model.Souvenir;

import java.util.Collection;
import java.util.Map;

public interface Dao {
    Map<Long, Manufacturer> getManufacturers();
    Map<Long, Souvenir> getSouvenirs();
    void saveManufactures(Collection<Manufacturer> manufacturers);
    void saveSouvenirs(Collection<Souvenir> souvenirs);
}
