package com.registration.service;

import com.registration.domain.User;
import com.registration.domain.value.LoginRequestVO;
import com.registration.domain.value.UserRequestVO;
import com.registration.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Optional;

/**
 * Test service class for User service
 * @author mohamed.rihan
 */
@ExtendWith(MockitoExtension.class)
class UserRegistrationServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegisterUser_success() {
        UserRequestVO request = new UserRequestVO();
        request.setName("Rihan");
        request.setEmail("rihan@example.com");
        request.setPassword("password");

        when(userJpaRepository.findByEmail(any())).thenReturn(Optional.empty());

        userService.register(request);

        verify(userJpaRepository).save(any(User.class));
    }

    @Test
    void ToVerifyUser_success() {
        LoginRequestVO request = new LoginRequestVO();
        request.setEmail("rihan@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail("rihan@example.com");
        user.setPassword("password");

        when(userJpaRepository.findByEmail("rihan@example.com"))
                .thenReturn(Optional.of(user));

        ResponseEntity<String> response = userService.verify(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Verified", response.getBody());
    }

    @Test
    void verifyUser_emailNotFound() {
        LoginRequestVO request = new LoginRequestVO();
        request.setEmail("unknown@example.com");
        request.setPassword("any");

        when(userJpaRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = userService.verify(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testRegisterUser_duplicateEmail_throwsException() {
        UserRequestVO request = new UserRequestVO();
        request.setName("Rihan");
        request.setEmail("rihan@example.com");
        request.setPassword("password");

        when(userJpaRepository.findByEmail(any())).thenReturn(Optional.of(new User()));

        ResponseEntity<String> response = userService.register(request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());
    }

    @Test
    void testGetAllUsers_returnsUserValues() {
        User user = new User();
        user.setName("Rihan");
        user.setEmail("rihan@example.com");
        user.setPassword("password");

        when(userJpaRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("Rihan", users.get(0).getName());
        assertEquals("rihan@example.com", users.get(0).getEmail());
    }
}