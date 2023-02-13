package exam.dto;

import exam.exception.ManufacturedNotFoundException;
import exam.exception.ManufacturerValidationException;
import lombok.NonNull;

import java.util.List;

public record ManufacturerFullDto(@NonNull Long id, @NonNull String name,
                                  @NonNull String country, @NonNull List<SouvenirDto> souvenirs) {
}
