package com.spring.boot.Config.Security.Level3Auth;

import com.spring.boot.Controller.Vm.security.UserVm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private UserVm userVm;

    public CustomUserDetails(UserVm userVm) {
        this.userVm = userVm;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userVm.getRolesVm().stream().map(vm->new SimpleGrantedAuthority("ROLE_"+vm.getCode())).collect(Collectors.toList());


    }

    @Override
    public String getPassword() {
        return "{bcrypt}"+userVm.getPassword();
    }

    @Override
    public String getUsername() {
        return userVm.getUsername();
    }
}
