package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.SouvenirNotFoundException;
import exam.exception.SouvenirValidationException;
import exam.model.Souvenir;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
@Component
@RequiredArgsConstructor
public class UpdateSouvenir implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "update_souvenir";
    }

    @Override
    public void printUsage() {
        System.out.println("+\t" + getName() + " \"souvenir id\" \"name\" \"date\" \"price\" - for updating souvenir\t\t +");
    }

    @Override
    public void execute(List<String> args) {
        var id = Long.parseLong(args.get(0));
        if(repository.getSouvenirById(id).isPresent()) {
            var souvenir = new Souvenir(id, args.get(1), LocalDate.parse(args.get(2)), Long.parseLong(args.get(3)), null);
            if (souvenir.getPrice() < 0 || souvenir.getDate().isAfter(LocalDate.now())) throw new SouvenirValidationException();
            repository.updateSouvenir(souvenir);
            System.out.println("your souvenir: " + mapper.toSouvenirDto(souvenir));
        } else throw new SouvenirNotFoundException(id);
    }
}
