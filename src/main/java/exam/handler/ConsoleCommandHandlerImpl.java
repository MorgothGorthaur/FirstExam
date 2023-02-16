package exam.handler;

import exam.handler.command.Command;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
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
        commands.values().stream().map(Command::getName).forEach(System.out::println);
        while (!(line = reader.readLine()).equals("exit")) {


        }
    }


}
