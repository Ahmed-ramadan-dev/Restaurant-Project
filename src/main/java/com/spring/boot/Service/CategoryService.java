package com.spring.boot.Service;

import com.spring.boot.Controller.Vm.CategoryVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CategoryService {
    Page<CategoryVm> getAllCategory(Pageable pageable);
   Page<CategoryVm> getAllCategoryByNameAsc(Pageable pageable);
    void createCategory(CategoryVm categoryVm);
   void createListOfCategory( List<CategoryVm> categoryList);
  void  updateCategory( CategoryVm categoryVm);
    void updateListOfCategory( List<CategoryVm> categoryList);
   void deleteCategoryById( Long id);
   void deleteListOfCategoryById( List<Long> ids);
}
