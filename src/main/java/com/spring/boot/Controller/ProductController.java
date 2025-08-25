package com.spring.boot.Controller;
import com.spring.boot.Controller.Vm.ProductVm;
import com.spring.boot.Dto.ProductDto;
import com.spring.boot.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@Tag(name = "Product", description = "APIs for managing products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).USER.toString()) or hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @GetMapping("/product/get-all-product-by-id-asc")
    @Operation(summary = "Get all products by ID ascending",
            description = "Retrieves all products sorted by their ID in ascending order. Accessible to users and admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No products found")
    })
    public ResponseEntity<Page<ProductVm>> getAllProductByIdAsc(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getAllProductByIdAsc(pageable));

    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).USER.toString()) or hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @GetMapping("/product/get-product-by-id-category")
    @Operation(summary = "Get products by category ID",
            description = "Retrieves all products that belong to a specific category ID. Accessible to users and admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Category ID is null"),
            @ApiResponse(responseCode = "404", description = "No products found for this category")
    })
    public ResponseEntity<Page<ProductVm>> getProductById(@RequestParam Long id,@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getProductById(pageable,id));

    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).USER.toString()) or hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @GetMapping("/product/find-product-by-id-category-and-key")
    @Operation(summary = "Find products by category ID and keyword",
            description = "Searches for products in a specific category using a keyword. Accessible to users and admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully"),
            @ApiResponse(responseCode = "400", description = "Category ID or search key is null/empty"),
            @ApiResponse(responseCode = "404", description = "No products match the search criteria")
    })
public ResponseEntity<Page<ProductVm>> findProductByIdAndKey(@RequestParam Long id, @RequestParam String key,@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
return ResponseEntity.ok(productService.findProductByIdAndKey(id, key,pageable));

    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @PostMapping("/product/create-new-prodect")
    @Operation(summary = "Create a new product",
            description = "Creates a single new product. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "ID must be null or invalid category data"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Void> createNewProduct(@RequestBody @Valid ProductDto productDto) {
        productService.createNewProduct(productDto);
       return ResponseEntity.created(URI.create("/product/create-new-prodect")).build();

    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @PostMapping("/product/create-newList-prodect")
    @Operation(summary = "Create multiple new products",
            description = "Creates a list of new products in bulk. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Products created successfully"),
            @ApiResponse(responseCode = "400", description = "List is null, empty, or contains invalid data"),
            @ApiResponse(responseCode = "404", description = "One or more categories not found")
    })
    public ResponseEntity<Void> createNewListProduct(@RequestBody @Valid List<@Valid ProductDto> productDtoList) {
        productService.createNewListProduct(productDtoList);
return ResponseEntity.created(URI.create("/product/create-newList-prodect")).build();
    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @PutMapping("/product/Update-product")
    @Operation(summary = "Update a product",
            description = "Updates an existing product. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "ID is null or invalid data"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> UpdateProduct(@RequestBody @Valid ProductVm productVm) {
        productService.UpdateProduct(productVm);
        return ResponseEntity.ok().build();


    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @PutMapping("/product/Update-list-product")
    @Operation(summary = "Update multiple products",
            description = "Updates a list of existing products in bulk. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products updated successfully"),
            @ApiResponse(responseCode = "400", description = "List contains null IDs or invalid data"),
            @ApiResponse(responseCode = "404", description = "One or more products not found")
    })
    public ResponseEntity<Void> UpdateListProduct(@RequestBody @Valid List<@Valid ProductDto> productDtoList) {
productService.UpdateListProduct(productDtoList);
return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @DeleteMapping("/product/delete-by-id-product")
    @Operation(summary = "Delete a product by ID",
            description = "Deletes a single product by its unique ID. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProductById(@RequestParam Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();

    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @DeleteMapping("/product/delete-by-list-id-product")
    @Operation(summary = "Delete multiple products by IDs",
            description = "Deletes multiple products using a list of their unique IDs. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Products deleted successfully"),
            @ApiResponse(responseCode = "400", description = "ID list is empty"),
            @ApiResponse(responseCode = "404", description = "One or more products not found")
    })
    public ResponseEntity<Void> deleteListProductById(@RequestParam List<Long> ids) {
        productService.deleteListProductById(ids);
        return ResponseEntity.noContent().build();

    }

}
