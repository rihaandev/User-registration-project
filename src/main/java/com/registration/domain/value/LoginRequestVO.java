package com.registration.domain.value;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Request class for User Login
 * @author mohamed.rihan
 */
@Data
public class LoginRequestVO {

    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;
}