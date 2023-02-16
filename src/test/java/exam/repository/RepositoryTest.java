package exam.repository;

import exam.model.Manufacturer;
import exam.model.Souvenir;
import exam.repository.dao.Dao;
import exam.repository.dao.DaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class RepositoryTest {
    private final Dao dao = Mockito.mock(DaoImpl.class);

    private Repository repository;

    @BeforeEach
    void setUp() {
        var firstManufacturer = new Manufacturer(0L, "first", "first country", new HashSet<>());
        var firstSouvenir = new Souvenir(0L, "first souvenir name", LocalDate.now(), 5, null);
        firstManufacturer.addSouvenir(firstSouvenir);
        var secondSouvenir = new Souvenir(1L, "second souvenir name", LocalDate.now(), 6, null);
        firstManufacturer.addSouvenir(secondSouvenir);
        var secondManufacturer = new Manufacturer(1L, "second", "second country", new HashSet<>());
        var thirstSouvenir = new Souvenir(2L, "third souvenir name", LocalDate.now(), 7, null);
        secondManufacturer.addSouvenir(thirstSouvenir);
        var fourthSouvenir = new Souvenir(3L, "fourth souvenir name", LocalDate.now(), 8, null);
        secondManufacturer.addSouvenir(fourthSouvenir);
        var manufacturersMap = Stream.of(firstManufacturer, secondManufacturer)
                .collect(Collectors.toMap(Manufacturer::getId, manufacturer -> manufacturer));
        var souvenirsMap = Stream.of(firstSouvenir, secondSouvenir, thirstSouvenir, fourthSouvenir)
                .collect(Collectors.toMap(Souvenir::getId, souvenir -> souvenir));
        when(dao.readAll()).thenReturn(manufacturersMap);
        repository = new RepositoryImpl(dao);
    }

    @Test
    void getManufacturers() {
        var expected = Arrays.asList(new Manufacturer(0L, "first", "first country", new HashSet<>()),
                new Manufacturer(1L, "second", "second country", new HashSet<>()));
        assertThat(repository.getManufacturers()).isEqualTo(expected);
    }

    @Test
    void getSouvenirs() {
        var expected = Arrays.asList(new Souvenir("first souvenir name", LocalDate.now(), 5),
                new Souvenir("second souvenir name", LocalDate.now(), 6),
                new Souvenir("third souvenir name", LocalDate.now(), 7),
                new Souvenir("fourth souvenir name", LocalDate.now(), 8));
        assertThat(repository.getSouvenirs()).isEqualTo(expected);

    }

    @Test
    void removeManufacturer() {
        var expected = List.of(new Manufacturer(1L, "second", "second country", new HashSet<>()));
        repository.removeManufacturer(0L);
        assertThat(repository.getManufacturers()).isEqualTo(expected);
    }

    @Test
    void removeSouvenir() {
        var expected = Arrays.asList(new Souvenir( "second souvenir name", LocalDate.now(), 6),
                new Souvenir("third souvenir name", LocalDate.now(), 7),
                new Souvenir( "fourth souvenir name", LocalDate.now(), 8));
        repository.removeSouvenir(0L);
        assertThat(repository.getSouvenirs()).isEqualTo(expected);
    }

    @Test
    void updateManufacturer() {
        var updated = new Manufacturer(0L, "updated", "country", new HashSet<>());
        var res = repository.updateManufacturer(updated);
        assertThat(res).isEqualTo(updated);
    }

    @Test
    void updateSouvenir() {
        var updated = new Souvenir( 0L,"updated",  LocalDate.now(),4, null);
        var res = repository.updateSouvenir(updated);
        assertThat(res).isEqualTo(updated);
    }

    @Test
    void addManufacturer() {
        var manufacturer = new Manufacturer("new", "country");
        var expected = new Manufacturer(2L, "new", "country", null);
        assertThat(repository.addManufacturer(manufacturer)).isEqualTo(expected);
    }

    @Test
    void addSouvenir() {
        var souvenir = new Souvenir("new", LocalDate.now(), 6);
        var expected = new Souvenir(5L, "new",  LocalDate.now(), 6, new Manufacturer(0L, "first", "first country", new HashSet<>()));
        assertThat(repository.addSouvenir(0L, souvenir)).isEqualTo(expected);
    }

    @Test
    void getManufacturerById() {
        var expected =  new Manufacturer(0L, "first", "first country", new HashSet<>());
        assertThat(repository.getManufacturerById(0L)).isEqualTo(expected);
    }

    @Test
    void getSouvenirById() {
        var souvenir = new Souvenir(0L, "first souvenir name", LocalDate.now(), 5, null);
        assertThat(repository.getSouvenirById(0L)).isEqualTo(souvenir);
    }

    @Test
    void getManufacturersBySouvenirNameAndYear() {
        var expected = new HashSet<>(List.of(new Manufacturer(0L, "first", "first country", new HashSet<>())));
        assertThat(repository.getManufacturersBySouvenirNameAndYear("first souvenir name", LocalDate.now().getYear())).isEqualTo(expected);
    }

    @Test
    void getSouvenirsByCountry() {
        var expected = new TreeMap<Integer, List<Souvenir>>();
        expected.put(LocalDate.now().getYear(), Arrays.asList(new Souvenir(0L, "first souvenir name", LocalDate.now(), 5, null),new Souvenir(1L, "second souvenir name", LocalDate.now(), 6, null),
                new Souvenir(2L, "third souvenir name", LocalDate.now(), 7, null), new Souvenir(3L, "fourth souvenir name", LocalDate.now(), 8, null)));
        assertThat(repository.getSouvenirsByYears()).isEqualTo(expected);
    }
}