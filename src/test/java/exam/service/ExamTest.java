package exam.service;

import exam.dao.Dao;
import exam.dao.DaoImpl;
import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;
import exam.model.Manufacturer;
import exam.model.Souvenir;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ExamTest {

    private final Dao dao = Mockito.mock(DaoImpl.class);
    private Exam exam;

    @BeforeEach
    void setUp() {
        var firstManufacturer = new Manufacturer(0L, "first", "first country", new ArrayList<>());
        var firstSouvenir = new Souvenir(0L, "first souvenir name", LocalDate.now(), 5, null);
        firstManufacturer.addSouvenir(firstSouvenir);
        var secondSouvenir = new Souvenir(1L, "second souvenir name", LocalDate.now(), 6, null);
        firstManufacturer.addSouvenir(secondSouvenir);
        var secondManufacturer = new Manufacturer(1L, "second", "second country", new ArrayList<>());
        var thirstSouvenir = new Souvenir(2L, "third souvenir name", LocalDate.now(), 7, null);
        secondManufacturer.addSouvenir(thirstSouvenir);
        var fourthSouvenir = new Souvenir(3L, "fourth souvenir name", LocalDate.now(), 8, null);
        secondManufacturer.addSouvenir(fourthSouvenir);
        var manufacturersMap = Stream.of(firstManufacturer, secondManufacturer)
                .collect(Collectors.toMap(Manufacturer::getId, manufacturer -> manufacturer));
        var souvenirsMap = Stream.of(firstSouvenir, secondSouvenir, thirstSouvenir, fourthSouvenir)
                .collect(Collectors.toMap(Souvenir::getId, souvenir -> souvenir));
        when(dao.getManufacturers()).thenReturn(manufacturersMap);
        when(dao.getSouvenirs()).thenReturn(souvenirsMap);
        exam = new ExamImpl(dao);
    }

    @AfterEach
    void remove() {
        exam = null;
    }


    @Test
    void getManufactures() {
        var expected = Arrays.asList(new ManufacturerDto(0L, "first", "first country"),
                new ManufacturerDto(1L, "second", "second country"));
        assertThat(exam.getManufactures()).isEqualTo(expected);
    }

    @Test
    void getSouvenirsByManufacturerId() {
        var expected = Arrays.asList(new SouvenirDto(0L, "first souvenir name", 5, LocalDate.now()),
                new SouvenirDto(1L, "second souvenir name", 6, LocalDate.now()));
        assertThat(exam.getSouvenirsByManufacturerId(0L)).isEqualTo(expected);
    }

    @Test
    void removeManufacturer() {
        var expected = List.of(new ManufacturerDto(1L, "second", "second country"));
        exam.removeManufacturer(0L);
        assertThat(exam.getManufactures()).isEqualTo(expected);
    }

    @Test
    void removeSouvenir() {
        var expected = Arrays.asList(new SouvenirDto(1L, "second souvenir name", 6, LocalDate.now()),
                new SouvenirDto(2L, "third souvenir name", 7, LocalDate.now()),
                new SouvenirDto(3L, "fourth souvenir name", 8, LocalDate.now()));
        exam.removeSouvenir(0L);
        assertThat(exam.getSouvenirs()).isEqualTo(expected);
    }

    @Test
    void updateSouvenir() {
        var updated = new SouvenirDto(0L, "updated", 4, LocalDate.now());
        var expected = Arrays.asList(
                new SouvenirDto(0L, "updated", 4, LocalDate.now()),
                new SouvenirDto(1L, "second souvenir name", 6, LocalDate.now()),
                new SouvenirDto(2L, "third souvenir name", 7, LocalDate.now()),
                new SouvenirDto(3L, "fourth souvenir name", 8, LocalDate.now()));
        exam.updateSouvenir(updated);
        assertThat(exam.getSouvenirs()).isEqualTo(expected);
    }

    @Test
    void updateManufacturer() {
        var updated = new ManufacturerDto(0L, "updated", "country");
        var expected = Arrays.asList(
                new ManufacturerDto(0L, "updated", "country"),
                new ManufacturerDto(1L, "second", "second country"));
        exam.updateManufacturer(updated);
        assertThat(exam.getManufactures()).isEqualTo(expected);
    }

    @Test
    void addSouvenir() {
        var newSouvenir = new SouvenirDto(null, "new", 4, LocalDate.now());
        var expected = Arrays.asList(
                new SouvenirDto(0L, "first souvenir name", 5, LocalDate.now()),
                new SouvenirDto(1L, "second souvenir name", 6, LocalDate.now()),
                new SouvenirDto(4L, "new", 4, LocalDate.now()));
        exam.addSouvenir(0L, newSouvenir);
        assertThat(exam.getSouvenirsByManufacturerId(0L)).isEqualTo(expected);
    }

    @Test
    void addManufacturer() {
        var newManufacturer = new ManufacturerDto(null, "new", "third country");
        var expected = Arrays.asList(
                new ManufacturerDto(0L, "first", "first country"),
                new ManufacturerDto(1L, "second", "second country"),
                new ManufacturerDto(2L, "new", "third country"));
        exam.addManufacturer(newManufacturer);
        assertThat(exam.getManufactures()).isEqualTo(expected);
    }

    @Test
    void testGetSouvenirsByManufacturerId() {
        var expected = Arrays.asList(
                new SouvenirDto(0L, "first souvenir name", 5, LocalDate.now()),
                new SouvenirDto(1L, "second souvenir name", 6, LocalDate.now()));
        assertThat(exam.getSouvenirsByManufacturerId(0L)).isEqualTo(expected);
    }

    @Test
    void getSouvenirsByCountry() {
        var expected = Arrays.asList(
                new SouvenirFullDto(0L, "first souvenir name", 5, LocalDate.now(),
                        new ManufacturerDto(0L, "first", "first country")),
                new SouvenirFullDto(1L, "second souvenir name", 6, LocalDate.now(),
                        new ManufacturerDto(0L, "first", "first country")));
        assertThat(exam.getSouvenirsByCountry("first country")).isEqualTo(expected);
    }

    @Test
    void getManufacturerWithSouvenirsCheaperThatPrice() {
        var expected = List.of(new ManufacturerFullDto(0L, "first", "first country",
                Arrays.asList(
                        new SouvenirDto(0L, "first souvenir name", 5, LocalDate.now()),
                        new SouvenirDto(1L, "second souvenir name", 6, LocalDate.now()))
        ));
        assertThat(exam.getManufacturerWithSouvenirsCheaperThatPrice(7)).isEqualTo(expected);
    }

    @Test
    void getManufacturersBySouvenirNameThatWasMadeThisYear() {
        var expected = List.of(new ManufacturerDto(0L, "first", "first country"));
        assertThat(exam.getManufacturersBySouvenirNameThatWasMadeThisYear("first souvenir name")).isEqualTo(expected);
    }

    @Test
    void getSouvenirsByYear() {
        var expected = new HashMap<LocalDate, List<SouvenirFullDto>>();
        var list = Arrays.asList(
                new SouvenirFullDto(0L, "first souvenir name", 5, LocalDate.now(),
                        new ManufacturerDto(0L, "first", "first country")),
                new SouvenirFullDto(1L, "second souvenir name", 6, LocalDate.now(),
                        new ManufacturerDto(0L, "first", "first country")),
                new SouvenirFullDto(2L, "third souvenir name", 7, LocalDate.now(),
                        new ManufacturerDto(1L, "second", "second country")),
                new SouvenirFullDto(3L, "fourth souvenir name", 8, LocalDate.now(),
                        new ManufacturerDto(1L, "second", "second country")));
        expected.put(LocalDate.now(), list);
        assertThat(exam.getSouvenirsByYear()).isEqualTo(expected);
    }
}