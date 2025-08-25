package com.spring.boot.Controller;
import com.spring.boot.Dto.ContactInfoDto;
import com.spring.boot.Service.ContactInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@Tag(name = "Contact Info", description = "APIs for managing contact information")
public class ContactInfoController {
    @Autowired
    private ContactInfoService contactInfoService;
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).USER.toString()) or hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact information created successfully"),
            @ApiResponse(responseCode = "400", description = "ID must be null")
    })
    @PostMapping("/contactinfo/create-new-contactinfo")
    @Operation(
            summary = "Create new contact information",
            description = "Creates a new contact information record. Accessible to both users and admins."
    )
    public ResponseEntity<Void> createNewContactInfo(@RequestBody @Valid ContactInfoDto contactInfoDto) {
        contactInfoService.createNewContactInfo(contactInfoDto);
    return ResponseEntity.created(URI.create("/contactinfo/create-new-contactinfo")).build();
    }
}
