package exam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.math.BigDecimal;
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
    private BigDecimal price;
    @JsonBackReference
    private Manufacturer manufacturer;

    public Souvenir(String name, LocalDate date, BigDecimal price) {
        this(null, name, date, price, null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Souvenir souvenir)) return false;
        return Objects.equals(name, souvenir.name) && Objects.equals(date, souvenir.date) && Objects.equals(price, souvenir.price);
    }

    @Override
    public int hashCode() {
        var hash = 7;
        hash = 31 * hash + (name != null ? name.hashCode() : 0);
        hash = 31 * hash + (date != null ? date.hashCode() : 0);
        hash = 31 * hash + (price != null ? price.hashCode() : 0);
        return hash;
    }
}
