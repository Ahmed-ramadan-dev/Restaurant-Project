package com.spring.boot.Service.Impl;

import com.spring.boot.Controller.Vm.CategoryVm;

import com.spring.boot.Mapper.CategoryMapper;
import com.spring.boot.Model.Category;
import com.spring.boot.Repo.CategoryRepo;
import com.spring.boot.Service.CategoryService;
import com.spring.boot.Exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
// Retrieves a list of categories.
// Throws an exception if the list is empty.
@Cacheable(value = "allCategory", key =  "#pageable.pageNumber + ':' + #pageable.pageSize")
@Override
    public Page<CategoryVm> getAllCategory(Pageable pageable) {

            if (categoryRepo.findAllByOrderByNameAsc(pageable).isEmpty()) {
                throw new SystemException("the.list.is.empty", HttpStatus.NOT_FOUND);
            }

            return categoryRepo.findAll(pageable)
                    .map(CategoryMapper.CATEGORY_MAPPER::toCategoryVm);




    }
    @Cacheable(value = "allCategory", key =  "#pageable.pageNumber + ':' + #pageable.pageSize")
    @Override
    public Page<CategoryVm> getAllCategoryByNameAsc(Pageable pageable) {
        Page<Category> page = categoryRepo.findAllByOrderByNameAsc(pageable);

        if (page.isEmpty()) {
            throw new SystemException("the.list.is.empty", HttpStatus.NOT_FOUND);
        }

        return page.map(CategoryMapper.CATEGORY_MAPPER::toCategoryVm);
    }

// Creates a single category.
// Throws an exception if the ID is provided or if required fields are missing.
@CacheEvict(value = "allCategory" ,allEntries = true)
@Override
    public void createCategory(CategoryVm categoryVm) {

            if (Objects.nonNull(categoryVm.getId())) {
                throw new SystemException("id.must.be.null", HttpStatus.BAD_REQUEST);
            }
            Category category = CategoryMapper.CATEGORY_MAPPER.toCategory(categoryVm);
            categoryRepo.save(category);


    }
    // Creates multiple categories.
// Throws an exception if the list is null or empty,
// if any category has a non-null ID, or if required fields are missing.
    @CacheEvict(value = "allCategory" ,allEntries = true)
    @Override
        public void createListOfCategory(List<CategoryVm> categoryVmList) {

                if (Objects.isNull(categoryVmList) || categoryVmList.isEmpty()) {
                    throw new SystemException("please.enter.category",HttpStatus.BAD_REQUEST);
                }
                if (categoryVmList.stream().anyMatch(Vm -> Vm.getId() != null)) {
                    throw new SystemException("id.must.be.null", HttpStatus.BAD_REQUEST);
                }
                List<Category> categoryList1 = CategoryMapper.CATEGORY_MAPPER.toCategoryList(categoryVmList);
                categoryRepo.saveAll(categoryList1);

    }
    // Updates a single category.
// Throws an exception if the ID is missing, not found, or if required fields are missing.
    @Override
    @CacheEvict(value = "allCategory" ,allEntries = true)
    public void updateCategory(CategoryVm categoryVm) {

            if (Objects.isNull(categoryVm.getId())) {
                throw new SystemException("id.must.not.be.null", HttpStatus.BAD_REQUEST);
            }
            if (!categoryRepo.findById(categoryVm.getId()).isPresent()) {
                throw new SystemException("id.not.found", HttpStatus.NOT_FOUND);
            }
            Category category = CategoryMapper.CATEGORY_MAPPER.toCategory(categoryVm);

            categoryRepo.save(category);

    }
    // Updates multiple categories.
// Throws an exception if the list is null or empty,
// if any item has a null ID,
// if any ID is not found in the database,
// or if required fields are missing.
    @Override
    @CacheEvict(value = "allCategory" ,allEntries = true)
    public void updateListOfCategory(List<CategoryVm> categoryList) {


            if (categoryList == null || categoryList.isEmpty()) {
                throw new SystemException("please.enter.list.of.category", HttpStatus.BAD_REQUEST);
            }

            if (categoryList.stream().anyMatch(Vm -> Vm.getId() == null)) {
                throw new SystemException("id.must.be.not.null", HttpStatus.BAD_REQUEST);
            }
            List<Long> ids = categoryList.stream().map(CategoryVm::getId).collect(Collectors.toList());
           List< Category> category = categoryRepo.findByIdIn(ids);
            if(ids.size() != category.size()) {
                throw new SystemException("id.not.found", HttpStatus.NOT_FOUND);


            }

            List<Category> categoryList1 = CategoryMapper.CATEGORY_MAPPER.toCategoryList(categoryList);
            categoryRepo.saveAll(categoryList1);



    }
    // Deletes a single category by ID.
// Throws an exception if the ID does not exist in the database.
    @Override
    @CacheEvict(value = "allCategory" ,allEntries = true)
    public void deleteCategoryById(Long id) {


            if (!categoryRepo.findById(id).isPresent()) {
                throw new SystemException("id.not.found", HttpStatus.NOT_FOUND);
            }
            categoryRepo.deleteById(id);


    }
    // Deletes multiple categories by their IDs.
// Throws an exception if the list is null or empty,
// or if one or more IDs are not found in the database.
    @Override
    @CacheEvict(value = "allCategory" ,allEntries = true)

    public void deleteListOfCategoryById(List<Long> ids) {


            if (ids == null || ids.isEmpty()) {
            throw new SystemException("id.must.be.null", HttpStatus.BAD_REQUEST);
            }
            if(ids.size() != categoryRepo.findAllById(ids).size()){
                throw new SystemException("one.or.more.id.not.found", HttpStatus.NOT_FOUND);

            }
            categoryRepo.deleteAllById(ids);


    }

}


