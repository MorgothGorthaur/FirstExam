package exam.handler;

import exam.dto.SouvenirDto;
import exam.model.Souvenir;

public interface SouvenirCreatorUpdater {
    SouvenirDto create();
    SouvenirDto update(Souvenir souvenir);
}
