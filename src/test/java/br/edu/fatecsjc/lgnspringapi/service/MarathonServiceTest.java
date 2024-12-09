package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.MarathonConverter;
import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarathonServiceTest {

    @Mock
    private MarathonRepository marathonRepository;

    @Mock
    private MarathonConverter marathonConverter;

    @InjectMocks
    private MarathonService marathonService;

    private MarathonDTO marathonDTO;
    private Marathon marathon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        marathonDTO = new MarathonDTO();
        marathonDTO.setId(1L);
        marathonDTO.setName("Test Marathon");

        marathon = new Marathon();
        marathon.setId(1L);
        marathon.setName("Test Marathon");
    }

    @Test
    void testGetAll() {
        List<Marathon> marathons = List.of(marathon);
        List<MarathonDTO> marathonDTOs = List.of(marathonDTO);

        when(marathonRepository.findAll()).thenReturn(marathons);
        when(marathonConverter.convertToDto(marathons)).thenReturn(marathonDTOs);

        List<MarathonDTO> result = marathonService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(marathonDTO.getId(), result.get(0).getId());
        assertEquals(marathonDTO.getName(), result.get(0).getName());

        verify(marathonRepository, times(1)).findAll();
        verify(marathonConverter, times(1)).convertToDto(marathons);
    }

    @Test
    void testFindById() {
        when(marathonRepository.findById(1L)).thenReturn(Optional.of(marathon));
        when(marathonConverter.convertToDto(marathon)).thenReturn(marathonDTO);

        MarathonDTO result = marathonService.findById(1L);

        assertNotNull(result);
        assertEquals(marathonDTO.getId(), result.getId());
        assertEquals(marathonDTO.getName(), result.getName());

        verify(marathonRepository, times(1)).findById(1L);
        verify(marathonConverter, times(1)).convertToDto(marathon);
    }

    @Test
    void testSaveWithId() {
        when(marathonRepository.findById(1L)).thenReturn(Optional.of(marathon));
        when(marathonConverter.convertToEntity(marathonDTO, marathon)).thenReturn(marathon);
        when(marathonRepository.save(marathon)).thenReturn(marathon);
        when(marathonConverter.convertToDto(marathon)).thenReturn(marathonDTO);

        MarathonDTO result = marathonService.save(1L, marathonDTO);

        assertNotNull(result);
        assertEquals(marathonDTO.getId(), result.getId());
        assertEquals(marathonDTO.getName(), result.getName());

        verify(marathonRepository, times(1)).findById(1L);
        verify(marathonConverter, times(1)).convertToEntity(marathonDTO, marathon);
        verify(marathonRepository, times(1)).save(marathon);
        verify(marathonConverter, times(1)).convertToDto(marathon);
    }

    @Test
    void testSaveWithoutId() {
        when(marathonConverter.convertToEntity(marathonDTO)).thenReturn(marathon);
        when(marathonRepository.save(marathon)).thenReturn(marathon);
        when(marathonConverter.convertToDto(marathon)).thenReturn(marathonDTO);

        MarathonDTO result = marathonService.save(marathonDTO);

        assertNotNull(result);
        assertEquals(marathonDTO.getId(), result.getId());
        assertEquals(marathonDTO.getName(), result.getName());

        verify(marathonConverter, times(1)).convertToEntity(marathonDTO);
        verify(marathonRepository, times(1)).save(marathon);
        verify(marathonConverter, times(1)).convertToDto(marathon);
    }

    @Test
    void testDelete() {
        doNothing().when(marathonRepository).deleteById(1L);

        marathonService.delete(1L);

        verify(marathonRepository, times(1)).deleteById(1L);
    }
}
