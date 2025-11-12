package com.example.prueba.providers;

import java.util.Collections;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Es el encargado de autenticar las credenciales.
 */
@Service
@RequiredArgsConstructor
public class Provedor implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = userDetailsService.loadUserByUsername(email);
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        return new UsernamePasswordAuthenticationToken(
            user, 
            password, 
            Collections.emptyList()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
