package exam.dto;

import exam.model.Souvenir;
import lombok.NonNull;

import java.time.LocalDate;

public record SouvenirDto(Long id, @NonNull String name, double price, @NonNull LocalDate date) {
    public SouvenirDto {
        if ((id != null && id < 0L) && name.equals("") && price < 0) throw new RuntimeException();
    }

    public Souvenir toSouvenir() {
        return new Souvenir(id, name, date, price, null);
    }
}
