package exam.handler;

import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.model.Manufacturer;

import java.util.List;

public interface ManufacturerHandler {
    void getManufacturers();
    void getFullManufacturer();
    void removeManufacturer();
    void addManufacturer();
    void updateManufacturer();
    void addSouvenir();
    void updateSouvenir();
    void getCheapest();
}
