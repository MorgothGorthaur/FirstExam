package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.SouvenirValidationException;
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
        System.out.println(getName() + " \"souvenir id\" \"name\" \"date\" \"price\" - for updating souvenir");
    }

    @Override
    public void execute(List<String> args) {
        var souvenir = repository.getSouvenirById(Long.parseLong(args.get(0)));
        souvenir.setName(args.get(1));
        souvenir.setDate(LocalDate.parse(args.get(2)));
        souvenir.setPrice(Long.parseLong(args.get(3)));
        if(souvenir.getName().equals("") || souvenir.getPrice() < 0 || souvenir.getDate().isAfter(LocalDate.now())) throw new SouvenirValidationException();
        repository.updateSouvenir(souvenir);
        System.out.println("your souvenir: " + mapper.toSouvenirDto(souvenir));
    }
}
