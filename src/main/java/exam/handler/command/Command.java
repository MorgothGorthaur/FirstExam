package exam.handler.command;

import java.util.List;

public interface Command {
    String getName();

    String getUsage();

    void execute(List<String> args);
}
