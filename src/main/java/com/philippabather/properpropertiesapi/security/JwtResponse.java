package com.philippabather.properpropertiesapi.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * JwtResponse - define el objeto que devuelve el JWT
 *
 * @author Philippa Bather
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String token;
    private String username;
    private List<String> roles;
}