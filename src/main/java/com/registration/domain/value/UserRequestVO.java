package com.registration.domain.value;

import com.registration.domain.constants.Gender;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Request class for User
 * @author mohamed.rihan
 */
@Data
public class UserRequestVO {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "Email must not be blank")
    private String email;
    private Gender gender;
    private String role = "USER";
}