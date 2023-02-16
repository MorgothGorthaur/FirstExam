package exam.dto;


import lombok.NonNull;
import java.util.Set;

public record ManufacturerFullDto(@NonNull Long id, @NonNull String name,
                                  @NonNull String country, @NonNull Set<SouvenirDto> souvenirs) {
    @Override
    public String toString() {
        var str = new StringBuilder();
        str.append("ManufacturerFullDto[id=").append(id).append(", name=").append(name).append(", country=").append(country).append(", souvenirs: \n");
        souvenirs.forEach(s -> str.append("\t").append(s.toString()).append("\n"));
        return str.append("]").toString();
    }
}
