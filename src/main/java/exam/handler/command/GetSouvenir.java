package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.SouvenirNotFoundException;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetSouvenir implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "get_souvenir";
    }

    @Override
    public void printUsage() {
        System.out.println("+\t" + getName() + " \"souvenir id\" -  for getting souvenir\t\t\t\t\t\t\t\t +");
    }

    @Override
    public void execute(List<String> args) {
        var id= Long.parseLong(args.get(0));
        var souvenir = repository.getSouvenirById(id).orElseThrow(() -> new SouvenirNotFoundException(id));
        System.out.println(mapper.toSouvenirFullDto(souvenir));
    }
}
