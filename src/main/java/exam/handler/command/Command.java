package exam.handler.command;

import java.util.List;

public interface Command {
    String getName();

    void printUsage();

    void execute(List<String> args);
}
