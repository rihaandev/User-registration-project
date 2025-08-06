package com.registration.rest;

import com.registration.domain.mapper.UserMapper;
import com.registration.domain.value.LoginRequestVO;
import com.registration.domain.value.UserRequestVO;
import com.registration.domain.value.UserValue;
import com.registration.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for User
 * @author mohamed.rihan
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "APIs related to user")
public class UserController {

    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /*
     * Register User
     * @param userRequestVO
     * @return
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user",
            description = "Register a new user with basic details such as name, email followed by a password.",
            responses = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "Request is empty"),
            @ApiResponse(responseCode = "409", description = "Email already exists"),
    })
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestVO request, BindingResult result) {

        if (result.hasErrors()) {
            logger.info("[RegisterUser] Request is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Request is empty");
        }
        logger.info("Going to register user with user mail {}", request.getEmail());
        return userService.register(request);
    }

    /*
     *
     * @param request
     * @param result
     * @return
     */
    @PostMapping("/verify")
    @Operation(summary = "Login", description = "Verify the registered user with credentials")
    public ResponseEntity<String> verifyUser(@RequestBody @Valid LoginRequestVO request, BindingResult result) {

        if (result.hasErrors()) {
            logger.info("[VerifyUser] Request is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Request is empty");
        }
        logger.info("Going to verify user with user mail {}", request.getEmail());
        return userService.verify(request);
    }

    /*
     * Get All Users
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers")
    @Operation(summary = "Get All users", description = "Only Admin role user can access to get all the users")
    public List<UserValue> getAllUsers() {

        logger.info("Going to get all users");
        return userService.getAllUsers().stream()
                .map(UserMapper.INSTANCE::convertToValue)
                .collect(Collectors.toList());
    }

    /*
     * Delete User by Email
     * @param email
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    @Operation(summary = "Delete a User by Mail", description = "Only Admin role user can delete a user by email id")
    public ResponseEntity<String> deleteUser(@RequestParam("email") String email) {

        if (email == null) {
            logger.info("[DeleteUser] Request email is found null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email is empty");
        }
        logger.info("Going to delete user by email {}", email);
        return userService.deleteUser(email);
    }
}
