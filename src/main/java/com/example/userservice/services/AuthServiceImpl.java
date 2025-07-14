package com.example.userservice.services;

import com.example.userservice.Exceptions.ExpiredJWTException;
import com.example.userservice.Exceptions.IncorrectPasswordException;
import com.example.userservice.Exceptions.InvalidTokenException;
import com.example.userservice.Exceptions.UserNotFoundException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    public static final long Expiration_Time_In_MS=10*60*60*1000;

    @Autowired
    UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretKey secretKey;
    public AuthServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, SecretKey secretKey){
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.secretKey=secretKey;
    }

    @Override
    public Token login(String email, String password) throws IncorrectPasswordException, UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            //throw user not found exception
            throw new UserNotFoundException("User not exist with email: "+email);
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            //throw login failed exception
            throw new IncorrectPasswordException("");
        }
        Date expirationDate = new Date(System.currentTimeMillis() + Expiration_Time_In_MS);
        Map<String,Object> claims = new HashMap<>();
        claims.put("userid",user.getId());
        claims.put("email",user.getEmail());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .setSubject(Long.toString(user.getId()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        Token tokenObj = new Token();
        tokenObj.setToken(token);
        return tokenObj;
    }

    @Override
    public User validateToken(String token) throws ExpiredJWTException, InvalidTokenException, UserNotFoundException {
        Claims claims;
        try{
            claims=Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        }catch (io.jsonwebtoken.ExpiredJwtException e){
            System.out.println("token expired. "+e.getMessage());
            throw new ExpiredJWTException(e);
        } catch (io.jsonwebtoken.JwtException ex){
            System.out.println("Invalid token. "+ex.getMessage());
            throw new InvalidTokenException(ex);
        }
        String email=claims.get("email").toString();
        if(email==null||email.isEmpty()){
            return null;
        }
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            System.out.println("No user exists with email :"+email);
            throw new UserNotFoundException("No user exists with email :"+email);
        }
        return userOptional.get();
    }
}
