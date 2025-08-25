package com.spring.boot.Dto.Security;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "RoleDto", description = "Data Transfer Object for Role")
public class RoleDto {
    @Schema(description = "Role ID", example = "1")
    private Long id;
    @NotBlank(message = "code.required")
    @Schema(description = "Unique code for the role", example = "ADMIN")
    private String code;
}
