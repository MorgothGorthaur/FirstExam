package exam.handler.command;

import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class RemoveSouvenir implements Command {
    private final Repository repository;

    @Override
    public String getName() {
        return "remove_souvenir";
    }

    @Override
    public String getUsage() {
        return "remove_souvenir \"id\" - for removing souvenir";
    }

    @Override
    public void execute(List<String> args) {
        repository.removeSouvenir(Integer.parseInt(args.get(0)));
    }
}
