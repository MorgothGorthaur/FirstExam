package exam.dto;

import lombok.NonNull;

import java.time.LocalDate;

public record SouvenirFullDto(@NonNull Long id, @NonNull String name, @NonNull LocalDate date, long price,
                              @NonNull ManufacturerDto manufacturer) {
}
