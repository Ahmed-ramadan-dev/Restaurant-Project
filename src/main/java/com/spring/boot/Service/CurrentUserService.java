package com.spring.boot.Service;

import com.spring.boot.Controller.Vm.security.UserVm;
import com.spring.boot.Exception.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public UserVm getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SystemException("User.not.authenticated", HttpStatus.UNAUTHORIZED);
        }//user act login or no ?...
        UserVm userVm = (UserVm) authentication.getPrincipal();
        return userVm;
    }
}
