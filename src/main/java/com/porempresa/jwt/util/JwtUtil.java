package com.porempresa.jwt.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String serverKey;
    public JwtUtil(@Value("${server.key}") String serverKey) {
        this.serverKey = serverKey;
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    /*Extraemos el claim de forma individual*/
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }catch (Exception e){
            System.out.println("Ocurrio un error: " + e.getMessage());
            return null;
        }
    }
    /*Creamos un método para extraer los claims (payload)*/
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(serverKey)
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    /*Generamos token solamete en base a los UsrDetail*/
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    /*Generamos token en base a los claims y el UserDetail*/
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, serverKey)
                .compact();
    }
    /*private String extractString(String token, int position){
        String[] array = token.split("\\.");
        String stringSplit = array[position];
        return stringSplit;
    }
    private String extractSignature(String token){
        int lastDotIndex = token.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return token.substring(lastDotIndex + 1);
        }
        return null;
    }
    private String decode(String objeto){
         byte[] decoder = Base64.getDecoder().decode(objeto);
         return new String(decoder, StandardCharsets.UTF_8);
    }
    private byte[] decodeForByte(String objeto){
        byte[] decoder = Base64.getDecoder().decode(objeto);
        return decoder;
    }
    private static String generateHmacSha256(String data, String key) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secretKey);
            byte[] hmacBytes = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hmacBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }*/
        /*Validaremos el token*/
    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(serverKey).parseClaimsJws(token);
                return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}