package exam.repository.filehandler;

import exam.model.Manufacturer;

import java.util.Map;

public interface FileHandler {
    Map<Long, Manufacturer> readAll();
    void saveManufacturer(Manufacturer manufacturer);
    void removeManufacturer(Manufacturer manufacturer);
}
