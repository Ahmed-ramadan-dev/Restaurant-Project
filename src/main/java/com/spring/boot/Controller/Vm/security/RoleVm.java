package com.spring.boot.Controller.Vm.security;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(name = "RoleVm", description = "View Model for Role")
public class RoleVm {
    @Schema(description = "Role ID", example = "1")
    private Long id;
    @Schema(description = "Unique code for the role", example = "ADMIN")
    private String code;
}
