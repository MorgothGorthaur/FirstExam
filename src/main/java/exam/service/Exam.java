package exam.service;

import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Exam {
    List<Manufacturer> getManufactures();
    List<Souvenir> getSouvenirsByManufacturerId();
    void removeManufacturer(Long id);
    void removeSouvenir(Long id);
    Souvenir updateSouvenir(Souvenir souvenir);
    Manufacturer updateManufacturer(Manufacturer manufacturer);

    void addSouvenir(Long id, Souvenir souvenir);

    void addManufacturer(Manufacturer manufacturer);

    List<Souvenir> getSouvenirsByManufacturerId(Long manufacturerId);

    List<Souvenir> getSouvenirsByCountry(String country);

    List<Manufacturer> getManufacturerWithSouvenirsCheaperThatPrice(double price);

    List<Manufacturer> getManufacturersBySouvenirNameThatWasMadeThisYear(String name);

    Map<LocalDate, Souvenir> getSouvenirsByYear(LocalDate date);

}
