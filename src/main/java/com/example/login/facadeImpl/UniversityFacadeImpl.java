package com.example.login.facadeImpl;

import com.example.login.dto.UniversityDTO;
import com.example.login.entity.University;
import com.example.login.facade.UniversityFacade;
import com.example.login.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UniversityFacadeImpl implements UniversityFacade {

    private final UniversityService universityService;

    @Override
    public List<UniversityDTO> getAllUniversities() {
        return universityService.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UniversityDTO getUniversityById(Long id) {
        return universityService.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    @Override
    public UniversityDTO createUniversity(UniversityDTO universityDTO) {
        University university = mapToEntity(universityDTO);
        University saved = universityService.save(university);
        return mapToDTO(saved);
    }

    @Override
    public UniversityDTO updateUniversity(Long id, UniversityDTO universityDTO) {
        University university = mapToEntity(universityDTO);
        University updated = universityService.update(id, university);
        return mapToDTO(updated);
    }

    @Override
    public void deleteUniversity(Long id) {
        universityService.deleteById(id);
    }

    private UniversityDTO mapToDTO(University university) {
        return UniversityDTO.builder()
                .id(university.getId())
                .name(university.getName())
                .city(university.getCity())
                .country(university.getCountry())
                .build();
    }

    private University mapToEntity(UniversityDTO dto) {
        return University.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .country(dto.getCountry())
                .build();
    }
}
