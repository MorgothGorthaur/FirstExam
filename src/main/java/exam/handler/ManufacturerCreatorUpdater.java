package exam.handler;

import exam.dto.ManufacturerDto;

public interface ManufacturerCreatorUpdater {
    ManufacturerDto create();
    ManufacturerDto update(ManufacturerDto dto);
}
