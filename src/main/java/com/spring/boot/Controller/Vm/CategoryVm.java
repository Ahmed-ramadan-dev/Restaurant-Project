package com.spring.boot.Controller.Vm;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "CategoryVm", description = "View Model for Category")
public class CategoryVm implements Serializable {
    @Schema(description = "Category ID", example = "1")
    private Long id;
    @Schema(description = "Name of the category", example = "pasta")
    @NotBlank(message = "category.name.required")
    @Size(min = 2, max = 50, message = "category.name.size")
    private String name ;
    @Schema(description = "Logo URL or path of the category", example = "https://example.com/logo.png")
    @NotBlank(message="category.logo.required")
    private String logo;
    @Schema(description = "Flag representing the category", example = "active")
    @NotBlank(message="category.flag.required")
    private String flag;

}
