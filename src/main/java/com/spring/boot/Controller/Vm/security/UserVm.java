package com.spring.boot.Controller.Vm.security;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;
@Data
@Schema(name = "UserVm", description = "View Model for User")
public class UserVm {
    @Schema(description = "User ID", example = "1")
    private Long id;
    @NotBlank(message = "username.required")
    @Schema(description = "Username of the user", example = "AhmedRamadan")
    @Size(min = 3, max = 50, message = "username.size")
    private String username;

    @NotBlank(message = "password.required")
    @Schema(description = "Password of the user", example = "Aa12345!")
    @Size(min = 3, max = 100, message = "password.size")
    private String password;
    @Schema(description = "List of roles assigned to the user")
    private List<RoleVm> rolesVm ;
}
