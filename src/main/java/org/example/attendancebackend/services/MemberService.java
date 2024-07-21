package org.example.attendancebackend.services;

import lombok.RequiredArgsConstructor;
import org.example.attendancebackend.controllers.MemberRequest;
import org.example.attendancebackend.models.Family;
import org.example.attendancebackend.models.MemberDto;
import org.example.attendancebackend.models.Members;
import org.example.attendancebackend.repositories.FamilyRepository;
import org.example.attendancebackend.repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;
    private final FamilyRepository familyRepository;

    public List<MemberDto> getAllMembers(){
        return repository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<MemberDto> getMembersByFamilyId(Integer id) {
        return repository.findMembersByFamilyId(id).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ResponseEntity<String> addMember(MemberRequest request) {
        Optional<Family> familyExists = Optional.of(familyRepository.findById(request.getFamilyId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "The family with that id not found")));
        if(!familyExists.isPresent()){
            return new ResponseEntity<>("Family with that id not found", HttpStatusCode.valueOf(404));
        }

        try{
            var member = Members
                    .builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .className(request.getClassName())
                    .family(familyExists.get())
                    .build();

            var savedMember = repository.save(member);
            if(savedMember != null){
                familyExists.get().setMemberCount(familyExists.get().getMemberCount() + 1 );
                familyRepository.save(familyExists.get());
                return new ResponseEntity<>("Member added successfully",  HttpStatusCode.valueOf(200));
            }else{
                return new ResponseEntity<>("Faced an error when adding a member", HttpStatusCode.valueOf(400));
            }
        }catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The family with that id not found");
        }

    }

    public MemberDto convertToDto(Members member){
        return MemberDto
                .builder()
                .id(member.getId())
                .firstname(member.getFirstname())
                .lastname(member.getLastname())
                .className(member.getClassName())
                .familyId(String.valueOf(member.getFamily().getId()))
                .familyName(member.getFamily().getFamilyName())
                .build();
    }
}
