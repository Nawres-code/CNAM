package com.Parking.GestionParking.auth;

import com.Parking.GestionParking.security.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name= "Authentication")

public class AuthenticationController {
    private final AuthenticationService service;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
//    @GetMapping("/activate-account")
//    public void confirm(
//            @RequestParam String token
//    ) throws MessagingException {
//        service.activateAccount(token);
//    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestParam("token") String token
    ) {
        if (jwtService.isTokenExpired(token)) {
            // Extract the username from the token
            String username = jwtService.extractUsername(token);

            // Retrieve UserDetails
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Generate a new token for the user
            String newToken = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(newToken));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }


}