package com.spring.boot.Dto.Security;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserDto", description = "Data Transfer Object for User")
public class UserDto {
    @Schema(description = "User ID", example = "1")
    private Long id;
    @NotBlank(message = "not_empty.username")
    @Size(min = 7, message = "size.username")
    @Schema(description = "Username of the user, minimum 7 characters", example = "AhmedRamadan")
    private String username;
    @NotBlank(message = "not_empty.password")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$",
            message = "error.password"
    )
    @Schema(description = "Password with at least 7 characters including uppercase, lowercase, number, and special character", example = "Aa12345!")
    private String password;
    @Valid
    @Schema(description = "List of roles assigned to the user")
    private List<RoleDto> rolesDto;
    @Valid
    @Schema(description = "Detailed information of the user")
    private UserDetailsDto userDetailDto;

}
