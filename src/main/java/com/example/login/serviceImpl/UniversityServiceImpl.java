package com.example.login.serviceImpl;

import com.example.login.entity.University;
import com.example.login.repository.UniversityRepository;
import com.example.login.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    @Override
    public List<University> findAll() {
        return universityRepository.findAll();
    }

    @Override
    public Optional<University> findById(Long id) {
        return universityRepository.findById(id);
    }

    @Override
    public University save(University university) {
        return universityRepository.save(university);
    }

    @Override
    public University update(Long id, University universityDetails) {
        return universityRepository.findById(id)
                .map(university -> {
                    university.setName(universityDetails.getName());
                    university.setCity(universityDetails.getCity());
                    university.setCountry(universityDetails.getCountry());
                    return universityRepository.save(university);
                }).orElseThrow(() -> new RuntimeException("University not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        universityRepository.deleteById(id);
    }
}
