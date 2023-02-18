package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.SouvenirValidationException;
import exam.model.Souvenir;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
@Component
@RequiredArgsConstructor
public class AddSouvenir implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "add_souvenir";
    }

    @Override
    public void printUsage() {
        System.out.println(getName() + " \"manufacturer id\" \"name\" \"date\" \"price\" - for adding souvenir");
    }

    @Override
    public void execute(List<String> args) {
        var souvenir = new Souvenir(args.get(1), LocalDate.parse(args.get(2)), Long.parseLong(args.get(3)));
        if(souvenir.getName().equals("") || souvenir.getPrice() < 0 || souvenir.getDate().isAfter(LocalDate.now())) throw new SouvenirValidationException();
        repository.addSouvenir(Long.parseLong(args.get(0)), souvenir);
        System.out.println("your souvenir: " + mapper.toSouvenirDto(souvenir));
    }
}
