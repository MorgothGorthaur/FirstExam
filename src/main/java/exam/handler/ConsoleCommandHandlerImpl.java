package exam.handler;

import exam.handler.command.Command;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class ConsoleCommandHandlerImpl implements ConsoleCommandHandler {

    private final BufferedReader reader;
    private final Map<String, Command> commands;

    public ConsoleCommandHandlerImpl(BufferedReader reader, List<Command> commands) {
        this.reader = reader;
        this.commands = commands.stream().collect(Collectors.toMap(Command::getName, Function.identity()));
    }
    @SneakyThrows
    @Override
    public void handleMenu() {
        var line = "";
        printMenu();
        while (!(line = reader.readLine()).equals("exit")) handleCommand(line);
    }

    private void handleCommand(String line) {
        try {
            var args = Arrays.asList(line.strip().replaceAll("\\s+", " ").split(" "));
            var command = commands.get(args.get(0));
            if (command != null) command.execute(args.subList(1, args.size()));
            else printMenu();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printMenu() {
        commands.values().forEach(Command::printUsage);
        System.out.println("exit - for exit");
    }
}
