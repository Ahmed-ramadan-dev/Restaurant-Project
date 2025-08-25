package com.spring.boot.Dto;
import com.spring.boot.Controller.Vm.ProductVm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CategoryDto", description = "Data Transfer Object for Category")
public class CategoryDto  implements Serializable {
    @NotBlank
    @Schema(description = "Category ID", example = "1")
    private Long id;
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 char")
    @Schema(description = "Name of the category", example = "Electronics")
    private String name;
    @NotBlank(message="logo is required")
    @Schema(description = "Logo URL of the category", example = "https://example.com/logo.png")
    private String logo;
    @NotBlank(message="flag is required")
    @Schema(description = "Flag representing the category", example = "active")
    private String flag;
    @Schema(description = "List of products under this category")
    private List<ProductVm> products;


}
