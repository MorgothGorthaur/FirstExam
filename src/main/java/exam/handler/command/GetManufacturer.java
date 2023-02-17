package exam.handler.command;

import exam.dto.mapper.Mapper;
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
    public String getUsage() {
        return getName() + " \"id\" - for getting manufacturer";
    }

    @Override
    public void execute(List<String> args) {
        System.out.println(mapper.toManufacturerFullDto(repository.getManufacturerById(Integer.parseInt(args.get(1)))));
    }
}
