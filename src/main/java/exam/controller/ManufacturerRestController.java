package exam.controller;

import exam.dao.Dao;
import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.dto.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam/manufacturers")
@AllArgsConstructor
public class ManufacturerRestController {
    private final Dao dao;
    private final Mapper mapper;

    @GetMapping
    public List<ManufacturerDto> getManufacturers() {
        return dao.getManufacturers().stream().map(mapper::toManufacturerDto).toList();
    }

    @GetMapping("/{id}")
    public List<SouvenirDto> getManufacturersSouvenirs(@PathVariable Long id) {
        return dao.getManufacturersSouvenirs(id).stream().map(mapper::toSouvenirDto).toList();
    }

    @DeleteMapping("/{id}")
    public void removeManufacturer(@PathVariable Long id) {
        dao.removeManufacturer(id);
    }

    @PostMapping
    public ManufacturerFullDto addManufacturer(@RequestBody ManufacturerDto dto) {
        return mapper.toManufacturerFullDto(dao.addManufacturer(dto.toManufacturer()));
    }

    @PostMapping("/{id}")
    public ManufacturerFullDto addSouvenir(@PathVariable Long id, @RequestBody SouvenirDto dto) {
        return mapper.toManufacturerFullDto(dao.addSouvenir(id, dto.toSouvenir()));
    }

    @PatchMapping
    public void updateManufacturer(@RequestBody ManufacturerDto dto) {
        dao.updateManufacturer(dto.toManufacturer());
    }

    @GetMapping("/cheaper/{price}")
    public List<ManufacturerFullDto> getManufacturers(@PathVariable double price) {
        return dao.getManufacturers().stream()
                .filter(manufacturer -> manufacturer.isMakesSouvenirsCheaperThanValue(price))
                .map(mapper::toManufacturerFullDto).toList();
    }


}
