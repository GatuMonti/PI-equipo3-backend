package com.example.vortex_games.service;

import com.example.vortex_games.entity.User;
import com.example.vortex_games.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioService {
    @Autowired
    private UserRepository userRepository;

    public List<User> listarUsuarios(){
        return userRepository.findAll();
    }
}
