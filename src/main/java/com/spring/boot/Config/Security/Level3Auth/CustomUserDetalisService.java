package com.spring.boot.Config.Security.Level3Auth;
import com.spring.boot.Controller.Vm.security.UserVm;
import com.spring.boot.Service.Security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Service
public class CustomUserDetalisService implements UserDetailsService {
@Autowired
private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserVm userVm =userService.getUserByUsername(username);
        return new CustomUserDetails(userVm);
    }
}
