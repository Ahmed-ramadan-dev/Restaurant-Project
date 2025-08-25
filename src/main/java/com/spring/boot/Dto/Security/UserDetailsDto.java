package com.spring.boot.Dto.Security;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserDetailsDto", description = "Data Transfer Object for User Details")
public class UserDetailsDto {
    @Schema(description = "User ID", example = "1")
    private Long id;

        @Min(value = 18, message = "user.age.invalid")
        @Schema(description = "Age of the user, minimum 18", example = "25")
        private int age;

        @NotBlank(message = "user.phone.invalid")
        @Pattern(
                regexp = "^(01)[0-9]{9}$",
                message = "user.phone.invalid"
        )
        @Schema(description = "Phone number of the user (Egyptian format)", example = "01012345678")
        private String phoneNumber;

        @NotBlank(message = "user.address.required")
        @Schema(description = "Address of the user", example = "123 Cairo Street, Egypt")
        private String address;




}
