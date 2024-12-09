package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.MarathonConverter;
import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarathonService {
    private final MarathonConverter marathonConverter;
    private final MarathonRepository marathonRepository;

    @Autowired
    public MarathonService(MarathonConverter marathonConverter, MarathonRepository marathonRepository) {
        this.marathonConverter = marathonConverter;
        this.marathonRepository = marathonRepository;
    }

    public List<MarathonDTO> getAll() {
        return marathonConverter.convertToDto(marathonRepository.findAll());
    }

    public void delete(Long id) {
        marathonRepository.deleteById(id);
    }

    public MarathonDTO findById(Long id) {
        return marathonConverter.convertToDto(marathonRepository.findById(id).orElseThrow());
    }

    @Transactional
    public MarathonDTO save(Long id, MarathonDTO dto) {
        Marathon entity = marathonRepository.findById(id).orElseThrow();
        Marathon marathonToSaved = marathonConverter.convertToEntity(dto, entity);
        Marathon marathonReturned = marathonRepository.save(marathonToSaved);
        return marathonConverter.convertToDto(marathonReturned);
    }

    public MarathonDTO save(MarathonDTO dto) {
        Marathon marathonToSaved = marathonConverter.convertToEntity(dto);
        Marathon marathonReturned = marathonRepository.save(marathonToSaved);
        return marathonConverter.convertToDto(marathonReturned);
    }
}
