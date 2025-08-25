package com.spring.boot.Controller;
import com.spring.boot.Dto.ChefDto;
import com.spring.boot.Service.ChefService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Tag(name = "Chef", description = "APIs for managing chefs")
public class ChefController {
    @Autowired
    private ChefService chefService;
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).USER.toString()) or hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @GetMapping("/chef/get-all-chef")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chefs retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No chefs found in the database")
    })

    @Operation(
            summary = "Get all chefs",
            description = "Retrieves a paginated list of all chefs. Accessible to both users and admins."
    )
    public ResponseEntity<Page<ChefDto>> getAllChef(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable Pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(chefService.getAllChef(Pageable));

    }

}
