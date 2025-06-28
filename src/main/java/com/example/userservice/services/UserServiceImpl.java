package com.example.userservice.services;

import com.example.userservice.dtos.CreateUserRequestDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.TokenRepository;
import com.example.userservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    //public static final SecretKey key= Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final long Expiration_Time_In_MS=10*60*60*1000;

//    @Autowired
   private UserRepository userRepository;
//
//    @Autowired
    private TokenRepository tokenRepository;
//
//    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecretKey secretKey;
    public UserServiceImpl(SecretKey secretKey,UserRepository userRepository, TokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.secretKey=secretKey;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User createUser(String firstName, String lastName, String email,String mobileNumber ,Date dob ,String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            return null;
        }
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setMobileNumber(mobileNumber);
        user.setDob(dob);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            //throw user not found exception
            return null;
        }
        User user = userOptional.get();
//        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
//            //throw login failed exception
//            return null;
//        }
//        Token token = new Token();
//        token.setUser(user);
//        token.setToken(RandomStringUtils.random(128));
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE,10);
//        Date expireDate = calendar.getTime();
//        token.setExpiryAt(expireDate);
//        return tokenRepository.save(token);
        Date expirationDate = new Date(System.currentTimeMillis() + Expiration_Time_In_MS);
        Map<String,Object> claims = new HashMap<>();
        claims.put("userid",user.getId());
        claims.put("email",user.getEmail());
        //claims.put("subject",user.getEmail());

        String token =Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .setSubject(user.getEmail())
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();
        Token tokenObj = new Token();
        tokenObj.setToken(token);
        return tokenObj;
    }

    @Override
    public User validateToken(String token) {
        Claims claims;
        try{
           claims=Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        }catch (io.jsonwebtoken.ExpiredJwtException e){
            System.out.println("token expired. "+e.getMessage());
            return null;
        } catch (io.jsonwebtoken.JwtException ex){
            System.out.println("Invalid token. "+ex.getMessage());
            return null;
        }
        String email=claims.getSubject();
        if(email==null||email.isEmpty()){
            return null;
        }
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            System.out.println("No user exists with email :"+email);
        }
        return userOptional.get();
    }


    private  User validateTokenWithDB(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        if(tokenOptional.isEmpty()){
            return null;
        }
        Token tokenEntity = tokenOptional.get();
        if(tokenEntity.getExpiryAt().before(new Date())){
            return null;
        }

        return tokenEntity.getUser();
    }

    @Override
    public void logout(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        if(tokenOptional.isPresent()){
            Token tokenEntity = tokenOptional.get();
            tokenEntity.setExpiryAt(new Date());
            tokenEntity.setUpdatedAt(new Date());
            tokenRepository.save(tokenEntity);
        }
    }


}
