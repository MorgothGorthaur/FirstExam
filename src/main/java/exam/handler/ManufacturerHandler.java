package exam.handler;

import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.model.Manufacturer;

import java.util.List;

public interface ManufacturerHandler {
    List<ManufacturerDto> getManufacturers();
    ManufacturerFullDto getFullManufacturer(Long id);
    void removeManufacturer(Long id);
    ManufacturerDto addManufacturer(ManufacturerDto dto);
    ManufacturerDto updateManufacturer(ManufacturerDto dto);
    SouvenirDto addSouvenir(Long id, SouvenirDto dto);
    SouvenirDto updateSouvenir(SouvenirDto dto);
    List<ManufacturerDto> getCheapest(double price);
}
