package kettlebell.agencyamazon.controller.user;

import jakarta.validation.Valid;
import kettlebell.agencyamazon.models.user.dto.AuthenticationRequest;
import kettlebell.agencyamazon.models.user.dto.JwtAuthenticationResponse;
import kettlebell.agencyamazon.models.user.dto.UserCreateRequest;
import kettlebell.agencyamazon.service.user.impl.UserServiceImpl;
import kettlebell.agencyamazon.service.user.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService tokenService;
    private final UserServiceImpl userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserCreateRequest request) {
        userService.addUser(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        return new JwtAuthenticationResponse(tokenService.generateToken(user.getUsername()));
    }

}
