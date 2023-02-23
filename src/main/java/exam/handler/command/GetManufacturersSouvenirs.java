package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.ManufacturedNotFoundException;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetManufacturersSouvenirs implements Command{
    private final Repository repository;
    private final Mapper mapper;
    @Override
    public String getName() {
        return "get_manufacturers_souvenirs";
    }

    @Override
    public void printUsage() {
        System.out.println("""
                           +\t%s "manufacturer id" - for getting souvenirs by \t\t +
                           +\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t manufacturer id +""".replace("%s", getName()));
    }

    @Override
    public void execute(List<String> args) {
        var id = Long.parseLong(args.get(0));
        var manufacturer = repository.getManufacturerById(id).orElseThrow(() -> new ManufacturedNotFoundException(id));
        manufacturer.getSouvenirs().stream().map(mapper::toSouvenirFullDto).forEach(System.out::println);
    }
}
