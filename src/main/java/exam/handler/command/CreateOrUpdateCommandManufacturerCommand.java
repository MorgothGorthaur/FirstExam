package exam.handler.command;

import exam.exception.ManufacturerValidationException;

import java.util.List;

public interface CreateOrUpdateCommandManufacturerCommand extends CreateOrUpdateCommand {
    default void checkArgs(List<String> args){
        var name = args.get(2);
        var country = args.get(3);
        if(name.equals("") || country.equals("")) throw new ManufacturerValidationException();
    }
}
