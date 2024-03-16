package com.example.vortex_games.service;

import com.example.vortex_games.entity.User;
import com.example.vortex_games.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Methods Manual

    public Optional<User> buscarUsuarioPorName(String name){
        return userRepository.findByUsername(name);
    }

    public Optional<User> buscarPorId(Integer id){ return userRepository.findById(id);}
}
