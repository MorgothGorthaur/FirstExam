package exam.handler.command;


import exam.dto.mapper.Mapper;
import exam.model.Manufacturer;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
@RequiredArgsConstructor
public class AddManufacturer implements CreateOrUpdateCommandManufacturerCommand {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "add_manufacturer";
    }

    @Override
    public String getUsage() {
        return getName() + " \"name\" \"country\" - for adding manufacturer";
    }

    @Override
    public void execute(List<String> args) {
        checkArgs(Arrays.asList(args.get(1), args.get(2)));
        var manufacturer = new Manufacturer(args.get(1), args.get(2));
        repository.addManufacturer(manufacturer);
        System.out.println("your manufacturer: " + mapper.toManufacturerDto(manufacturer));
    }
}
