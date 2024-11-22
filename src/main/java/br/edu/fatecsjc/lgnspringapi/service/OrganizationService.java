package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.OrganizationConverter;
import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationConverter organizationConverter;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, OrganizationConverter organizationConverter) {
        this.organizationRepository = organizationRepository;
        this.organizationConverter = organizationConverter;
    }

    public List<OrganizationDTO> getAll() {
        return organizationConverter.convertToDto(organizationRepository.findAll());
    }

    public OrganizationDTO findById(Long id) {
        return organizationConverter.convertToDto(organizationRepository.findById(id).orElseThrow());
    }

    @Transactional
    public OrganizationDTO save(Long id, OrganizationDTO dto) {
        Organization entity = organizationRepository.findById(id).orElseThrow();
        Organization organizationToSaved = organizationConverter.convertToEntity(dto, entity);
        Organization organizationReturned = organizationRepository.save(organizationToSaved);
        return organizationConverter.convertToDto(organizationReturned);
    }

    public OrganizationDTO save(OrganizationDTO dto) {
        Organization organizationToSaved = organizationConverter.convertToEntity(dto);
        Organization organizationReturned = organizationRepository.save(organizationToSaved);
        return organizationConverter.convertToDto(organizationReturned);
    }

    public void delete(Long id) {
        organizationRepository.deleteById(id);
    }
}
