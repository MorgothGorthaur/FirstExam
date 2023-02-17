package exam.handler.command;

import java.util.List;

public interface CreateOrUpdateCommand extends Command{
    void checkArgs(List<String> args);
}
