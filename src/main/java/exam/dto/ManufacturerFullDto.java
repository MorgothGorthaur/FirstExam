package exam.dto;


import lombok.NonNull;
import java.util.Set;

public record ManufacturerFullDto(@NonNull Long id, @NonNull String name,
                                  @NonNull String country, @NonNull Set<SouvenirDto> souvenirs) {
}
