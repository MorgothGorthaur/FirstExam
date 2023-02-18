package exam.handler.command;


import exam.dto.mapper.Mapper;
import exam.exception.ManufacturerValidationException;
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
        System.out.println(getName() + " \"name\" \"country\" - for adding manufacturer");
    }

    @Override
    public void execute(List<String> args) {
        if(args.get(0).equals("") || args.get(1).equals("")) throw new ManufacturerValidationException();
        var manufacturer = new Manufacturer(args.get(0), args.get(1));
        repository.addManufacturer(manufacturer);
        System.out.println("your manufacturer: " + mapper.toManufacturerDto(manufacturer));
    }
}
