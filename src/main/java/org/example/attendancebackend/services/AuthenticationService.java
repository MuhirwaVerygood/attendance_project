package org.example.attendancebackend.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.attendancebackend.config.JwtService;
import org.example.attendancebackend.models.Token;
import org.example.attendancebackend.repositories.TokenRepository;
import org.example.attendancebackend.models.TokenType;
import org.example.attendancebackend.models.User;
import org.example.attendancebackend.repositories.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.example.attendancebackend.validations.StringValidation.isInvalid;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<String> register(RegisterRequest request) {
    if (isInvalid(request.getFirstname())) {
      return new ResponseEntity<>("Please enter a valid firstname", HttpStatus.BAD_REQUEST);
    }
    if (isInvalid(request.getLastname())) {
      return new ResponseEntity<>("Please enter a valid lastname", HttpStatus.BAD_REQUEST);
    }

    if (isInvalid(request.getEmail())) {
      return new ResponseEntity<>("Please enter a valid email", HttpStatus.BAD_REQUEST);
    }

    if (isInvalid(request.getPassword())) {
      return new ResponseEntity<>("Please enter a valid password", HttpStatus.BAD_REQUEST);
    }

    if (isInvalid(String.valueOf(request.getRole()))) {
      return new ResponseEntity<>("Please enter a valid role", HttpStatus.BAD_REQUEST);
    }



    //checking if the user with email already exists
    Optional<User> userExists =  repository.findByEmail(request.getEmail());
    if(userExists.isPresent()){
      return new ResponseEntity<>("User with that email already exists",HttpStatusCode.valueOf(409));
    }

    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    var savedUser = repository.save(user);
    if(savedUser != null){
      return ResponseEntity.ok("User saved successfully");
    }else{
      return new ResponseEntity("Faced an error",HttpStatusCode.valueOf(403));
    }
  }


  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    if (isInvalid(request.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter a valid email");
    }

    if (isInvalid(String.valueOf(request.getPassword()))) {
      throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Please enter a valid password");
    }

    try{
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );
    }catch (ResponseStatusException e){
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed");
    }

    var user = repository.findByEmail(request.getEmail())
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  };
}
