package com.spring.boot.Service.Impl.Security;

import com.spring.boot.Config.TokenHandler;
import com.spring.boot.Controller.Vm.TokenVm.TokenResponseVm;
import com.spring.boot.Controller.Vm.security.UserLoginVm;
import com.spring.boot.Controller.Vm.security.UserSignUpVm;
import com.spring.boot.Controller.Vm.security.UserVm;
import com.spring.boot.Dto.Security.RoleDto;
import com.spring.boot.Dto.Security.UserDto;
import com.spring.boot.Exception.SystemException;
import com.spring.boot.Mapper.UserMapper;
import com.spring.boot.Model.Security.Role;
import com.spring.boot.Model.Security.User;
import com.spring.boot.Model.Security.UserDetails;
import com.spring.boot.Repo.Security.RoleRepo;
import com.spring.boot.Repo.Security.UserRepo;
import com.spring.boot.Service.Security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private @Lazy PasswordEncoder passwordEncoder ;
    @Autowired
    private TokenHandler tokenHandler;

    @Override
    public void signUpControl(UserDto userDto) {
        if (userDto.getId()!=null||userDto.getUserDetailDto().getId()!=null){
            throw new SystemException("please.id.must.be.null",HttpStatus.BAD_REQUEST);
        }
        if (userDto.getRolesDto() != null) {
            for (RoleDto role : userDto.getRolesDto()) {
                if (role.getId() != null) {
                    throw new SystemException("role.id.must.be.null", HttpStatus.BAD_REQUEST);
                }
            }
        }

        if (userRepo.findByUsername(userDto.getUsername()).isPresent()) {
            throw new SystemException("user.Name.already.signedUp", HttpStatus.CONFLICT);
        }

        User user = UserMapper.USER_MAPPER.toUser(userDto);

        // تشفير الباسورد قبل الحفظ
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        // جلب الأدوار من DB بناءً على الاسم
        List<Role> roles = userDto.getRolesDto().stream()
                .map(RoleDto::getCode)
                .map(roleName -> roleRepo.findByCode(roleName)
                        .orElseThrow(() -> new SystemException("role.not.found", HttpStatus.NOT_FOUND)))
                .collect(Collectors.toList());


        user.setRoles(roles);
        // ربط UserDetails بالـ User
        UserDetails userDetails = new UserDetails();
        userDetails.setAge(userDto.getUserDetailDto().getAge());
        userDetails.setPhoneNumber(userDto.getUserDetailDto().getPhoneNumber());
        userDetails.setAddress(userDto.getUserDetailDto().getAddress());
        userDetails.setUser(user);       // مهم جداً
        user.setUserDetails(userDetails);
        userRepo.save(user);
    }

    @Override
    public void signUp(UserSignUpVm userSignUpVm) {
     if (userSignUpVm.getUserDetailDto().getId()!=null){
         throw new SystemException("please.id.must.be.null",HttpStatus.BAD_REQUEST);
     }

        if (userRepo.findByUsername(userSignUpVm.getUsername()).isPresent()) {
            throw new SystemException("user.Name.already.signedUp", HttpStatus.CONFLICT);
        }

        User user = UserMapper.USER_MAPPER.toUser(userSignUpVm);

     // تشفير الباسورد قبل الحفظ
     String encodedPassword = passwordEncoder.encode(userSignUpVm.getPassword());
     user.setPassword(encodedPassword);
     /*
        // جلب الأدوار من DB بناءً على الاسم
        List<Role> roles = userVm.getRolesVm().stream()
                .map(RoleVm::getCode)
                .map(roleName -> roleRepo.findByCode(roleName)
                        .orElseThrow(() -> new SystemException("role.not.found", HttpStatus.NOT_FOUND)))
                .collect(Collectors.toList());
*/
         Role userRole = roleRepo.findByCode("USER").orElseThrow(
                 () -> new SystemException("role.not.found", HttpStatus.NOT_FOUND)
         );
        // ربط UserDetails بالـ User
        UserDetails userDetails = new UserDetails();
        userDetails.setAge(userSignUpVm.getUserDetailDto().getAge());
        userDetails.setPhoneNumber(userSignUpVm.getUserDetailDto().getPhoneNumber());
        userDetails.setAddress(userSignUpVm.getUserDetailDto().getAddress());
        userDetails.setUser(user);       // مهم جداً
        user.setUserDetails(userDetails);
        user.setRoles(List.of(userRole));

        userRepo.save(user);
    }

    @Override
    public TokenResponseVm login(UserLoginVm userLoginVm) {

    User user1 = userRepo.findByUsername(userLoginVm.getUsername())
            .orElseThrow(() -> new SystemException("user.name.not.found", HttpStatus.NOT_FOUND));
     if (!passwordEncoder.matches(userLoginVm.getPassword(), user1.getPassword())) {
         throw new SystemException("user.password.not.match", HttpStatus.UNAUTHORIZED);
     }
     if(Objects.isNull(userLoginVm)){
         throw new SystemException("user.not.found", HttpStatus.NOT_FOUND);

     }
        UserVm userVm1 = UserMapper.USER_MAPPER.toUserVm(user1);
        return new TokenResponseVm(tokenHandler.CreateToken(userVm1));
    }
}
