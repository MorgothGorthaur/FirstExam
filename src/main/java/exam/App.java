package exam;

import exam.handler.ConsoleCommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@RequiredArgsConstructor
@SpringBootApplication
public class App implements CommandLineRunner {
    private final ConsoleCommandHandler consoleCommandHandler;
    private final ConfigurableApplicationContext context;
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Override
    @SneakyThrows
    public void run(String... args) {
        consoleCommandHandler.menuHandler();
        SpringApplication.exit(context);
    }


}
