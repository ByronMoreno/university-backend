package com.example.login.facade;

import com.example.login.dto.UniversityDTO;
import java.util.List;

public interface UniversityFacade {
    List<UniversityDTO> getAllUniversities();
    UniversityDTO getUniversityById(Long id);
    UniversityDTO createUniversity(UniversityDTO universityDTO);
    UniversityDTO updateUniversity(Long id, UniversityDTO universityDTO);
    void deleteUniversity(Long id);
}
