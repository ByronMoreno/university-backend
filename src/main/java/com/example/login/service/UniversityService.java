package com.example.login.service;

import com.example.login.entity.University;
import java.util.List;
import java.util.Optional;

public interface UniversityService {
    List<University> findAll();
    Optional<University> findById(Long id);
    University save(University university);
    University update(Long id, University university);
    void deleteById(Long id);
}
