package com.example.vortex_games.controller;

import com.example.vortex_games.Dto.DtoFavoritos;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.entity.User;
import com.example.vortex_games.exception.ResourceNotFoundException;
import com.example.vortex_games.service.ProductService;
import com.example.vortex_games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/usuario")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @PostMapping("/registrar")
    public ResponseEntity<String> addFavoriteProduct(@RequestBody DtoFavoritos dtoFavoritos) throws ResourceNotFoundException {
        Optional<Product> productoBuscado = productService.searchById(dtoFavoritos.getIdProducto());
        Optional<User> usuarioEncontrado = userService.buscarPorId(dtoFavoritos.getIdUsuario());
        if(productoBuscado.isEmpty()) throw new ResourceNotFoundException("No existe el usuario");
        if(productoBuscado.isEmpty()) throw new ResourceNotFoundException("No existe el producto");
        userService.addFavoriteProduct(dtoFavoritos);
        return ResponseEntity.ok("El usuario "+usuarioEncontrado.get().getUsername()+" agrego correctamente el producto: "+productoBuscado.get().getName()+" a sus favoritos");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeFavoriteProduct(@RequestBody DtoFavoritos dtoFavoritos) throws ResourceNotFoundException {
        Optional<Product> productoBuscado = productService.searchById(dtoFavoritos.getIdProducto());
        Optional<User> usuarioEncontrado = userService.buscarPorId(dtoFavoritos.getIdUsuario());
        if(productoBuscado.isEmpty()) throw new ResourceNotFoundException("No existe el usuario");
        if(productoBuscado.isEmpty()) throw new ResourceNotFoundException("No existe el producto");
        userService.removeFavoriteProduct(dtoFavoritos);
        return ResponseEntity.ok("El usuario "+usuarioEncontrado.get().getUsername()+" removio correctamente el producto: "+productoBuscado.get().getName()+" de sus favorito");
    }

    @GetMapping("listar-todos/{userId}")
    public ResponseEntity<Set<Product>> listarFavoritosDeUsuario(@PathVariable Integer userId){
        Set<Product> productosFavoritos = userService.listarFavoritos(userId);
        return ResponseEntity.ok(productosFavoritos);
    }
}
