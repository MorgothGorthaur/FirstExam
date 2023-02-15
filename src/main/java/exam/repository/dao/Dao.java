package exam.repository.dao;

import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.util.Map;

public interface Dao {
    void readAll(Map<Long, Manufacturer> manufacturers, Map<Long, Souvenir> souvenirs);
    void saveManufacturer(Manufacturer manufacturer);
    void removeManufacturer(Manufacturer manufacturer);
}
