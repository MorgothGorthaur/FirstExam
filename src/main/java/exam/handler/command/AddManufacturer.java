package exam.handler.command;


import exam.dto.mapper.Mapper;
import exam.model.Manufacturer;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddManufacturer implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "add_manufacturer";
    }

    @Override
    public void printUsage() {
        System.out.println("+\t" + getName() + " \"name\" \"country\" - for adding manufacturer\t\t\t\t\t\t +");
    }

    @Override
    public void execute(List<String> args) {
        var manufacturer = new Manufacturer(args.get(0), args.get(1));
        repository.addManufacturer(manufacturer);
        System.out.println("your manufacturer: " + mapper.toManufacturerDto(manufacturer));
    }
}
