package exam.dao;

import exam.model.Manufacturer;

import java.util.Collection;
import java.util.Map;

public interface Dao {
    Map<Long, Manufacturer> readManufacturers();
    void saveManufactures(Collection<Manufacturer> manufacturers);
}
