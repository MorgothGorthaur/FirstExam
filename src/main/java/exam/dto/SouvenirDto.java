package exam.dto;

import lombok.NonNull;

import java.time.LocalDate;

public record SouvenirDto(Long id, @NonNull String name, @NonNull LocalDate date, long price) {
}
