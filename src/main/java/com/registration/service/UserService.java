package com.registration.service;

import com.registration.domain.User;
import com.registration.domain.value.LoginRequestVO;
import com.registration.domain.value.UserRequestVO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Service layer for User
 * @author mohamed.rihan
 */
public interface UserService {

    /*
     * Method for register user
     * @param request
     * @return
     */
    ResponseEntity<String> register(UserRequestVO request);

    /*
     * Method for verify user
     * @param request
     * @return
     */
    ResponseEntity<String> verify(LoginRequestVO request);

    /*
     * Method for getting all users
     * Note: Only user with role ADMIN can see all the users list
     * @return
     */
    List<User> getAllUsers();

    /*
     * Method for deleting an user by email
     * Note: Only user with role ADMIN can delete the users
     * @return
     */
    ResponseEntity<String> deleteUser(String email);
}