package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.ManufacturedNotFoundException;
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
        System.out.println("+\t" + getName() + " \"manufacturer id\" \"name\" \"date\" \"price\" - for adding souvenir\t\t +");
    }

    @Override
    public void execute(List<String> args) {
        var manufacturerId = Long.parseLong(args.get(0));
        var souvenir = new Souvenir(args.get(1), LocalDate.parse(args.get(2)), Long.parseLong(args.get(3)));
        if(souvenir.getPrice() < 0 || souvenir.getDate().isAfter(LocalDate.now())) throw new SouvenirValidationException();
        var manufacturer = repository.getManufacturerById(manufacturerId)
                .orElseThrow(() -> new ManufacturedNotFoundException(manufacturerId));
        manufacturer.addSouvenir(souvenir);
        repository.addSouvenir(souvenir);
        System.out.println("your souvenir: " + mapper.toSouvenirDto(souvenir));
    }
}
