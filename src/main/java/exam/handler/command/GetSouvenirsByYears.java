package exam.handler.command;


import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetSouvenirsByYears implements Command {
    private final Repository repository;

    @Override
    public String getName() {
        return "get_souvenirs_by_years";
    }

    @Override
    public String getUsage() {
        return getName() + " - for getting souvenirs by years";
    }

    @Override
    public void execute(List<String> args) {
        System.out.println(repository.getSouvenirsByYears());
    }
}
