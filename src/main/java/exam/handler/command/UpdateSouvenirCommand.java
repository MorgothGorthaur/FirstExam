package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
@Component
@RequiredArgsConstructor
public class UpdateSouvenirCommand implements CreateOrUpdateCommandSouvenirCommand {
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
        checkArgs(Arrays.asList(args.get(1), args.get(3)));
        var souvenir = repository.getSouvenirById(Long.parseLong(args.get(0)));
        souvenir.setName(args.get(1));
        souvenir.setDate(LocalDate.parse(args.get(2)));
        souvenir.setPrice(Long.parseLong(args.get(3)));
        repository.updateSouvenir(souvenir);
        System.out.println("your souvenir: " + mapper.toSouvenirDto(souvenir));
    }
}
