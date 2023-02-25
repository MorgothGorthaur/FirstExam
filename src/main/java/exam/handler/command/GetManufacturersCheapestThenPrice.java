package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
@RequiredArgsConstructor
public class GetManufacturersCheapestThenPrice implements Command {
    private final Repository repository;
    private final Mapper mapper;
    @Override
    public String getName() {
        return "get_manufacturers_with_souvenirs_cheaper_then";
    }

    @Override
    public void printUsage() {
        System.out.println("""
                              +\t%s "price" - for getting \t\t\t +
                              +\t\t\t\t\t\t\t   manufacturers that makes souvenirs cheaper then price +""".replace("%s", getName()));
    }

    @Override
    public void execute(List<String> args) {
        repository.getManufacturersThatMakesSouvenirsCheaperThenValue(new BigDecimal(args.get(0)))
                .stream().map(mapper::toManufacturerDto)
                .forEach(System.out::println);
    }
}
