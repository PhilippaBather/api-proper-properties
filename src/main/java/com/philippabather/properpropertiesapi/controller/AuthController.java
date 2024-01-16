package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.LoginDTOIn;
import com.philippabather.properpropertiesapi.dto.ProprietorDTOIn;
import com.philippabather.properpropertiesapi.dto.ProprietorDTOOut;
import com.philippabather.properpropertiesapi.exception.RegistrationException;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.security.JwtResponse;
import com.philippabather.properpropertiesapi.security.JwtUtils;
import com.philippabather.properpropertiesapi.service.ProprietorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private ProprietorService proprietorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTOIn user) {
        logger.info("start: AuthController_authenticateUser");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        logger.info("end: AuthController_authenticateUser");
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<ProprietorDTOOut> registerUser(@RequestBody ProprietorDTOIn newUser) throws RegistrationException {
        logger.info("start: AuthController_registerUser");
        Proprietor user = proprietorService.findByUsername(newUser.getUsername());
        if (user == null) {
            ProprietorDTOOut proprietor = proprietorService.save(newUser);
            logger.info("end: AuthController_registerUser");
            return new ResponseEntity<>(proprietor, HttpStatus.CREATED);
        } else {
            throw new RegistrationException(newUser.getUsername());
        }

    }
}
