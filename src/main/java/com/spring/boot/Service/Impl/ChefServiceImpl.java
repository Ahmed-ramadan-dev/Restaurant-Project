package com.spring.boot.Service.Impl;

import com.spring.boot.Dto.ChefDto;
import com.spring.boot.Mapper.ChefMapper;
import com.spring.boot.Repo.ChefRepo;
import com.spring.boot.Service.ChefService;
import com.spring.boot.Exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



@Service
public class ChefServiceImpl implements ChefService {
    @Autowired
    private ChefRepo chefRepo;

 //Retrieves a list of all chefs.
            //Throws an exception if no chefs are found in the database.
    @Override
    @Cacheable(value = "chefs", key = "#page + ':' + #size")
    public Page<ChefDto> getAllChef(Pageable pageable) {
        if (chefRepo.findAll(pageable).isEmpty()) {
            throw new SystemException("No.chef.found", HttpStatus.NOT_FOUND);
        }

       return chefRepo.findAll(pageable).map(dto->ChefMapper.CHEF_MAPPER.tochefDto(dto));

    }
}
