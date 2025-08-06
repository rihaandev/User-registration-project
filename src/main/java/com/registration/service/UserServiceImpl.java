package com.registration.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.registration.domain.User;
import com.registration.domain.value.LoginRequestVO;
import com.registration.domain.value.UserRequestVO;
import com.registration.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation layer for User Service
 * @author mohamed.rihan
 */
@Service
public class UserServiceImpl implements UserService {

    protected static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public ResponseEntity<String> register(UserRequestVO request) {

        if (request == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Request is empty");
        }
        if (userJpaRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setGender(request.getGender());
        user.setRole(request.getRole());

        //Mapping the ip and country for user
        try {
            String ip = getPublicIP();
            String country = getCountryFromIP(ip);
            user.setIpAddress(ip);
            user.setCountry(country);
        } catch (Exception e) {
            logger.error("Error occurred", e);
        }

        logger.info("[Register] Saving the user: {}", user.getEmail());
        userJpaRepository.save(user);

        logger.info("[Register] User saved successfully");
        return ResponseEntity.ok("User created successfully");
    }

    @Override
    public ResponseEntity<String> verify(LoginRequestVO request) {

        Optional<User> userOpt = userJpaRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = userOpt.get();
        if (request.getPassword().equals(user.getPassword())) {
            logger.info("[Verify] User verified successfully");
            return ResponseEntity.ok("User Verified");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public ResponseEntity<String> deleteUser(String email) {
        Optional<User> user = userJpaRepository.findByEmail(email);
        user.ifPresent(value -> userJpaRepository.delete(value));
        logger.info("[DeleteUser] User deleted successfully");
        return ResponseEntity.ok("User deleted successfully");
    }

    /******************************* PRIVATE METHODS *************************************/

    /*
     * Method for get public ip
     * @return
     * @throws IOException
     */
    private String getPublicIP() throws IOException {
        URL url = new URL("https://api.ipify.org");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return br.readLine();
    }

    /*
     * Method for get country from the ip
     * @param ip
     * @return
     * @throws IOException
     */
    private String getCountryFromIP(String ip) throws IOException {
        URL url = new URL("http://ip-api.com/json/" + ip);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String response = reader.readLine();

        // Parse JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response);
        return node.get("country").asText();
    }
}