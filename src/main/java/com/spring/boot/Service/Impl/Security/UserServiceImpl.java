package com.spring.boot.Service.Impl.Security;

import com.spring.boot.Controller.Vm.security.UserVm;
import com.spring.boot.Exception.SystemException;
import com.spring.boot.Mapper.UserMapper;
import com.spring.boot.Model.Security.User;
import com.spring.boot.Repo.Security.UserRepo;
import com.spring.boot.Service.Security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;


    @Override
    public UserVm getUserByUsername(String username) {
        if(Objects.isNull(username)) {
            throw new SystemException("user.Name.must.be.not.null", HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepo.findByUsername(username);
        if(!user.isPresent()) {
            throw new SystemException("user.Name.not.found", HttpStatus.NOT_FOUND);
        }
            return UserMapper.USER_MAPPER.toUserVm(user.get());
    }



}
