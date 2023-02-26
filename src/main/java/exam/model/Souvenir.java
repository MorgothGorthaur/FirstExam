package exam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Souvenir {
    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private long price;
    @JsonBackReference
    private Manufacturer manufacturer;

    public Souvenir(String name, LocalDate date, long price) {
        this(null, name, date, price, null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Souvenir souvenir)) return false;
        return Objects.equals(name, souvenir.name) && Objects.equals(date, souvenir.date) && price == souvenir.price;
    }

    @Override
    public int hashCode() {
        var hash = 7;
        hash = 31 * hash + (name != null ? name.hashCode() : 0);
        hash = 31 * hash + (date != null ? date.hashCode() : 0);
        hash = 31 * hash + Long.hashCode(price);
        return hash;
    }
}
