package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.ManufacturedNotFoundException;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetManufacturer implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "get_manufacturer";
    }

    @Override
    public void printUsage() {
        System.out.println(getName() + " \"id\" - for getting manufacturer");
    }

    @Override
    public void execute(List<String> args) {
        var id = Long.parseLong(args.get(0));
        var manufacturer = repository.getManufacturerById(id).orElseThrow(() -> new ManufacturedNotFoundException(id));
        System.out.println(mapper.toManufacturerFullDto(manufacturer));
    }
}
