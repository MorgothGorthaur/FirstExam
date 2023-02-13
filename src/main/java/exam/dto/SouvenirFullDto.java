package exam.dto;

import exam.exception.SouvenirValidationException;
import lombok.NonNull;

import java.time.LocalDate;

public record SouvenirFullDto(@NonNull Long id, @NonNull String name, double price, @NonNull LocalDate date,
                              @NonNull ManufacturerDto manufacturer) {
}
