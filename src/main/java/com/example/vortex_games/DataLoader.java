package com.example.vortex_games;

import com.example.vortex_games.user.UserRepository;
import com.example.vortex_games.user.User;
import com.example.vortex_games.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.stereotype.Component;

import org.springframework.security.crypto.password.PasswordEncoder;


@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Constructor
    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        if(userRepository.findById(1).isEmpty()) {
            String password = passwordEncoder.encode("admin");
            userRepository.save(new User(1,"admin", password, "admin", "admin", "adminDir", Role.ADMIN));
        }

        if(userRepository.findByUsername("user").isEmpty()) {
            String password2 = passwordEncoder.encode("user");
            userRepository.save(new User(2,"user", password2, "user", "user", "userDir", Role.USER));
        }


    }
}