package exam.dto;

import lombok.NonNull;
public record ManufacturerDto(@NonNull Long id,@NonNull String name,@NonNull String country) {
    public ManufacturerDto{
        if(id < 0L && name.equals("") && country.equals("")) throw new RuntimeException();
    }
}
