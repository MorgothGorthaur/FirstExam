package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.ManufacturedNotFoundException;
import exam.model.Manufacturer;
import exam.repository.Repository;
import exam.repository.filehandler.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateManufacturer implements Command {
    private final Repository repository;
    private final Mapper mapper;

    private final FileHandler fileHandler;

    @Override
    public String getName() {
        return "update_manufacturer";
    }

    @Override
    public void printUsage() {
        System.out.println("""
                +\t%s "manufacturer id" "name" "country" - for updating\t\t\t +
                +\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tmanufacturer +""".replace("%s", getName()));
    }

    @Override
    public void execute(List<String> args) {
        var id = Long.parseLong(args.get(0));
        var updated = repository.getManufacturerById(id).orElseThrow(() -> new ManufacturedNotFoundException(id));
        updated.setName(args.get(1));
        updated.setCountry(args.get(2));
        fileHandler.saveManufacturer(updated);
        System.out.println("your manufacturer: " + mapper.toManufacturerDto(updated));
    }
}
