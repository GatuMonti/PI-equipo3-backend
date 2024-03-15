package com.example.vortex_games.controller;

import com.example.vortex_games.entity.Calificacion;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.exception.BadRequestException;
import com.example.vortex_games.exception.ResourceNotFoundException;
import com.example.vortex_games.service.CalificacionService;
import com.example.vortex_games.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;
    @Autowired
    private ProductService productService;

    @PostMapping("/calificar")
    public ResponseEntity<Calificacion> calificar(@RequestBody Calificacion calificacion) throws BadRequestException {
        List<Calificacion> calificacionesBuscadas = calificacionService.buscarPorUsuarioAndProducto(calificacion);
        if(calificacionesBuscadas.size()>0){
            throw new BadRequestException("El usuario solamente puede votar una sola ves");
        }
        return ResponseEntity.ok(calificacionService.calificar(calificacion));
    }

    @GetMapping("/calificacionPromedio/{id}")
    public ResponseEntity<Double> calificacionPromedio(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Product> productoBuscado = productService.searchById(id);
        if(productoBuscado.isPresent()){
            return ResponseEntity.ok(productoBuscado.get().getPromedioCalificaciones());
        }else{
            throw new ResourceNotFoundException("No se encontro el producto");
        }
    }
}
