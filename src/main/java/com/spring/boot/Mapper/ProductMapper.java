package com.spring.boot.Mapper;
import com.spring.boot.Controller.Vm.CategoryVm;
import com.spring.boot.Controller.Vm.ProductVm;
import com.spring.boot.Dto.ProductDto;
import com.spring.boot.Model.Category;
import com.spring.boot.Model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDto productDto);
    ProductDto toProductDto(Product product);
    CategoryVm toCategoryVm(Category category);
    Product toProduct(ProductVm productVm);
    ProductVm toProductVm( Product product);
List<Product>toProductList(List<ProductDto> productDtoList);


}
