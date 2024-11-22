package br.edu.fatecsjc.lgnspringapi.converter;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrganizationConverter implements Converter<Organization, OrganizationDTO> {

    private final ModelMapper modelMapper;

    private TypeMap<OrganizationDTO, Organization> propertyMapperDto;

    @Autowired
    public OrganizationConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Organization convertToEntity(OrganizationDTO dto) {
        if(propertyMapperDto == null) {
            propertyMapperDto = modelMapper.createTypeMap(OrganizationDTO.class, Organization.class);
            propertyMapperDto.addMappings(mapper -> mapper.skip(Organization::setId));
        }

        Organization entity = modelMapper.map(dto, Organization.class);
        Provider<Organization> organizationProvider = p -> new Organization();
        propertyMapperDto.setProvider(organizationProvider);

        return entity;
    }

    @Override
    public Organization convertToEntity(OrganizationDTO dto, Organization entity) {
        if(propertyMapperDto == null) {
            propertyMapperDto = modelMapper.createTypeMap(OrganizationDTO.class, Organization.class);
            propertyMapperDto.addMappings(mapper -> mapper.skip(Organization::setId));
        }

        Provider<Organization> organizationProvider = p -> entity;
        propertyMapperDto.setProvider(organizationProvider);

        return modelMapper.map(dto, Organization.class);
    }

    @Override
    public OrganizationDTO convertToDto(Organization entity) {
        return modelMapper.map(entity, OrganizationDTO.class);
    }

    @Override
    public List<Organization> convertToEntity(List<OrganizationDTO> dtos) {
        return modelMapper.map(dtos, new TypeToken<List<Organization>>(){}.getType());
    }

    @Override
    public List<OrganizationDTO> convertToDto(List<Organization> entities) {
        return modelMapper.map(entities, new TypeToken<List<OrganizationDTO>>(){}.getType());
    }
}
