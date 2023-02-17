package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
@RequiredArgsConstructor
public class UpdateManufacturerCommand implements CreateOrUpdateCommandManufacturerCommand {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "update_manufacturer";
    }

    @Override
    public String getUsage() {
        return "update_manufacturer \"manufacturer id\" \"name\" \"country\" - for updating manufacturer";
    }

    @Override
    public void execute(List<String> args) {
        checkArgs(Arrays.asList(args.get(2), args.get(3)));
        var updated = repository.getManufacturerById(Long.parseLong(args.get(1)));
        updated.setName(args.get(2));
        updated.setCountry(args.get(3));
        repository.updateManufacturer(updated);
        System.out.println("your manufacturer: " + mapper.toManufacturerDto(updated));
    }
}
