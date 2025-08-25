package com.spring.boot.Dto;
import com.spring.boot.Controller.Vm.CategoryVm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(name = "ProductDto", description = "Data Transfer Object for Product")
    public class ProductDto implements Serializable {
    @Schema(description = "Product ID", example = "1")
        private Long id;

    @NotBlank(message = "Name.is.required")
    @Size(min = 2, max = 50, message = "Name.must.be.between.2.and.50.char")
    @Schema(description = "Name of the product", example = "pasta...")
        private String name;
    @Schema(description = "Image URL or path of the product", example = "https://example.com/product.png")
    @NotBlank(message="imagePath.is.required")
        private String imagePath;
    @Schema(description = "Description of the product", example = "Delicious Italian pasta with tomato sauce and parmesan cheese")
    @NotBlank(message="description.is.required")
    private String description;
    @Schema(description = "Price of the product", example = "1200.50")
    @NotNull(message="price.is.required")
    private Double price;
    @Schema(description = "Category of the product")
        private CategoryVm category;

    }
