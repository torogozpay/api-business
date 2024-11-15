package com.porempresa.jwt.config;

import com.porempresa.jwt.modelos.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.porempresa.jwt.modelos.User userDb = userRepository.findUserByUsername(userName);
        if(userDb != null){
            return userDb;
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        ///return new User("foo", "foo", new ArrayList<>());
    }
}