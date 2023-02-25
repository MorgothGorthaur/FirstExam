package exam.dto;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SouvenirFullDto(@NonNull Long id, @NonNull String name, @NonNull LocalDate date, BigDecimal price,
                              @NonNull ManufacturerDto manufacturer) {
}
