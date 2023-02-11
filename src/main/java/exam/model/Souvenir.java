package exam.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor
@Getter @Setter
public class Souvenir {
    private Long id;
    private String name;
    private LocalDate date;
    private double price;
    private Manufacturer manufacturer;

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
        hash = 31 * hash + Double.valueOf(price).hashCode();
        return hash;
    }
    @Override
    public String toString(){
        return "Souvenir(name=" + name + ", date=" + date + ", price=" + price + ", manufacturer=" + manufacturer.toString() + ")";
    }
}
