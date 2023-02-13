package exam.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class Manufacturer {
    private Long id;
    private String name;
    private String country;
    @JsonManagedReference
    private List<Souvenir> souvenirs;

    public void addSouvenir(Souvenir souvenir) {
        souvenir.setManufacturer(this);
        souvenirs.add(souvenir);
    }

    public void removeSouvenir(Souvenir souvenir) {
        souvenir.setManufacturer(null);
        souvenirs.remove(souvenir);
    }

    public boolean isMakesSouvenirsCheaperThanValue(double price) {
        return souvenirs.stream().filter(souvenir -> souvenir.getPrice() > price).toList().size() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Manufacturer manufacturer)) return false;
        return Objects.equals(name, manufacturer.name) && Objects.equals(country, manufacturer.country);
    }

    @Override
    public int hashCode() {
        var hash = 7;
        hash = 31 * hash + (name != null ? name.hashCode() : 0);
        hash = 31 * hash + (country != null ? country.hashCode() : 0);
        return hash;
    }
}
