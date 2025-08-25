package com.spring.boot.Controller;
import com.spring.boot.Controller.Vm.CategoryVm;
import com.spring.boot.Service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
@RestController
@Validated
@Tag(name = "Category", description = "APIs for managing categories")
public class CategoryController  {
    @Autowired
    private CategoryService categoryService;
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).USER.toString()) or hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")

    @GetMapping("/category/getAll-category-by-Name-asc")
    @Operation(summary = "Get all categories sorted by name (ascending)",
            description = "Retrieves a paginated list of all categories sorted alphabetically by name in ascending order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "The category list is empty")
    })
    public ResponseEntity<Page<CategoryVm>> getAllCategoryByNameAsc(  @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
return ResponseEntity.ok().body(categoryService.getAllCategoryByNameAsc(pageable));
    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).USER.toString()) or hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @GetMapping("/category/getAll-category")
    @Operation(summary = "Get all categories",
            description = "Retrieves a paginated list of all categories without sorting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "The category list is empty")
    })

    public ResponseEntity <Page<CategoryVm>> getAllCategory(@RequestParam (defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10")int size){
        Pageable pageable = PageRequest.of(page, size);
return ResponseEntity.ok(categoryService.getAllCategory(pageable));
    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")

    @PostMapping("/category/create-category")
    @Operation(summary = "Create a new category",
            description = "Creates a single new category. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "ID must be null")
    })

    public ResponseEntity<Void> createCategory(@RequestBody @Valid CategoryVm categoryVm){
        categoryService.createCategory(categoryVm);
        return ResponseEntity.created(URI.create("/category/create-category")).build();
    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @PostMapping("/category/create-ListOf-category")
    @Operation(summary = "Create multiple categories",
            description = "Creates a list of new categories at once. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categories created successfully"),
            @ApiResponse(responseCode = "400", description = "List is null/empty or contains IDs")
    })
    public ResponseEntity<Void> createListOfCategory(@RequestBody @Valid List<@Valid CategoryVm> categoryList){
        categoryService.createListOfCategory(categoryList);
      return ResponseEntity.created(URI.create("/category/create-ListOf-category")).build();

    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @PutMapping("/category/update-category")
    @Operation(summary = "Update a category",
            description = "Updates an existing category. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "ID must not be null"),
            @ApiResponse(responseCode = "404", description = "ID not found")
    })
    public ResponseEntity<Void> updateCategory(@RequestBody @Valid CategoryVm categoryVm){

        categoryService.updateCategory(categoryVm);
      return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @PutMapping("/category/update-list-category")
    @Operation(summary = "Update multiple categories",
            description = "Updates a list of existing categories at once. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories updated successfully"),
            @ApiResponse(responseCode = "400", description = "List is null/empty or contains null IDs"),
            @ApiResponse(responseCode = "404", description = "One or more IDs not found")
    })
    public ResponseEntity<Void> updateListOfCategory(@RequestBody @Valid List<@Valid CategoryVm> categoryList){
        categoryService.updateListOfCategory(categoryList);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @DeleteMapping("/category/delete-by-ID-category")
    @Operation(summary = "Delete category by ID",
            description = "Deletes a category using its unique ID. Only accessible to admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "ID not found")
    })
    public ResponseEntity<Void> deleteCategoryById(@RequestParam Long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();

    }
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @DeleteMapping("/category/delete-by-list-iD-category")
    @Operation(summary = "Delete multiple categories by IDs",
            description = "Deletes multiple categories using a list of their unique IDs. Only accessible to admins.")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categories deleted successfully"),
            @ApiResponse(responseCode = "400", description = "List is null/empty"),
            @ApiResponse(responseCode = "404", description = "One or more IDs not found")
    })
public ResponseEntity<Void> deleteListOfCategoryById(@RequestParam List<Long> ids){
        categoryService.deleteListOfCategoryById(ids);
        return ResponseEntity.noContent().build();


    }
}
