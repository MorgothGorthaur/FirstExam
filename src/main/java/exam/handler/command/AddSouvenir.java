package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.model.Souvenir;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
@Component
@RequiredArgsConstructor
public class AddSouvenir implements CreateOrUpdateCommandSouvenirCommand {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "add_souvenir";
    }

    @Override
    public String getUsage() {
        return "add_souvenir \"manufacturer id\" \"name\" \"date\" \"price\" - for adding souvenir";
    }

    @Override
    public void execute(List<String> args) {
        checkArgs(Arrays.asList(args.get(2), args.get(4)));
        var souvenir =     new Souvenir(args.get(2), LocalDate.parse(args.get(3)), Long.parseLong(args.get(4)));
        repository.addSouvenir(Long.parseLong(args.get(1)), souvenir);
        System.out.println("your souvenir: " + mapper.toSouvenirDto(souvenir));
    }
}
