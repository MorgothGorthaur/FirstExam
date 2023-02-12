package exam.service;

import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;
import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Exam {
    List<ManufacturerDto> getManufactures();

    List<SouvenirFullDto> getSouvenirs();

    void removeManufacturer(Long id);

    void removeSouvenir(Long id);

    void updateSouvenir(SouvenirDto souvenir);

    void updateManufacturer(ManufacturerDto manufacturer);

    void addSouvenir(Long id, SouvenirDto souvenir);

    void addManufacturer(ManufacturerDto manufacturer);

    List<SouvenirDto> getSouvenirsByManufacturerId(Long manufacturerId);

    List<SouvenirFullDto> getSouvenirsByCountry(String country);

    List<ManufacturerFullDto> getManufacturerWithSouvenirsCheaperThatPrice(double price);

    List<ManufacturerDto> getManufacturersBySouvenirNameThatWasMadeThisYear(String name);

    Map<LocalDate, List<SouvenirFullDto>> getSouvenirsByYear();

}
