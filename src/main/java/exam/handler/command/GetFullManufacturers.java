package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetFullManufacturers implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "get_full_manufacturers";
    }

    @Override
    public void printUsage() {
        System.out.println("+\t" + getName() + " - for getting manufacturers with souvenirs\t\t\t\t +");
    }

    @Override
    public void execute(List<String> args) {
        repository.getManufacturers().stream().map(mapper::toManufacturerFullDto).forEach(System.out::println);
    }
}
