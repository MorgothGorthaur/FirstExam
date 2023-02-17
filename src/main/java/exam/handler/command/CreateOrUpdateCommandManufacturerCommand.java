package exam.handler.command;

import exam.exception.ManufacturerValidationException;

import java.util.List;

public interface CreateOrUpdateCommandManufacturerCommand extends CreateOrUpdateCommand {
    default void checkArgs(List<String> args){
        var name = args.get(0);
        var country = args.get(1);
        if(name.equals("") || country.equals("")) throw new ManufacturerValidationException();
    }
}
