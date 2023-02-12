package exam.dto;

import lombok.NonNull;

import java.time.LocalDate;

public record SouvenirFullDto(@NonNull Long id, @NonNull String name, double price, @NonNull LocalDate date,
                              @NonNull ManufacturerDto manufacturer) {
    public SouvenirFullDto {
        if (id < 0L && name.equals("") && price < 0) throw new RuntimeException();
    }
}
