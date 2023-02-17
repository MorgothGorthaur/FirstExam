package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetSouvenirs implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "get_souvenirs";
    }

    @Override
    public void printUsage() {
        System.out.println(getName() + " - for getting souvenirs");
    }

    @Override
    public void execute(List<String> args) {
        repository.getSouvenirs().stream().map(mapper::toSouvenirDto).forEach(System.out::println);
    }
}
