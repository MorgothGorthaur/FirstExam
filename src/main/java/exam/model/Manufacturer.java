package exam.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class Manufacturer {
    private Long id;
    private String name;
    private String country;
    @JsonManagedReference
    private Set<Souvenir> souvenirs;

    public Manufacturer(String name, String country) {
        this(null, name, country, new HashSet<>());
    }

    public void addSouvenir(Souvenir souvenir) {
        souvenir.setManufacturer(this);
        souvenirs.add(souvenir);
    }

    public void removeSouvenir(Souvenir souvenir) {
        souvenir.setManufacturer(null);
        souvenirs.remove(souvenir);
    }

    public boolean isMakesSouvenirsCheaperThanValue(long price) {
        return souvenirs.size() > 0 && souvenirs.stream().filter(souvenir -> souvenir.getPrice() >= price).toList().size() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(o == this) return true;
        if(!(o instanceof Manufacturer manufacturer)) return false;
        return Objects.equals(name, manufacturer.name) && Objects.equals(country, manufacturer.country) && Objects.equals(id, manufacturer.id);
    }

    @Override
    public int hashCode() {
        var hash = 7;
        hash = 31 * hash + (name != null ? name.hashCode() : 0);
        hash = 31 * hash + (country != null ? country.hashCode() : 0);
        hash = 31 * hash + (id != null ? id.hashCode() : 0);
        return hash;
    }
}
