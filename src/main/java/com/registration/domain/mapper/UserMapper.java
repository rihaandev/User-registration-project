package com.registration.domain.mapper;

import com.registration.domain.User;
import com.registration.domain.value.UserValue;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for User class
 * @author mohamed.rihan
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserValue convertToValue(User user);
    User convertToDomain(UserValue userValue);
}