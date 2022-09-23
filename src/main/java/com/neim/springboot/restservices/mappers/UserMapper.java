package com.neim.springboot.restservices.mappers;

import com.neim.springboot.restservices.dtos.UserMapStructDTO;
import com.neim.springboot.restservices.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "role", target = "rolename")
    })
    UserMapStructDTO userToUserDto(User user);

    List<UserMapStructDTO> usersToUserDtos(List<User> users);
}
