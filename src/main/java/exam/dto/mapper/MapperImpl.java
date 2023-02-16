package exam.dto.mapper;

import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.dto.SouvenirFullDto;
import exam.model.Manufacturer;
import exam.model.Souvenir;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperImpl implements Mapper {
    @Override
    public ManufacturerDto toManufacturerDto(Manufacturer manufacturer) {
        return new ManufacturerDto(manufacturer.getId(), manufacturer.getName(), manufacturer.getCountry());
    }

    @Override
    public SouvenirDto toSouvenirDto(Souvenir souvenir) {
        return new SouvenirDto(souvenir.getId(), souvenir.getName(), souvenir.getPrice(), souvenir.getDate());
    }

    @Override
    public ManufacturerFullDto toManufacturerFullDto(Manufacturer manufacturer) {
        return new ManufacturerFullDto(manufacturer.getId(), manufacturer.getName(),
                manufacturer.getCountry(), manufacturer.getSouvenirs().stream().map(this::toSouvenirDto).collect(Collectors.toSet()));
    }

    @Override
    public SouvenirFullDto toSouvenirFullDto(Souvenir souvenir) {
        return new SouvenirFullDto(souvenir.getId(), souvenir.getName(), souvenir.getPrice(), souvenir.getDate(), toManufacturerDto(souvenir.getManufacturer()));
    }
}
