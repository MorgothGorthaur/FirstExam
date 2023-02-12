package exam.dto.mapper;

import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;
import exam.model.Manufacturer;
import exam.model.Souvenir;

public interface Mapper {
    ManufacturerDto toManufacturerDto(Manufacturer manufacturer);

    SouvenirDto toSouvenirDto(Souvenir souvenir);

    ManufacturerFullDto toManufacturerFullDto(Manufacturer manufacturer);

    SouvenirFullDto toSouvenirFullDto(Souvenir souvenir);
}
