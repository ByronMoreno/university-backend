package com.example.login.controller;

import com.example.login.dto.UniversityDTO;
import com.example.login.facade.UniversityFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/universities")
@RequiredArgsConstructor
@Tag(name = "University", description = "University Management APIs")
@SecurityRequirement(name = "bearerAuth")
public class UniversityController {

    private final UniversityFacade universityFacade;

    @GetMapping
    @PreAuthorize("hasAuthority('UNIVERSITY_READ')")
    @Operation(summary = "Get all universities")
    public ResponseEntity<List<UniversityDTO>> getAll() {
        return ResponseEntity.ok(universityFacade.getAllUniversities());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('UNIVERSITY_READ')")
    @Operation(summary = "Get university by ID")
    public ResponseEntity<UniversityDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(universityFacade.getUniversityById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('UNIVERSITY_CREATE')")
    @Operation(summary = "Create a new university")
    public ResponseEntity<UniversityDTO> create(@RequestBody UniversityDTO universityDTO) {
        return new ResponseEntity<>(universityFacade.createUniversity(universityDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UNIVERSITY_UPDATE')")
    @Operation(summary = "Update an existing university")
    public ResponseEntity<UniversityDTO> update(@PathVariable Long id, @RequestBody UniversityDTO universityDTO) {
        return ResponseEntity.ok(universityFacade.updateUniversity(id, universityDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('UNIVERSITY_DELETE')")
    @Operation(summary = "Delete a university")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        universityFacade.deleteUniversity(id);
        return ResponseEntity.noContent().build();
    }
}
