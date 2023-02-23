package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.ManufacturedNotFoundException;
import exam.model.Manufacturer;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateManufacturer implements Command {
    private final Repository repository;
    private final Mapper mapper;

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
        if (repository.getManufacturerById(id).isPresent()) {
            var updated = new Manufacturer(id, args.get(1), args.get(2), new HashSet<>());
            repository.updateManufacturer(updated);
            System.out.println("your manufacturer: " + mapper.toManufacturerDto(updated));
        } else throw new ManufacturedNotFoundException(id);

    }
}
