package exam.handler.command;

import exam.exception.SouvenirValidationException;

import java.util.List;

public interface CreateOrUpdateCommandSouvenirCommand extends CreateOrUpdateCommand {
    default void checkArgs(List<String> args) {
        var name = args.get(2);
        var price = Long.parseLong(args.get(4));
        if(name.equals("") && price < 0) throw new SouvenirValidationException();
    }
}
