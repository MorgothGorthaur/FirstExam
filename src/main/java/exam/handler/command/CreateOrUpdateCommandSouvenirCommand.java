package exam.handler.command;

import exam.exception.SouvenirValidationException;

import java.time.LocalDate;
import java.util.List;

public interface CreateOrUpdateCommandSouvenirCommand extends CreateOrUpdateCommand {
    default void checkArgs(List<String> args) {
        var name = args.get(0);
        var date = LocalDate.parse(args.get(1));
        var price = Long.parseLong(args.get(2));
        if(name.equals("") || price < 0 || date.isAfter(LocalDate.now())) throw new SouvenirValidationException();
    }
}
