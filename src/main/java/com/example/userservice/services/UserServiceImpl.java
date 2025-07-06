package com.example.userservice.services;

import com.example.userservice.Exceptions.*;
import com.example.userservice.dtos.SendEmailDto;
import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        //
        //    @Autowired
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
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

        SendEmailDto  sendEmailDto = new SendEmailDto();
        sendEmailDto.setTo_email(email);
        sendEmailDto.setSubject("Welcome to Ecommerce App.");
        sendEmailDto.setBody("Hello "+firstName+" "+lastName);
        String sendEmailDtoStr=null;
        try {
            sendEmailDtoStr = objectMapper.writeValueAsString(sendEmailDto);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        kafkaTemplate.send("send_email",sendEmailDtoStr);
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
