package exam.handler.command;

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
        System.out.println(getName() + " \"id\" - for removing manufacturer");
    }

    @Override
    public void execute(List<String> args) {
        repository.removeManufacturer(Integer.parseInt(args.get(1)));
    }
}
