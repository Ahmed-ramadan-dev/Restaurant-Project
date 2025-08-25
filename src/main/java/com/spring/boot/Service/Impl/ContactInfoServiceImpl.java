package com.spring.boot.Service.Impl;

import com.spring.boot.Dto.ContactInfoDto;
import com.spring.boot.Exception.SystemException;
import com.spring.boot.Mapper.ContactInfoMapper;

import com.spring.boot.Model.ContactInfo;
import com.spring.boot.Repo.ContactInfoRepo;
import com.spring.boot.Service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {
    @Autowired
    private ContactInfoRepo contactInfoRepo  ;
        //Creates a new contact info entry.
         //Validates that the incoming DTO does not contain an ID (ID must be null).
          //  If ID is present, throws a SystemException indicating that ID must not be provided by the client.
          //  Then maps the DTO to an entity and saves it to the repository.
    @Override
    public void createNewContactInfo(ContactInfoDto contactInfoDto) {
        if(Objects.nonNull(contactInfoDto.getId())) {
            throw new SystemException("id.must.be.null", HttpStatus.BAD_REQUEST);
        }

       ContactInfo contactInfo= ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfo(contactInfoDto);
        contactInfoRepo.save(contactInfo);
    }
}
