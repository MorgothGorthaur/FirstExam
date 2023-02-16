package exam.repository.dao;

import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.util.Map;

public interface Dao {
    Map<Long, Manufacturer> readAll();
    void saveManufacturer(Manufacturer manufacturer);
    void removeManufacturer(Manufacturer manufacturer);
}
