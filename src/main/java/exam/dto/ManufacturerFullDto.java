package exam.dto;

import lombok.NonNull;

import java.util.List;

public record ManufacturerFullDto(@NonNull Long id, @NonNull String name,
                                  @NonNull String country, @NonNull List<SouvenirDto> souvenirs) {
    public ManufacturerFullDto {
        if (id < 0L && name.equals("") && country.equals("")) throw new RuntimeException();
    }
}
