package exam.controller;

import exam.dao.Dao;
import exam.dto.ManufacturerDto;
import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;
import exam.dto.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam/souvenirs")
@AllArgsConstructor
public class SouvenirRestController {
    private final Dao dao;
    private final Mapper mapper;

    @GetMapping
    public List<SouvenirFullDto> getSouvenirs() {
        return dao.getSouvenirs().stream().map(mapper::toSouvenirFullDto).toList();
    }


    @DeleteMapping("/{id}")
    public void removeSouvenir(@PathVariable Long id) {
        dao.removeSouvenir(id);
    }


    @PatchMapping
    public void updateSouvenir(@RequestBody SouvenirDto dto) {
        dao.updateSouvenir(dto.toSouvenir());
    }

    @GetMapping("/{country}")
    public List<SouvenirFullDto> getSouvenirsByCountry(@PathVariable String country) {
        return dao.getSouvenirs().stream().filter(souvenir -> souvenir.getManufacturer().getCountry().equals(country))
                .map(mapper::toSouvenirFullDto).toList();
    }


    @GetMapping("/years")
    public Map<Integer, List<SouvenirFullDto>> getSouvenirsByYear() {
        var map = new HashMap<Integer, List<SouvenirFullDto>>();
        for (var s : dao.getSouvenirs()) {
            var list = map.get(s.getDate().getYear());
            if (list != null) list.add(mapper.toSouvenirFullDto(s));
            else map.put(s.getDate().getYear(), new ArrayList<>(List.of(mapper.toSouvenirFullDto(s))));
        }
        return map;
    }

    @GetMapping("/name_and_year")
    public List<ManufacturerDto> getManufacturers(@RequestBody SouvenirNameAndYearDto dto) {
        return dao.getSouvenirs().stream()
                .filter(souvenir -> souvenir.getName().equals(dto.name()) && souvenir.getDate().getYear() == dto.date().getYear())
                .map(souvenir -> mapper.toManufacturerDto(souvenir.getManufacturer())).toList();
    }

    record SouvenirNameAndYearDto(@NonNull String name, @NonNull LocalDate date) {
    }

}


