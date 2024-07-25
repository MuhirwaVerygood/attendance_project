package org.example.attendancebackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Family Controllers", description = "Operations about families")
public class FamilyControllers {
    private final FamilyService familyService;
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Get all families", description = "Endpoint to get all families")
    public List<Family> getFamilies(){
        return familyService.getFamilies();
    }

    @GetMapping("/{family-id}")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Get a family by id", description = "Endpoint to get family by it's id")
    public ResponseEntity<Family> getFamilyByFamilyId(@PathVariable("family-id") Integer familyId){
        return familyService.getFamilyById(familyId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Operation(summary = "Create a new family", description = "Endpoint to create a new family")
    public ResponseEntity<FamilyResponse> createFamily(@RequestBody FamilyRequest request){
        return familyService.createFamily(request);
    }


    @DeleteMapping("/{family-id}")
    @Operation(summary = "Delete family by familyId", description = "Endpoint to delete the family by it's id")
    public ResponseEntity<String> deleteFamilyById(@PathVariable("family-id") Integer familyId){
        return familyService.deleteFamilyById(familyId);
    }


    @PutMapping("/{family-id}")
    @Operation(summary = "Update family by familyId", description = "Endpoint to update the family by it's id")
    public ResponseEntity<String> updatedFamilyById(@PathVariable("family-id") Integer familyId, @RequestBody FamilyRequest request){
        return familyService.updateFamilyById(familyId,request);
    }


}
