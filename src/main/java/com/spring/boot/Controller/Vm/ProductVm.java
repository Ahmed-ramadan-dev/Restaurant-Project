package com.spring.boot.Controller.Vm;
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
@Schema(name = "ProductVm", description = "View Model for Product")
public class ProductVm implements Serializable {
    @Schema(description = "Product ID", example = "1")
    private Long id;
    @Schema(description = "Name of the product", example = "pasta...")
    @NotBlank(message = "product.name.required")
    @Size(min = 2, max = 50, message = "product.name.size")
    private String name;
    @Schema(description = "Image URL or path of the product", example = "https://example.com/product.png")
    @NotBlank(message="product.imagePath.required")
    private String imagePath;
    @NotBlank(message="product.description.required")
    @Schema(description = "Description of the product", example = "Delicious Italian pasta with tomato sauce and parmesan cheese")
    private String description;
    @NotNull(message="product.price.required")
    @Schema(description = "Price of the product", example = "1200.50")
    private Double price;

}
