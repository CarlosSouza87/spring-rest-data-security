package br.edu.fatecsjc.lgnspringapi.converter;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import java.util.List;
import java.util.Objects;

@Component
public class GroupConverter implements Converter<Group, GroupDTO> {
    private final ModelMapper modelMapper;

    private TypeMap<GroupDTO, Group> propertyMapperDto;

    @Autowired
    public GroupConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Group convertToEntity(GroupDTO dto) {
        if(propertyMapperDto == null) {
            propertyMapperDto = modelMapper.createTypeMap(GroupDTO.class, Group.class);
            propertyMapperDto.addMappings(mapper -> mapper.skip(Group::setId));
            propertyMapperDto.addMappings(mapper -> mapper.skip(Group::setOrganization));
        }

        Group entity = modelMapper.map(dto, Group.class);
        Provider<Group> groupProvider = p -> new Group();
        propertyMapperDto.setProvider(groupProvider);

        entity.getMembers().forEach(m -> m.setGroup(entity));
        if(ObjectUtils.isEmpty(dto.getOrganizationId())) {
            entity.setOrganization(null);
        } else {
            entity.setOrganization(new Organization(dto.getOrganizationId()));
        }
        return entity;
    }

    @Override
    public Group convertToEntity(GroupDTO dto, Group entity) {
        if(propertyMapperDto == null) {
            propertyMapperDto = modelMapper.createTypeMap(GroupDTO.class, Group.class);
            propertyMapperDto.addMappings(mapper -> mapper.skip(Group::setId));
            propertyMapperDto.addMappings(mapper -> mapper.skip(Group::setOrganization));
        }

        Provider<Group> groupProvider = p -> entity;
        propertyMapperDto.setProvider(groupProvider);

        Group newEntity = modelMapper.map(dto, Group.class);
        newEntity.getMembers().forEach(member -> member.setGroup(newEntity));
        if(ObjectUtils.isEmpty(dto.getOrganizationId())) {
            entity.setOrganization(null);
        } else {
            entity.setOrganization(new Organization(dto.getOrganizationId()));
        }
        return newEntity;
    }

    @Override
    public GroupDTO convertToDto(Group entity) {
        return modelMapper.map(entity, GroupDTO.class);
    }

    @Override
    public List<Group> convertToEntity(List<GroupDTO> dtos) {
        List<Group> groups = modelMapper.map(dtos, new TypeToken<List<Group>>(){}.getType());
        groups.forEach(group -> group.getMembers().forEach(member -> member.setGroup(group)));
        return groups;
    }

    @Override
    public List<GroupDTO> convertToDto(List<Group> entities) {
        return modelMapper.map(entities, new TypeToken<List<GroupDTO>>(){}.getType());
    }
}
