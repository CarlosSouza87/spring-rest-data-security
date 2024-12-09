package br.edu.fatecsjc.lgnspringapi.converter;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MarathonConverter implements Converter<Marathon, MarathonDTO> {
    private final ModelMapper modelMapper;

    private TypeMap<MarathonDTO, Marathon> propertyMapperDto;

    @Autowired
    public MarathonConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Marathon convertToEntity(MarathonDTO dto) {
        if(propertyMapperDto == null) {
            propertyMapperDto = modelMapper.createTypeMap(MarathonDTO.class, Marathon.class);
            propertyMapperDto.addMappings(mapper -> mapper.skip(Marathon::setId));
        }

        Marathon entity = modelMapper.map(dto, Marathon.class);
        Provider<Marathon> marathonProvider = p -> new Marathon();
        propertyMapperDto.setProvider(marathonProvider);
        return entity;
    }

    @Override
    public Marathon convertToEntity(MarathonDTO dto, Marathon entity) {
        if(propertyMapperDto == null) {
            propertyMapperDto = modelMapper.createTypeMap(MarathonDTO.class, Marathon.class);
            propertyMapperDto.addMappings(mapper -> mapper.skip(Marathon::setId));
        }

        Provider<Marathon> marathonProvider = p -> entity;
        propertyMapperDto.setProvider(marathonProvider);

        return modelMapper.map(dto, Marathon.class);
    }

    @Override
    public MarathonDTO convertToDto(Marathon entity) {
        return modelMapper.map(entity, MarathonDTO.class);
    }

    @Override
    public List<Marathon> convertToEntity(List<MarathonDTO> dtos) {
        return modelMapper.map(dtos, new TypeToken<List<Marathon>>(){}.getType());
    }

    @Override
    public List<MarathonDTO> convertToDto(List<Marathon> entities) {
        return modelMapper.map(entities, new TypeToken<List<MarathonDTO>>(){}.getType());
    }
}
