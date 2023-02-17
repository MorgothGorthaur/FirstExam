package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetManufacturersByNameAndYear implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "get_manufacturers_by_name_and_year";
    }

    @Override
    public void printUsage() {
        System.out.println(getName() + " \"name\" \"year\" - for getting manufacturers by souvenir name and year");
    }

    @Override
    public void execute(List<String> args) {
        repository.getManufacturersBySouvenirNameAndYear(args.get(0), Integer.parseInt(args.get(1)))
                .stream().map(mapper::toManufacturerDto)
                .forEach(System.out::println);
    }
}
