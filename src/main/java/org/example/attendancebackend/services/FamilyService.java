package org.example.attendancebackend.services;

import lombok.RequiredArgsConstructor;
import org.example.attendancebackend.controllers.FamilyResponse;
import org.example.attendancebackend.models.Family;
import org.example.attendancebackend.repositories.FamilyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;

    public List<Family> getFamilies() {
        return familyRepository.findAll();
    }


    public ResponseEntity<FamilyResponse> createFamily(FamilyRequest request) {
        Optional<Family> familyExists = familyRepository.findByFamilyName(request.getFamilyName());
        if(familyExists.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Family with that name already exists");
        }

        try{
            var family = Family.builder()
                    .familyName(request.getFamilyName())
                    .members(List.of(request.getMembers()))
                    .build();

            familyRepository.save(family);

            return ResponseEntity.ok(
                    FamilyResponse
                            .builder()
                            .message("Family added successfully")
                            .family(family)
                            .build()
            );
        }catch (ResponseStatusException e){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Family not created");
        }


    }

    public ResponseEntity<Family> getFamilyById(Integer familyId) {
        try{
            if(familyRepository.existsById(familyId)){
                return ResponseEntity.ok(familyRepository.findById(familyId).get());
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Family with that id not found");
            }
        }catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Family not found");
        }
    }

    public ResponseEntity<String> deleteFamilyById(Integer familyId) {
        if(familyRepository.existsById(familyId)){
            familyRepository.deleteById(familyId);
            return  ResponseEntity.ok("Family deleted successfully");
        }else{
            return ResponseEntity.status(404).body("Family not found");
        }
    }

    public ResponseEntity<String> updateFamilyById(Integer familyId , FamilyRequest request) {
        if(familyRepository.existsById(familyId)){
            Family family = familyRepository.findById(familyId).get();
            family.setFamilyName(request.getFamilyName());
            family.setMembers(Arrays.asList(request.getMembers()));
            familyRepository.save(family);
            return  ResponseEntity.ok("Family updated successfully");
        }else{
            return ResponseEntity.status(404).body("Family not found");
        }
    }
}
