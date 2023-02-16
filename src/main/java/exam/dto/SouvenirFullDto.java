package exam.dto;

import lombok.NonNull;

import java.time.LocalDate;

public record SouvenirFullDto(@NonNull Long id, @NonNull String name, double price, @NonNull LocalDate date,
                              @NonNull ManufacturerDto manufacturer) {
}
