package com.spring.boot.Controller.Vm.security;

import com.spring.boot.Dto.Security.UserDetailsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserSignUpVm", description = "View Model for User Sign Up")
public class UserSignUpVm {
    @Schema(description = "Username for sign up, minimum 7 characters", example = "AhmedRamadan")
    @NotBlank(message = "not_empty.username")
    @Size(min = 7, message = "size.username")
    private String username;
    @Schema(description = "Password with at least 7 characters including uppercase, lowercase, number, and special character", example = "Aa12345!")
    @NotBlank(message = "not_empty.password")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$",
            message = "error.password"
    )
    private String password;
    @Valid
    @Schema(description = "Detailed information of the user")
    private UserDetailsDto userDetailDto;

}
