package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetSouvenirsByCountry implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "get_souvenirs_by_country";
    }

    @Override
    public String getUsage() {
        return "get_souvenirs_by_country \"country\" - for getting souvenirs by country";
    }

    @Override
    public void execute(List<String> args) {
        repository.getSouvenirsByCountry(args.get(0)).stream().map(mapper::toSouvenirDto).forEach(System.out::println);
    }
}
