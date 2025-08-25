package com.spring.boot.Mapper;
import com.spring.boot.Dto.ChefDto;
import com.spring.boot.Model.Chef;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ChefMapper {
    ChefMapper CHEF_MAPPER = Mappers.getMapper(ChefMapper.class);
    Chef tochef(ChefDto chefDto);
    ChefDto tochefDto(Chef chef);
}
