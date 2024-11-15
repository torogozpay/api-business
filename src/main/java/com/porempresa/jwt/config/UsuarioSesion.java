package com.porempresa.jwt.config;

import com.porempresa.jwt.modelos.User;
import com.porempresa.jwt.modelos.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class UsuarioSesion {
    @Autowired
    private UserRepository userRepository;
    public UsuarioSesion(){
    }

}
