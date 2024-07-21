package org.example.attendancebackend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.attendancebackend.models.Family;
import org.example.attendancebackend.services.FamilyRequest;
import org.example.attendancebackend.services.FamilyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/family")
@RequiredArgsConstructor
public class FamilyControllers {
    private final FamilyService familyService;
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public List<Family> getFamilies(){
        return familyService.getFamilies();
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<FamilyResponse> createFamily(@RequestBody FamilyRequest request){
        return familyService.createFamily(request);
    }
}
