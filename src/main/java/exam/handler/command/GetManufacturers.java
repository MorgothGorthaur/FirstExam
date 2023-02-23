package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetManufacturers implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "get_manufacturers";
    }

    @Override
    public void printUsage() {
        System.out.println("+\t" + getName() + " - for getting list of manufacturers\t\t\t\t\t\t\t +");
    }

    @Override
    public void execute(List<String> args) {
        repository.getManufacturers().stream().map(mapper::toManufacturerDto).forEach(System.out::println);
    }
}
