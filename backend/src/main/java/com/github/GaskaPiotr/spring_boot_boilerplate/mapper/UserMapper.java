package com.github.GaskaPiotr.spring_boot_boilerplate.mapper;

import com.github.GaskaPiotr.spring_boot_boilerplate.dto.UserResponse;
import com.github.GaskaPiotr.spring_boot_boilerplate.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target="role", source="role.name")
    UserResponse userToUserResponse(User user);
}
