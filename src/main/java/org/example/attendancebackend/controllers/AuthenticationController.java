package org.example.attendancebackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.attendancebackend.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name="Authentication Controllers", description = "Operations about user authentication")

public class AuthenticationController {
  private final AuthenticationService service;

  @PatchMapping
  @Operation(summary = "Change the user password", description = "Endpoint to change the user password")
  public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
  ) {
    service.changePassword(request, connectedUser);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/register")
  @Operation(summary = "Register a new user", description = "Register a new user")
  public ResponseEntity<String> register(
      @RequestBody RegisterRequest request
  ) {
    return service.register(request);
  }
  @PostMapping("/login")
  @Operation(summary = "Login a created user", description ="Endpoint for the user login")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }
}
