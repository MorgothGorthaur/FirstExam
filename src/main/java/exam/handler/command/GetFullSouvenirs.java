package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetFullSouvenirs implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "get_full_souvenirs";
    }

    @Override
    public String getUsage() {
        return getName() + " - for getting souvenirs with manufacturers";
    }

    @Override
    public void execute(List<String> args) {
        repository.getSouvenirs().stream().map(mapper::toSouvenirFullDto).forEach(System.out::println);
    }
}
