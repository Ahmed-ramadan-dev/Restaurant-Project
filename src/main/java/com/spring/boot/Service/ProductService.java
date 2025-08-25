package com.spring.boot.Service;

import com.spring.boot.Controller.Vm.ProductVm;
import com.spring.boot.Dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductVm> getAllProductByIdAsc(Pageable pageable);
    Page<ProductVm> getProductById(Pageable pageable,Long id);
    Page<ProductVm> findProductByIdAndKey( Long id,  String key, Pageable pageable);
    void createNewProduct(ProductDto productDto);
   void createNewListProduct( List<ProductDto> productDtoList);
   void UpdateProduct( ProductVm productVm);
   void UpdateListProduct( List<ProductDto> productDtoList);
  void  deleteProductById(Long id);
    void deleteListProductById( List<Long> ids);

}
