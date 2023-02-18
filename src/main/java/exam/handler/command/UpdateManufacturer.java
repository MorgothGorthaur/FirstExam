package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.ManufacturerValidationException;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        System.out.println(getName() + " \"manufacturer id\" \"name\" \"country\" - for updating manufacturer");
    }

    @Override
    public void execute(List<String> args) {
        var updated = repository.getManufacturerById(Long.parseLong(args.get(0)));
        updated.setName(args.get(1));
        updated.setCountry(args.get(2));
        repository.updateManufacturer(updated);
        System.out.println("your manufacturer: " + mapper.toManufacturerDto(updated));
    }
}
