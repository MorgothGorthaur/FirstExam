package exam.handler.command;

import exam.dto.mapper.Mapper;
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
    public String getUsage() {
        return "update_souvenir \"souvenir id\" \"name\" \"date\" \"price\" - for updating souvenir";
    }

    @Override
    public void execute(List<String> args) {
        var souvenir = repository.getSouvenirById(Long.parseLong(args.get(1)));
        souvenir.setName(args.get(2));
        souvenir.setDate(LocalDate.parse(args.get(3)));
        souvenir.setPrice(Long.parseLong(args.get(4)));
        System.out.println("your souvenir: " + mapper.toSouvenirDto(repository.updateSouvenir(souvenir)));
    }
}
