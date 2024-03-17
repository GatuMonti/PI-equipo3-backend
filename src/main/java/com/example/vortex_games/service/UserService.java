package com.example.vortex_games.service;

import com.example.vortex_games.Dto.DtoFavoritos;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.entity.User;
import com.example.vortex_games.repository.ProductRepository;
import com.example.vortex_games.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    //Methods Manual

    public Optional<User> buscarUsuarioPorName(String name){
        return userRepository.findByUsername(name);
    }

    public Optional<User> buscarPorId(Integer id){ return userRepository.findById(id);}

    public void addFavoriteProduct(DtoFavoritos dtoFavoritos) {
        User usuarioBuscado = userRepository.findById(dtoFavoritos.getIdUsuario()).get();
        Product product = productRepository.findById(dtoFavoritos.getIdProducto()).get();
        usuarioBuscado.getFavoriteProducts().add(product);
        userRepository.save(usuarioBuscado);
    }

    public void removeFavoriteProduct(DtoFavoritos dtoFavoritos){
        User usuarioBuscado = userRepository.findById(dtoFavoritos.getIdUsuario()).get();
        Product product = productRepository.findById(dtoFavoritos.getIdProducto()).get();
        usuarioBuscado.getFavoriteProducts().remove(product);
        userRepository.save(usuarioBuscado);
    }

    public Set<Product> listarFavoritos(Integer usuarioId){
        User usuarioBuscado = userRepository.findById(usuarioId).get();
        return usuarioBuscado.getFavoriteProducts();
    }
}
