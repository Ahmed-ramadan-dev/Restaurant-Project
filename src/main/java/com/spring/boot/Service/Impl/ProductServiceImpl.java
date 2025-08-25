package com.spring.boot.Service.Impl;

import com.spring.boot.Controller.Vm.ProductVm;
import com.spring.boot.Dto.ProductDto;
import com.spring.boot.Mapper.ProductMapper;
import com.spring.boot.Model.Category;
import com.spring.boot.Model.Product;
import com.spring.boot.Repo.CategoryRepo;
import com.spring.boot.Repo.ProductRepo;
import com.spring.boot.Service.ProductService;
import com.spring.boot.Exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Cacheable(value = "allProduct", key = "#pageable.pageNumber + ':' + #pageable.pageSize")
    @Override
    public Page<ProductVm> getAllProductByIdAsc(Pageable pageable) {
        Page<Product> productsList = productRepo.findAll(pageable);
        if(productsList.isEmpty()){
            throw new SystemException("the.list.is.empty", HttpStatus.NOT_FOUND);        }
        return productRepo.findAllByOrderByIdAsc(pageable).map(vm->ProductMapper.PRODUCT_MAPPER.toProductVm(vm));
    }

    // Retrieves a list of products by category ID.
// Throws an exception if the category ID does not exist or no products are found.
    @Override
    @Cacheable(value = "allProduct", key =  "#pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<ProductVm> getProductById(Pageable pageable,Long id) {
        
            if (Objects.isNull(id)) {
                throw new SystemException("id.is.null", HttpStatus.BAD_REQUEST);
            }
            Page<Product> productList = productRepo.findByCategoryIdOrderByIdAsc(pageable,id);
            if (productList.isEmpty()) {
                throw new SystemException("product.is.empty", HttpStatus.NOT_FOUND);
            }
            return productList.map(product -> ProductMapper.PRODUCT_MAPPER.toProductVm(product));



    }

    @Override
    @Cacheable(value = "allProduct", key = "#pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<ProductVm> findProductByIdAndKey(Long id, String key, Pageable pageable) {
        if(id==null||id.toString().trim().isEmpty()){
            throw new SystemException("id.must.not.null", HttpStatus.BAD_REQUEST);
        }
        if(key == null || key.trim().isEmpty()){
            throw new SystemException("key.is.empty", HttpStatus.BAD_REQUEST);
        }

Page<Product>productList=productRepo.findAllByCategoryIdAndNameContainingIgnoreCaseOrderByIdAsc(id, key,pageable);
        if(productList.isEmpty()){
            throw new SystemException("product.is.empty", HttpStatus.NOT_FOUND);
        }
        return productList.map(vm -> ProductMapper.PRODUCT_MAPPER.toProductVm(vm));
    }
// Creates a new product.
// Throws an exception if the ID is provided or if required fields are missing.

    @Override
    @CacheEvict(value = "allProduct" ,allEntries = true)
    public void createNewProduct(ProductDto productDto) {

            if (Objects.nonNull(productDto.getId())) {
                throw new SystemException("id.must.be.null", HttpStatus.BAD_REQUEST);
            }
            if (productDto.getCategory().getId() != null || productDto.getCategory().getFlag() != null || productDto.getCategory().getLogo() != null) {
                throw new SystemException("please.enter.Category.name.only", HttpStatus.BAD_REQUEST);
            }
            String categoryName = productDto.getCategory().getName();
            Optional<Category> category = categoryRepo.findByName(categoryName);
            if (!category.isPresent()) {
                throw new SystemException("category.not.found", HttpStatus.NOT_FOUND);

            }

            Product product = ProductMapper.PRODUCT_MAPPER.toProduct(productDto);
            product.setCategory(category.get());
            productRepo.save(product);

    }
// Creates a list of new products.
// Throws an exception if the list is null or empty,
// if any product has a non-null ID, or if required fields are missing.

    @Override
    @CacheEvict(value = "allProduct" ,allEntries = true)
    public void createNewListProduct(List<ProductDto> productDtoList) {

            if (  productDtoList == null|| productDtoList.isEmpty() ) {
                throw new SystemException("please.enter.product.list", HttpStatus.BAD_REQUEST);
            }
            if (productDtoList.stream().anyMatch(dto -> dto.getId() != null)) {
                throw new SystemException("id.must.be.null", HttpStatus.BAD_REQUEST);
            }
            if (productDtoList
                    .stream()
                    .anyMatch(list -> list.getCategory().getId() != null || list.getCategory().getFlag() != null || list.getCategory().getLogo() != null)) {
                throw new SystemException("please.enter.category.name.only", HttpStatus.BAD_REQUEST);

            }
            List<String> categoryNames = productDtoList
                    .stream().map(list -> list.getCategory().getName())
                    .distinct()
                    .collect(Collectors.toList());

            List<Category> categoryList = categoryRepo.findByNameIn(categoryNames);
            if (categoryList.size() != categoryNames.size()) {
                throw new SystemException("One.or.more.categories.not.found", HttpStatus.NOT_FOUND);
            }
            // تحويل قائمة الأصناف إلى Map لتسهيل الربط بالاسم
            Map<String, Category> categoryMap = categoryList.stream()
                    .collect(Collectors.toMap(Category::getName, c -> c));

            // تحويل المنتجات وربطها بالصنف
            List<Product> products = productDtoList.stream()
                    .map(dto -> {
                        Product product = ProductMapper.PRODUCT_MAPPER.toProduct(dto);
                        String catName = dto.getCategory().getName();
                        product.setCategory(categoryMap.get(catName));
                        return product;
                    })
                    .collect(Collectors.toList());

            productRepo.saveAll(products);

    }
// Updates a single product.
// Throws an exception if the ID is missing, not found, or if required fields are missing.

    @Override
    @CacheEvict(value = "allProduct" ,allEntries = true)
    public void UpdateProduct(ProductVm productVm) {



            if (Objects.isNull(productVm.getId())) {
                throw new SystemException("id.must.be.not.null", HttpStatus.BAD_REQUEST);
            }
            if (!productRepo.findById(productVm.getId()).isPresent()) {
                throw new SystemException("id.not.found", HttpStatus.NOT_FOUND);
            }
            Product product = ProductMapper.PRODUCT_MAPPER.toProduct(productVm);
            productRepo.save(product);


    }
// Updates a list of products.
// Throws an exception if the list is null or empty,
// if any product has a null ID,
// if any ID does not exist in the database,
// or if required fields are missing.

    @Override
    @CacheEvict(value = "allProduct" ,allEntries = true)
    public void UpdateListProduct(List<ProductDto> productDtoList) {

            if (productDtoList.stream().anyMatch(Vm ->Vm.getId() == null)) {
                throw new SystemException("id.must.not.null", HttpStatus.BAD_REQUEST);
            }
          List<Long>Ids = productDtoList.stream().map(ProductDto::getId).collect(Collectors.toList());
            if (productRepo.findAllById(Ids).size() != Ids.size()){
                throw new SystemException("one.or.more.id.not.found", HttpStatus.NOT_FOUND);
            }
            List<Product> product = ProductMapper.PRODUCT_MAPPER.toProductList(productDtoList);
            productRepo.saveAll(product);

    }
// Deletes a product by its ID.
// Throws an exception if the ID does not exist in the database.

    @Override
    @CacheEvict(value = "allProduct" ,allEntries = true)
    public void deleteProductById(Long id) {

            if (!productRepo.findById(id).isPresent()) {
                throw new SystemException("id.not.found", HttpStatus.NOT_FOUND);
            }
            productRepo.deleteById(id);


    }
// Deletes multiple products by their IDs.
// Throws an exception if the list is null or empty,
// or if one or more IDs do not exist in the database.

    @Override
    @CacheEvict(value = "allProduct" ,allEntries = true)
    public void deleteListProductById(List<Long> ids) {

            if (ids.isEmpty()) {
                throw new SystemException("id.must.not.empty", HttpStatus.BAD_REQUEST);
            }
            if (productRepo.findAllById(ids).size() != ids.size()) {
                throw new SystemException("one.or.more.id.not.found", HttpStatus.NOT_FOUND);
            }
            productRepo.deleteAllById(ids);

    }
}
