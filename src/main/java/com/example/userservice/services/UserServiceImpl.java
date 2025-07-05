package com.example.userservice.services;

import com.example.userservice.Exceptions.*;
import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    //public static final SecretKey key= Keys.secretKeyFor(SignatureAlgorithm.HS256);

//    @Autowired
   private final UserRepository userRepository;
    //
//    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        //
        //    @Autowired
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User createUser(String firstName, String lastName, String email,String mobileNumber ,Date dob ,String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            throw  new UserAlreadyExistsException("User already exists with email: "+email);
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
    public User getUserById(Long userId) throws UserNotFoundException {

        Optional<User> userOptional=userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found with id: "+userId);
        }
        return userOptional.get();
    }





    /*private  User validateTokenWithDB(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        if(tokenOptional.isEmpty()){
            return null;
        }
        Token tokenEntity = tokenOptional.get();
        if(tokenEntity.getExpiryAt().before(new Date())){
            return null;
        }

        return tokenEntity.getUser();
    }*/


    @Override
    public boolean isUserExists(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }


}
