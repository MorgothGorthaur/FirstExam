package exam.handler;

import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;

import java.util.List;

public interface SouvenirHandler {
    List<SouvenirDto> getSouvenirs();
    List<SouvenirFullDto> getSouvenirById();
}
