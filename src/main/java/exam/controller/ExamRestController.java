package exam.controller;

import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;
import exam.service.Exam;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam")
@AllArgsConstructor
public class ExamRestController {
    private final Exam exam;

    @GetMapping("/manufacturers")
    public List<ManufacturerDto> getManufacturers() {
        return exam.getManufactures();
    }

    @GetMapping("/souvenirs")
    public List<SouvenirFullDto> getSouvenirs() {
        return exam.getSouvenirs();
    }

    @GetMapping("/manufacturers/{id}")
    public List<SouvenirDto> getManufacturersSouvenirs(@PathVariable Long id) {
        return exam.getSouvenirsByManufacturerId(id);
    }

    @DeleteMapping("/manufacturers/{id}")
    public void removeManufacturer(@PathVariable Long id) {
        exam.removeManufacturer(id);
    }

    @DeleteMapping("/souvenirs/{id}")
    public void removeSouvenir(@PathVariable Long id) {
        exam.removeSouvenir(id);
    }

    @PostMapping("/manufacturers")
    public void addManufacturer(@RequestBody ManufacturerDto dto) {
        exam.addManufacturer(dto);
    }

    @PostMapping("/manufacturers/{id}")
    public void addSouvenir(@PathVariable Long id, @RequestBody SouvenirDto dto) {
        exam.addSouvenir(id, dto);
    }

    @PatchMapping("/manufacturers")
    public void updateManufacturer(@RequestBody ManufacturerDto dto) {
        exam.updateManufacturer(dto);
    }

    @PatchMapping("/souvenirs")
    public void updateSouvenir(@RequestBody SouvenirDto dto) {
        exam.updateSouvenir(dto);
    }

    @GetMapping("/souvenirs/{country}")
    public List<SouvenirFullDto> getSouvenirsByCountry(@PathVariable String country) {
        return exam.getSouvenirsByCountry(country);
    }

    @GetMapping("/manufacturers/cheaper/{price}")
    public List<ManufacturerFullDto> getManufacturers(@PathVariable double price) {
        return exam.getManufacturerWithSouvenirsCheaperThatPrice(price);
    }

    @GetMapping("/manufacturers/souvenir")
    public List<ManufacturerDto> getManufacturers(@RequestBody SouvenirNameAndYearDto dto) {
        return exam.getManufacturersBySouvenirNameAndYear(dto.name(), dto.date());
    }

    @GetMapping("/souvenirs/years")
    public Map<LocalDate, List<SouvenirFullDto>> getSouvenirsByYear() {
        return exam.getSouvenirsByYear();
    }

    record SouvenirNameAndYearDto(@NonNull String name, @NonNull LocalDate date) {
    }
}


