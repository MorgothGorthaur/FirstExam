package exam.dto;

import exam.exception.ManufacturerValidationException;
import exam.model.Manufacturer;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashSet;

public record ManufacturerDto(Long id, @NonNull String name, @NonNull String country) {
    public ManufacturerDto {
        if (name.equals("") && country.equals("")) throw new ManufacturerValidationException();
    }
}
