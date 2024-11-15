package com.porempresa.jwt.controladores;

import com.porempresa.jwt.modelos.AuthenticationRequest;
import com.porempresa.jwt.modelos.AuthenticationResponse;
import com.porempresa.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class InicioController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return ResponseEntity.status(HttpStatus.OK).body("Hello World!");
    }

    @PostMapping("/authenticate")

    public ResponseEntity<?> createAthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            //throw new Exception("Incorrecto usuario o contraseña", e);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        System.out.println(jwt);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
