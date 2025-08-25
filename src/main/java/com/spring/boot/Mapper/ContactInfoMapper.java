package com.spring.boot.Mapper;
import com.spring.boot.Dto.ContactInfoDto;
import com.spring.boot.Model.ContactInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactInfoMapper {
    ContactInfoMapper CONTACT_INFO_MAPPER = Mappers.getMapper(ContactInfoMapper.class);
    ContactInfo toContactInfo(ContactInfoDto contactInfoDto);
    ContactInfoDto toContactInfoDto(ContactInfo contactInfo);

}
