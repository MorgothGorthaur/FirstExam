package exam;

import exam.model.Manufacturer;
import exam.model.Souvenir;

import java.time.LocalDate;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        var manufacturer = new Manufacturer(0L, "name", "country", new ArrayList<>());
        var souvenir = new Souvenir(0L, "name", LocalDate.now(), 4, null);
        manufacturer.addSouvenir(souvenir);
        System.out.println(souvenir.hashCode());
        System.out.println(souvenir);
    }
}
