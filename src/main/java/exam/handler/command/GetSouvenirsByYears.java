package exam.handler.command;


import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetSouvenirsByYears implements Command {
    private final Repository repository;
    private final Mapper mapper;
    @Override
    public String getName() {
        return "get_souvenirs_by_years";
    }

    @Override
    public void printUsage() {
        System.out.println(getName() + " - for getting souvenirs by years");
    }

    @Override
    public void execute(List<String> args) {
        repository.getSouvenirsByYears().entrySet().stream()
                .map(entry ->entry.getKey() + " \n\t" +  entry.getValue().stream().map(mapper::toSouvenirDto).toList())
                .forEach(System.out::println);

    }
}
