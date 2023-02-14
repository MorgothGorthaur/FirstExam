package exam.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    public Manufacturer(String name, String country) {
        this(null, name, country, new ArrayList<>());
    }

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
}
