package com.spring.boot.Service.Security;

import com.spring.boot.Controller.Vm.TokenVm.TokenResponseVm;
import com.spring.boot.Controller.Vm.security.UserLoginVm;
import com.spring.boot.Controller.Vm.security.UserSignUpVm;
import com.spring.boot.Controller.Vm.security.UserVm;
import com.spring.boot.Dto.Security.UserDto;

public interface AuthService  {
    void signUpControl  (UserDto userDto);
    void signUp(UserSignUpVm userSignUpVm);
    TokenResponseVm login (UserLoginVm userLoginVm);
}
