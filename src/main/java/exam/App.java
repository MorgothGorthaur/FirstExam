package exam;

import exam.handler.ManufacturerHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;


@RequiredArgsConstructor
public class App {
    ManufacturerHandler manufacturerHandler;
    BufferedReader reader;
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        new App().run();
    }

    @SneakyThrows
    private void run() {
        System.out.println(reader.readLine());
    }
}
