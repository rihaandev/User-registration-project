package com.registration.domain.value;

import com.registration.domain.constants.Gender;
import com.registration.domain.constants.Role;
import lombok.Data;

/**
 * Value class for User
 * @author mohamed.rihan
 */
@Data
public class UserValue {

    private Long id;
    private String name;
    private String email;
    private Gender gender;
    private String ipAddress;
    private String country;
}