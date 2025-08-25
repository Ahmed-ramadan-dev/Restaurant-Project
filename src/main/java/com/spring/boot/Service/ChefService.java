package com.spring.boot.Service;

import com.spring.boot.Dto.ChefDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface ChefService {
    Page<ChefDto> getAllChef(Pageable pageable);
}
