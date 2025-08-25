package com.spring.boot.Config.Security.CustomProvider;
import com.spring.boot.Controller.Vm.security.UserVm;
import com.spring.boot.Exception.SystemException;
import com.spring.boot.Service.Security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.stream.Collectors;
//@Component
public class CustomAuthProvider implements AuthenticationProvider {
@Autowired
private UserService userService;
    @Override
    public Authentication authenticate(Authentication authentication)  {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        UserVm userVm = userService.getUserByUsername(username);

        if (userVm == null) {
            throw new SystemException("Username.not.found", HttpStatus.NOT_FOUND);
        }
        if (!password.equals(userVm.getPassword())) {
            throw new SystemException("Password.not.match", HttpStatus.UNAUTHORIZED);

        }
        List<GrantedAuthority>roles = userVm.getRolesVm().stream().map(
                roleDto -> new SimpleGrantedAuthority("ROLE_"+ roleDto.getCode())).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userVm, password, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
