package com.spring.boot.Service.Security;

import com.spring.boot.Controller.Vm.security.UserVm;

public interface UserService {
    UserVm getUserByUsername(String username);

}
