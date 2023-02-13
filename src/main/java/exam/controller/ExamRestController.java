package exam.controller;

import exam.dao.Dao;
import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
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
@RequestMapping("/exam")
@AllArgsConstructor
public class ExamRestController {
    private final Dao dao;
    private final Mapper mapper;
    @GetMapping("/manufacturers")
    public List<ManufacturerDto> getManufacturers() {
        return dao.getManufacturers().stream().map(mapper::toManufacturerDto).toList();
    }

    @GetMapping("/souvenirs")
    public List<SouvenirFullDto> getSouvenirs() {
        return dao.getSouvenirs().stream().map(mapper::toSouvenirFullDto).toList();
    }

    @GetMapping("/manufacturers/{id}")
    public List<SouvenirDto> getManufacturersSouvenirs(@PathVariable Long id) {
        return dao.getManufacturersSouvenirs(id).stream().map(mapper::toSouvenirDto).toList();
    }

    @DeleteMapping("/manufacturers/{id}")
    public void removeManufacturer(@PathVariable Long id) {
        dao.removeManufacturer(id);
    }

    @DeleteMapping("/souvenirs/{id}")
    public void removeSouvenir(@PathVariable Long id) {
        dao.removeSouvenir(id);
    }

    @PostMapping("/manufacturers")
    public ManufacturerFullDto addManufacturer(@RequestBody ManufacturerDto dto) {
        return mapper.toManufacturerFullDto(dao.addManufacturer(dto.toManufacturer()));
    }

    @PostMapping("/manufacturers/{id}")
    public ManufacturerFullDto addSouvenir(@PathVariable Long id, @RequestBody SouvenirDto dto) {
        return mapper.toManufacturerFullDto(dao.addSouvenir(id, dto.toSouvenir()));
    }

    @PatchMapping("/manufacturers")
    public void updateManufacturer(@RequestBody ManufacturerDto dto) {dao.updateManufacturer(dto.toManufacturer());}

    @PatchMapping("/souvenirs")
    public void updateSouvenir(@RequestBody SouvenirDto dto) {
        dao.updateSouvenir(dto.toSouvenir());
    }

    @GetMapping("/souvenirs/{country}")
    public List<SouvenirFullDto> getSouvenirsByCountry(@PathVariable String country) {
        return dao.getSouvenirs().stream().filter(souvenir -> souvenir.getManufacturer().getCountry().equals(country))
                .map(mapper::toSouvenirFullDto).toList();
    }

    @GetMapping("/manufacturers/cheaper/{price}")
    public List<ManufacturerFullDto> getManufacturers(@PathVariable double price) {
        return dao.getManufacturers().stream()
                .filter(manufacturer -> manufacturer.isMakesSouvenirsCheaperThanValue(price))
                .map(mapper::toManufacturerFullDto).toList();
    }

    @GetMapping("/manufacturers/souvenir")
    public List<ManufacturerDto> getManufacturers(@RequestBody SouvenirNameAndYearDto dto) {
        return dao.getSouvenirs().stream()
                .filter(souvenir -> souvenir.getName().equals(dto.name()) && souvenir.getDate().getYear() == dto.date().getYear())
                .map(souvenir -> mapper.toManufacturerDto(souvenir.getManufacturer())).toList();
    }

    @GetMapping("/souvenirs/years")
    public Map<Integer, List<SouvenirFullDto>> getSouvenirsByYear() {
        var map = new HashMap<Integer, List<SouvenirFullDto>>();
        for (var s : dao.getSouvenirs()) {
            var list = map.get(s.getDate().getYear());
            if (list != null) list.add(mapper.toSouvenirFullDto(s));
            else map.put(s.getDate().getYear(), new ArrayList<>(List.of(mapper.toSouvenirFullDto(s))));
        }
        return map;
    }

    record SouvenirNameAndYearDto(@NonNull String name, @NonNull LocalDate date) {
    }
}


