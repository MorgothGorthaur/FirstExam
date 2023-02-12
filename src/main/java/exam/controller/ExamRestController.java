package exam.controller;

import exam.dto.ManufacturerDto;
import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;
import exam.service.Exam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/manufactures/{id}")
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


}
