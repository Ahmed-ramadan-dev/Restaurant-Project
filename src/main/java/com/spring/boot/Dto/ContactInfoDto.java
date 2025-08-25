package com.spring.boot.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ContactInfoDto", description = "Data Transfer Object for Contact Information")
public class ContactInfoDto implements Serializable {
    @Schema(description = "Contact ID", example = "1")
    private Long id;

    @NotBlank(message = "contact.name.required")
    @Size(min = 2, max = 50, message = "contact.name.size")
    @Schema(description = "Name of the contact", example = "Ahmed Ramadan")
    private String name;

    @NotBlank(message = "contact.email.required")
    @Email(message = "contact.email.invalid")
    @Schema(description = "Email address of the contact", example = "ahmed@example.com")
    private String email;

    @NotBlank(message = "contact.subject.required")
    @Size(min = 2, max = 100, message = "contact.subject.size")
    @Schema(description = "Subject of the contact message", example = "Inquiry about services")
    private String subject;

    @NotBlank(message = "contact.message.required")
    @Size(min = 5, max = 1000, message = "contact.message.size")
    @Schema(description = "Content of the contact message", example = "Hello, I would like to know more about your services...")
    private String message;

}

