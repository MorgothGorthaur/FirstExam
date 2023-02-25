package exam.repository;

import exam.exception.ManufacturedNotFoundException;
import exam.exception.SouvenirNotFoundException;
import exam.model.Manufacturer;
import exam.model.Souvenir;
import exam.repository.filehandler.FileHandler;
import exam.repository.filehandler.FileHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RepositoryTest {
    private final FileHandler fileHandler = Mockito.mock(FileHandlerImpl.class);

    private Repository repository;

    @BeforeEach
    void setUp() {
        var firstManufacturer = new Manufacturer(0L, "first", "first country", new HashSet<>());
        var firstSouvenir = new Souvenir(0L, "first souvenir name", LocalDate.now(), new BigDecimal("5.5"), null);
        firstManufacturer.addSouvenir(firstSouvenir);
        var secondSouvenir = new Souvenir(1L, "second souvenir name", LocalDate.now(), new BigDecimal("6.5"), null);
        firstManufacturer.addSouvenir(secondSouvenir);
        var secondManufacturer = new Manufacturer(1L, "second", "second country", new HashSet<>());
        var thirstSouvenir = new Souvenir(2L, "third souvenir name", LocalDate.now(), new BigDecimal("7.5"), null);
        secondManufacturer.addSouvenir(thirstSouvenir);
        var fourthSouvenir = new Souvenir(3L, "fourth souvenir name", LocalDate.now(), new BigDecimal("8.5"), null);
        secondManufacturer.addSouvenir(fourthSouvenir);
        var manufacturersMap = Stream.of(firstManufacturer, secondManufacturer)
                .collect(Collectors.toMap(Manufacturer::getId, manufacturer -> manufacturer));
        when(fileHandler.readAll()).thenReturn(manufacturersMap);
        repository = new RepositoryImpl(fileHandler);
    }

    @Test
    void testGetManufacturers() {
        var expected = Arrays.asList(new Manufacturer(0L, "first", "first country", new HashSet<>()),
                new Manufacturer(1L, "second", "second country", new HashSet<>()));
        assertThat(repository.getManufacturers()).isEqualTo(expected);
    }

    @Test
    void testGetSouvenirs() {
        var expected = Arrays.asList(new Souvenir("first souvenir name", LocalDate.now(), new BigDecimal("5.5")),
                new Souvenir("second souvenir name", LocalDate.now(), new BigDecimal("6.5")),
                new Souvenir("third souvenir name", LocalDate.now(), new BigDecimal("7.5")),
                new Souvenir("fourth souvenir name", LocalDate.now(), new BigDecimal("8.5")));
        assertThat(repository.getSouvenirs()).isEqualTo(expected);

    }

    @Test
    void testRemoveManufacturer() {
        var expected = List.of(new Manufacturer(1L, "second", "second country", new HashSet<>()));
        repository.removeManufacturer(0L);
        assertThat(repository.getManufacturers()).isEqualTo(expected);
    }

    @Test
    void testRemoveSouvenir() {
        var expected = Arrays.asList(new Souvenir("second souvenir name", LocalDate.now(), new BigDecimal("6.5")),
                new Souvenir("third souvenir name", LocalDate.now(), new BigDecimal("7.5")),
                new Souvenir("fourth souvenir name", LocalDate.now(), new BigDecimal("8.5")));
        repository.removeSouvenir(0L);
        assertThat(repository.getSouvenirs()).isEqualTo(expected);
    }

    @Test
    void testAddManufacturer() {
        var manufacturer = new Manufacturer("new", "country");
        var expected = new Manufacturer(2L, "new", "country", new HashSet<>());
        repository.addManufacturer(manufacturer);
        assertThat(repository.getManufacturerById(2L)).isPresent().get().isEqualTo(expected);
    }

    @Test
    void testAddSouvenir() {
        var souvenir = new Souvenir("new", LocalDate.now(), new BigDecimal("6.5"));
        var expected = new Souvenir(4L, "new", LocalDate.now(), new BigDecimal("6.5"), null);
        repository.addSouvenir(souvenir);
        assertThat(repository.getSouvenirById(4L)).isPresent().get().isEqualTo(expected);
    }

    @Test
    void testGetManufacturerById() {
        var expected = new Manufacturer(0L, "first", "first country", new HashSet<>());
        assertThat(repository.getManufacturerById(0L)).isPresent().get().isEqualTo(expected);
    }

    @Test
    void testGetSouvenirById() {
        var souvenir = new Souvenir(0L, "first souvenir name", LocalDate.now(), new BigDecimal("5.5"), null);
        assertThat(repository.getSouvenirById(0L)).isPresent().get().isEqualTo(souvenir);
    }

    @Test
    void testGetManufacturersBySouvenirNameAndYear() {
        var expected = List.of(new Manufacturer(0L, "first", "first country", new HashSet<>()));
        assertThat(repository.getManufacturersBySouvenirNameAndYear("first souvenir name", LocalDate.now().getYear())).isEqualTo(expected);
    }

    @Test
    void testGetSouvenirsByCountry() {
        var expected = new TreeMap<Integer, List<Souvenir>>();
        expected.put(LocalDate.now().getYear(), Arrays.asList(new Souvenir(0L, "first souvenir name", LocalDate.now(), new BigDecimal("5.5"), null), new Souvenir(1L, "second souvenir name", LocalDate.now(), new BigDecimal("6.5"), null),
                new Souvenir(2L, "third souvenir name", LocalDate.now(), new BigDecimal("7.5"), null), new Souvenir(3L, "fourth souvenir name", LocalDate.now(), new BigDecimal("8.5"), null)));
        assertThat(repository.getSouvenirsByYears()).isEqualTo(expected);
    }


}