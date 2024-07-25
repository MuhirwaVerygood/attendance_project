package org.example.attendancebackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name="Member Controllers", description = "Operations about members")
public class MemberControllers {
    private final MemberService service;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Get all members", description = "Fetch a list of all members")
    public List<MemberDto> getAllMembers(){

        return service.getAllMembers();
    }

    @GetMapping("/{family-id}")
    @Operation(summary = "Get all members by family id", description = "Fetch a list of all members by a family id")
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
    @Operation(summary = "Add a member", description = "Add members in a family")
    public ResponseEntity<String> addMember(@RequestBody MemberRequest request){
        return service.addMember(request);
    }

    @DeleteMapping("/{member-id}")
    @Operation(summary = "Delete a member by id", description = "Delete a member by a member id")
    public ResponseEntity<String> deleteMember(@PathVariable("member-id") Integer memberId){
        return service.deleteMember(memberId);
    }


    @PutMapping("/{member-id}")
    @Operation(summary = "Update a member by id", description = "Update a member by id")
    public ResponseEntity<String> updateMember( @PathVariable("member-id") Integer id,  @RequestBody MemberRequest request){
        return service.updateMember(id, request);
    }
}
