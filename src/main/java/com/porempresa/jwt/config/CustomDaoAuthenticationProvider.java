package com.porempresa.jwt.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // No se realiza ninguna verificación adicional de la contraseña
        // Esto está improvisado porque luego se deberá encriptar y validar por contraseña
    }
}
