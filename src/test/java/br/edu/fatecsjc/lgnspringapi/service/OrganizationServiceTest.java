package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.OrganizationConverter;
import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private OrganizationConverter organizationConverter;

    @InjectMocks
    private OrganizationService organizationService;

    private OrganizationDTO organizationDTO;
    private Organization organization;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        organizationDTO = new OrganizationDTO();
        organizationDTO.setId(1L);
        organizationDTO.setName("Test Organization");

        organization = new Organization();
        organization.setId(1L);
        organization.setName("Test Organization");
    }

    @Test
    void testGetAll() {
        List<Organization> organizations = List.of(organization);
        List<OrganizationDTO> organizationDTOs = List.of(organizationDTO);

        when(organizationRepository.findAll()).thenReturn(organizations);
        when(organizationConverter.convertToDto(organizations)).thenReturn(organizationDTOs);

        List<OrganizationDTO> result = organizationService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(organizationDTO.getId(), result.get(0).getId());
        assertEquals(organizationDTO.getName(), result.get(0).getName());

        verify(organizationRepository, times(1)).findAll();
        verify(organizationConverter, times(1)).convertToDto(organizations);
    }

    @Test
    void testFindById() {
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(organizationConverter.convertToDto(organization)).thenReturn(organizationDTO);

        OrganizationDTO result = organizationService.findById(1L);

        assertNotNull(result);
        assertEquals(organizationDTO.getId(), result.getId());
        assertEquals(organizationDTO.getName(), result.getName());

        verify(organizationRepository, times(1)).findById(1L);
        verify(organizationConverter, times(1)).convertToDto(organization);
    }

    @Test
    void testSaveWithId() {
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(organizationConverter.convertToEntity(organizationDTO, organization)).thenReturn(organization);
        when(organizationRepository.save(organization)).thenReturn(organization);
        when(organizationConverter.convertToDto(organization)).thenReturn(organizationDTO);

        OrganizationDTO result = organizationService.save(1L, organizationDTO);

        assertNotNull(result);
        assertEquals(organizationDTO.getId(), result.getId());
        assertEquals(organizationDTO.getName(), result.getName());

        verify(organizationRepository, times(1)).findById(1L);
        verify(organizationConverter, times(1)).convertToEntity(organizationDTO, organization);
        verify(organizationRepository, times(1)).save(organization);
        verify(organizationConverter, times(1)).convertToDto(organization);
    }

    @Test
    void testSaveWithoutId() {
        when(organizationConverter.convertToEntity(organizationDTO)).thenReturn(organization);
        when(organizationRepository.save(organization)).thenReturn(organization);
        when(organizationConverter.convertToDto(organization)).thenReturn(organizationDTO);

        OrganizationDTO result = organizationService.save(organizationDTO);

        assertNotNull(result);
        assertEquals(organizationDTO.getId(), result.getId());
        assertEquals(organizationDTO.getName(), result.getName());

        verify(organizationConverter, times(1)).convertToEntity(organizationDTO);
        verify(organizationRepository, times(1)).save(organization);
        verify(organizationConverter, times(1)).convertToDto(organization);
    }

    @Test
    void testDelete() {
        doNothing().when(organizationRepository).deleteById(1L);

        organizationService.delete(1L);

        verify(organizationRepository, times(1)).deleteById(1L);
    }
}
