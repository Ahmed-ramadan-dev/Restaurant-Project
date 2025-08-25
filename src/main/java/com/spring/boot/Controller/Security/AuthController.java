package com.spring.boot.Controller.Security;
import com.spring.boot.Controller.Vm.TokenVm.TokenResponseVm;
import com.spring.boot.Controller.Vm.security.UserLoginVm;
import com.spring.boot.Controller.Vm.security.UserSignUpVm;
import com.spring.boot.Dto.Security.UserDto;
import com.spring.boot.Service.Security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Tag(name = "Authentication",
        description = "APIs for user sign-up and login") // ← التاج الخاص بالـ Swagger
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
        @PostMapping("/sign-up-for-dev")
        @Operation(
                summary = "Developer sign-up",
                description = "Allows developers to sign up with additional privileges."
        )
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Sign Up Successful for developer"),
                @ApiResponse(responseCode = "400", description = "Invalid input data: ID must be null"),
                @ApiResponse(responseCode = "409", description = "User already exists"),
                @ApiResponse(responseCode = "404", description = "Role not found")
        })
        @SecurityRequirements

    public ResponseEntity<String> signUpControl(@RequestBody @Valid UserDto userDto) {
        authService.signUpControl(userDto);
        return ResponseEntity.created(URI.create("/auth/sign-up-for-dev"))
                .body("Sign Up Successful ya Dev");    }
    @PostMapping("/sign-up")
    @Operation(
            summary = "User sign-up",
            description = "Registers a new user account in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sign Up Successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input data: ID must be null"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @SecurityRequirements

    public ResponseEntity<String> signUp(@RequestBody @Valid UserSignUpVm userSignUpVm) {
        authService.signUp(userSignUpVm);
        return ResponseEntity.created(URI.create("/auth/sign-up"))
                .body("Sign Up Successful");

    }
    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticates a user and returns an access token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Successful"),
            @ApiResponse(responseCode = "401", description = "Password does not match"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @SecurityRequirements

    public ResponseEntity<TokenResponseVm> login(@RequestBody @Valid UserLoginVm userLoginVm) {
    authService.login(userLoginVm);
    return ResponseEntity.ok( authService.login(userLoginVm));
    }
}
