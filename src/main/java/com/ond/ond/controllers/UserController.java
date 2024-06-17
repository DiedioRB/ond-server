package com.ond.ond.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ond.ond.models.dto.UserDTO;
import com.ond.ond.models.entities.User;
import com.ond.ond.repositories.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;

import org.modelmapper.ModelMapper;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController{

    @Autowired
    private UserRepository userRepository;
    private ModelMapper modelMapper = new ModelMapper();

    private byte[] salt = "OND".getBytes();

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody User user) throws Exception {
        String passwordHash = this.hash(user.getPassword());

        User found = userRepository.findByEmailAndPassword(user.getEmail(), passwordHash.toString()).orElse(null);
        if(found == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        UserDTO userDTO = modelMapper.map(found, UserDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user) throws Exception {
        String passwordHash = this.hash(user.getPassword());

        User found = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(found != null){
            throw new Exception("E-mail já existe");
        }

        user.setPassword(passwordHash);
        User registered = userRepository.save(user);
        UserDTO userDTO = modelMapper.map(registered, UserDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    private String hash(String text){
        MessageDigest digest;
        byte[] passwordHash = null;
        StringBuilder sb = new StringBuilder();
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt);
            passwordHash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            for(int i=0; i < passwordHash.length ;i++)
            {
                sb.append(Integer.toString((passwordHash[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
