package com.spring.boot.Mapper;
import com.spring.boot.Controller.Vm.security.UserSignUpVm;
import com.spring.boot.Controller.Vm.security.UserVm;
import com.spring.boot.Dto.Security.UserDto;
import com.spring.boot.Model.Security.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "roles", target = "rolesVm")
    UserVm toUserVm(User user);
    @Mapping(source = "rolesVm", target = "roles")
    User toUser (UserVm userVm);
    @Mapping(source = "rolesDto", target = "roles")
    @Mapping(source = "userDetailDto", target = "userDetails")
    User toUser (UserDto userDto);
    @Mapping(source = "roles", target = "rolesDto")
    @Mapping(source = "userDetails", target = "userDetailDto")
    UserDto toUserDto(User user);
    @Mapping(source = "userDetails", target = "userDetailDto")
    UserSignUpVm toUserSignUpVm(User user);
    @Mapping(source = "userDetailDto", target = "userDetails")
    User toUser (UserSignUpVm userSignUpVm);
}
