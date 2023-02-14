package exam.handler;

import exam.dao.Dao;
import exam.dto.ManufacturerDto;
import exam.dto.ManufacturerFullDto;
import exam.dto.SouvenirDto;
import exam.dto.mapper.Mapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ManufacturerHandlerImpl implements ManufacturerHandler {
    private final Dao dao;
    private final Mapper mapper;

    @Override
    public List<ManufacturerDto> getManufacturers() {
        return dao.getManufacturers().stream().map(mapper::toManufacturerDto).toList();
    }

    @Override
    public ManufacturerFullDto getFullManufacturer(Long id) {
        return mapper.toManufacturerFullDto(dao.getManufacturerById(id));
    }

    @Override
    public void removeManufacturer(Long id) {
        dao.removeManufacturer(id);
    }

    @Override
    public ManufacturerDto addManufacturer(ManufacturerDto dto) {
        return mapper.toManufacturerDto(dao.addManufacturer(dto.toManufacturer()));
    }

    @Override
    public ManufacturerDto updateManufacturer(ManufacturerDto dto) {
        return mapper.toManufacturerDto(dao.updateManufacturer(dto.toManufacturer()));
    }

    @Override
    public SouvenirDto addSouvenir(Long id, SouvenirDto dto) {
        return mapper.toSouvenirDto(dao.addSouvenir(id, dto.toSouvenir()));
    }

    @Override
    public SouvenirDto updateSouvenir(SouvenirDto dto) {
        return mapper.toSouvenirDto(dao.updateSouvenir(dto.toSouvenir()));
    }

    @Override
    public List<ManufacturerDto> getCheapest(double price) {
        return dao.getManufacturers().stream()
                .filter(m -> m.isMakesSouvenirsCheaperThanValue(price))
                .map(mapper::toManufacturerDto).toList();
    }
}
