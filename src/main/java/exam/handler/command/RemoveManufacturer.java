package exam.handler.command;

import exam.exception.ManufacturedNotFoundException;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class RemoveManufacturer implements Command{
    private final Repository repository;
    @Override
    public String getName() {
        return "remove_manufacturer";
    }

    @Override
    public void printUsage() {
        System.out.println("+\t" + getName() + " \"id\" - for removing manufacturer\t\t\t\t\t\t\t +");
    }

    @Override
    public void execute(List<String> args) {
        repository.removeManufacturer(Long.parseLong(args.get(0)));
    }
}
