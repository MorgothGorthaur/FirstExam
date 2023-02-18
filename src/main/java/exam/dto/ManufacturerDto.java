package exam.dto;

import lombok.NonNull;


public record ManufacturerDto(Long id, @NonNull String name, @NonNull String country) {
}
