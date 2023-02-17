package exam.handler.command;

import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetManufacturersCheapestThenPrice implements Command {
    private final Repository repository;

    @Override
    public String getName() {
        return "get_manufacturers_with_souvenirs_cheaper_then";
    }

    @Override
    public void printUsage() {
        System.out.println(getName() + " \"price\" - for getting manufacturers that makes souvenirs cheaper then price");
    }

    @Override
    public void execute(List<String> args) {
        repository.getManufacturersThatMakesSouvenirsCheaperThenValue(Long.parseLong(args.get(1)))
                .forEach(System.out::println);
    }
}
