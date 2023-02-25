package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.exception.SouvenirNotFoundException;
import exam.exception.SouvenirValidationException;
import exam.model.Souvenir;
import exam.repository.Repository;
import exam.repository.filehandler.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateSouvenir implements Command {
    private final Repository repository;
    private final Mapper mapper;
    private final FileHandler fileHandler;
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
        var name = args.get(1);
        var date = LocalDate.parse(args.get(2));
        var price = new BigDecimal(args.get(3));
        if(price.signum() < 0 || date.isAfter(LocalDate.now())) throw new SouvenirValidationException();
        var souvenir = repository.getSouvenirById(id).orElseThrow(() -> new SouvenirNotFoundException(id));
        souvenir.setName(name);
        souvenir.setDate(date);
        souvenir.setPrice(price);
        fileHandler.saveManufacturer(souvenir.getManufacturer());
        System.out.println("your souvenir: " + mapper.toSouvenirDto(souvenir));
    }
}
