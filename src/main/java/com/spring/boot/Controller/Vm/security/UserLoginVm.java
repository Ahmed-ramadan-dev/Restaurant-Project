package com.spring.boot.Controller.Vm.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserLoginVm", description = "View Model for User Login")
public class UserLoginVm {
    @Schema(description = "Username for login", example = "AhmedRamadan")
    @NotBlank(message = "username.required")
    @Size(min = 3, max = 50, message = "username.size")
    private String username;

    @NotBlank(message = "password.required")
    @Size(min = 3, max = 100, message = "password.size")
    @Schema(description = "Password for login", example = "Aa12345!")
    private String password;
}
