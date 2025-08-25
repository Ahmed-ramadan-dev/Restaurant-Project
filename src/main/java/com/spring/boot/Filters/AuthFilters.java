package com.spring.boot.Filters;
import com.spring.boot.Config.Security.SecurityConfig;
import com.spring.boot.Config.TokenHandler;
import com.spring.boot.Controller.Vm.security.UserVm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AuthFilters extends OncePerRequestFilter {
    @Autowired
    private TokenHandler tokenHandler;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            String path = request.getRequestURI();
        if (checkPath(path, SecurityConfig.PUBLIC_URLS)) {
            return true;
        }
        return false;
    }

    @SneakyThrows

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (Objects.isNull(token)|| !token.startsWith("Bearer "))  {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        token = token.substring(7);
        UserVm userVm1 = tokenHandler.validateToken(token);
        if(Objects.isNull(userVm1)) {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        List<GrantedAuthority> roles = userVm1.getRolesVm().stream().map(
                roleDto -> new SimpleGrantedAuthority("ROLE_"+ roleDto.getCode())).collect(Collectors.toList());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new  UsernamePasswordAuthenticationToken(userVm1, userVm1.getPassword() , roles);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }// /auth/login
private boolean checkPath(String originalPath,String [] publicPaths) {
        String [] parts = originalPath.split("/");// auth  login
        String finalPath = "/" + parts[1] + "/**"; // /auth/**
        return Arrays.asList(publicPaths).contains(finalPath);
}//"/auth/**","/swagger-ui/**","/v3/**"

    }

