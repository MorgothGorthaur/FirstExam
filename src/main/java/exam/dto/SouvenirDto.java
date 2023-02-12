package exam.dto;

import lombok.NonNull;

import java.time.LocalDate;
public record SouvenirDto(@NonNull Long id,@NonNull String name, double price,@NonNull LocalDate date) {
    public SouvenirDto{
        if(id < 0L && name.equals("") && price < 0) throw new RuntimeException();
    }
}
