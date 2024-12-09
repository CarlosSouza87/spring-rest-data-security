package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.GroupConverter;
import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private GroupConverter groupConverter;

    @InjectMocks
    private GroupService groupService;

    private GroupDTO groupDTO;
    private Group group;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        groupDTO = new GroupDTO();
        groupDTO.setId(1L);
        groupDTO.setName("Test Group");

        group = new Group();
        group.setId(1L);
        group.setName("Test Group");
        group.setMembers(new ArrayList<>());
    }

    @Test
    void testGetAll() {
        List<Group> groups = List.of(group);
        List<GroupDTO> groupDTOs = List.of(groupDTO);

        when(groupRepository.findAll()).thenReturn(groups);
        when(groupConverter.convertToDto(groups)).thenReturn(groupDTOs);

        List<GroupDTO> result = groupService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(groupDTO.getId(), result.get(0).getId());
        assertEquals(groupDTO.getName(), result.get(0).getName());

        verify(groupRepository, times(1)).findAll();
        verify(groupConverter, times(1)).convertToDto(groups);
    }

    @Test
    void testFindById() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(groupConverter.convertToDto(group)).thenReturn(groupDTO);

        GroupDTO result = groupService.findById(1L);

        assertNotNull(result);
        assertEquals(groupDTO.getId(), result.getId());
        assertEquals(groupDTO.getName(), result.getName());

        verify(groupRepository, times(1)).findById(1L);
        verify(groupConverter, times(1)).convertToDto(group);
    }

    @Test
    void testSaveWithId() {
        Member member = new Member();
        member.setId(1L);
        group.getMembers().add(member);

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        doNothing().when(memberRepository).deleteMembersByGroup(group);
        when(groupConverter.convertToEntity(groupDTO, group)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);
        when(groupConverter.convertToDto(group)).thenReturn(groupDTO);

        GroupDTO result = groupService.save(1L, groupDTO);

        assertNotNull(result);
        assertEquals(groupDTO.getId(), result.getId());
        assertEquals(groupDTO.getName(), result.getName());

        verify(groupRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).deleteMembersByGroup(group);
        verify(groupConverter, times(1)).convertToEntity(groupDTO, group);
        verify(groupRepository, times(1)).save(group);
        verify(groupConverter, times(1)).convertToDto(group);
    }

    @Test
    void testSaveWithoutId() {
        when(groupConverter.convertToEntity(groupDTO)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);
        when(groupConverter.convertToDto(group)).thenReturn(groupDTO);

        GroupDTO result = groupService.save(groupDTO);

        assertNotNull(result);
        assertEquals(groupDTO.getId(), result.getId());
        assertEquals(groupDTO.getName(), result.getName());

        verify(groupConverter, times(1)).convertToEntity(groupDTO);
        verify(groupRepository, times(1)).save(group);
        verify(groupConverter, times(1)).convertToDto(group);
    }

    @Test
    void testDelete() {
        doNothing().when(groupRepository).deleteById(1L);

        groupService.delete(1L);

        verify(groupRepository, times(1)).deleteById(1L);
    }
}
