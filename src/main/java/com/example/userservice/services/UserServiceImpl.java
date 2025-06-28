package com.example.userservice.services;

import com.example.userservice.dtos.CreateUserRequestDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.TokenRepository;
import com.example.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            //throw login failed exception
            return null;
        }
        Token token = new Token();
        token.setUser(user);
        token.setToken(RandomStringUtils.random(128));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,10);
        Date expireDate = calendar.getTime();
        token.setExpiryAt(expireDate);
        return tokenRepository.save(token);
    }

    @Override
    public User validateToken(String token) {
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
}
