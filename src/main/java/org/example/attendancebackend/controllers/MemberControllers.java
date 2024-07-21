package org.example.attendancebackend.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.attendancebackend.models.MemberDto;
import org.example.attendancebackend.models.Members;
import org.example.attendancebackend.services.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAuthority;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor


public class MemberControllers {
    private final MemberService service;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public List<MemberDto> getAllMembers(){

        return service.getAllMembers();
    }

    @GetMapping("/{family-id}")
    public List<MemberDto> getMembersByFamilyId(@PathVariable("family-id") Integer id){
        return service.getMembersByFamilyId(id);
    }


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "When a amember is added successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "When a family with the specified id does not exist"
            )
    }
    )
    @PostMapping
    public ResponseEntity<String> addMember(@RequestBody MemberRequest request){
        return service.addMember(request);
    }
}
