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

    @GetMapping
    public List<ManufacturerDto> getManufacturers() {
        return exam.getManufactures();
    }

    @GetMapping
    public List<SouvenirFullDto> getSouvenirs() {
        return exam.getSouvenirs();
    }

    @GetMapping("/{id}")
    public List<SouvenirDto> getManufacturersSouvenirs(@PathVariable Long id) {
        return exam.getSouvenirsByManufacturerId(id);
    }

}
