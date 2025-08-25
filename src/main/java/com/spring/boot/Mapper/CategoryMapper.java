package com.spring.boot.Mapper;
import com.spring.boot.Controller.Vm.CategoryVm;
import com.spring.boot.Dto.CategoryDto;
import com.spring.boot.Model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);
    Category toCategory(CategoryDto categoryDto);
    CategoryDto toCategoryDto(Category category);
    Category toCategory(CategoryVm categoryVm);
    CategoryVm toCategoryVm(Category category);
 List<CategoryVm> toCategoryVmList (List<Category> categoryList);
    List<Category> toCategoryList (List<CategoryVm> categoryVmList);
}
