package exam.handler.command;

import exam.dto.mapper.Mapper;
import exam.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetSouvenir implements Command {
    private final Repository repository;
    private final Mapper mapper;

    @Override
    public String getName() {
        return "get_souvenir";
    }

    @Override
    public String getUsage() {
        return "get_souvenir \"souvenir id\" for getting souvenir";
    }

    @Override
    public void execute(List<String> args) {

    }
}
