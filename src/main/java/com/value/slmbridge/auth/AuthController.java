package com.value.slmbridge.auth;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @GetMapping("/me")
    public UserProfileResponse me(Authentication authentication) {
        return authService.getCurrentUser(authentication.getName());
    }

    @PutMapping("/me")
    public UserProfileResponse updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        return authService.updateProfile(authentication.getName(), request);
    }

    @PutMapping("/change-password")
    public String changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        return authService.changePassword(authentication.getName(), request);
    }
}