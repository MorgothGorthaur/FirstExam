package exam.handler.command;

import exam.exception.SouvenirNotFoundException;
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
    public void printUsage() {
        System.out.println("+\t" + getName() + " \"id\" - for removing souvenir\t\t\t\t\t\t\t\t\t +");
    }

    @Override
    public void execute(List<String> args) {
        var id = Long.parseLong(args.get(0));
        if(repository.getSouvenirById(id).isPresent()) repository.removeSouvenir(id);
        else throw new SouvenirNotFoundException(id);
    }
}
