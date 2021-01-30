package com.guavapay.ms.auth.mapper;

import com.guavapay.ms.auth.entity.User;
import com.guavapay.ms.auth.security.CustomUserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "fullName", source = "user", qualifiedByName = "mapFullName")
    public abstract CustomUserPrincipal toPrincipal(User user);

    @Named("mapFullName")
    String mapFullName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
