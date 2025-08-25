package com.spring.boot.Controller.Vm.TokenVm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "TokenResponseVm", description = "View Model for JWT Token Response")
public class TokenResponseVm {
        @Schema(description = "JWT token returned after successful authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        private String token;

    }
