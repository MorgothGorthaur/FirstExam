package exam.dto;

import exam.model.Manufacturer;
import lombok.NonNull;

import java.util.ArrayList;

public record ManufacturerDto(Long id, @NonNull String name, @NonNull String country) {
    public ManufacturerDto {
        if ((id != null && id < 0L) && name.equals("") && country.equals("")) throw new RuntimeException();
    }

    public Manufacturer toManufacturer() {
        return new Manufacturer(id, name, country, new ArrayList<>());
    }
}
