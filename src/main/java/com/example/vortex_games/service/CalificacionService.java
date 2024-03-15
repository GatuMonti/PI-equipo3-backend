package com.example.vortex_games.service;

import com.example.vortex_games.entity.Calificacion;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.entity.User;
import com.example.vortex_games.repository.CalificacionRepository;
import com.example.vortex_games.repository.ProductRepository;
import com.example.vortex_games.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Log4j2
@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Calificacion> buscarPorUsuarioAndProducto(Calificacion calificacion){
        User usuarioEncontrado = userRepository.findById(calificacion.getUsuario().getId()).get();
        Product productoEncontrado = productRepository.findById(calificacion.getProducto().getId()).get();
        return calificacionRepository.findByUsuarioAndProducto(usuarioEncontrado,productoEncontrado);
    }

    public Calificacion calificar(Calificacion calificacion) {
        User usuarioEncontrado = userRepository.findById(calificacion.getUsuario().getId()).get();
        Product productoEncontrado = productRepository.findById(calificacion.getProducto().getId()).get();
        calificacion.setUsuario(usuarioEncontrado);
        calificacion.setProducto(productoEncontrado);
        Calificacion calificacionGuardada = calificacionRepository.save(calificacion);

        //Con esta logica seteo el promedio de la calificacion en producto
        double valorSumado = productoEncontrado.getPromedioCalificaciones();
        double calPromedio = 0;
        List<Calificacion> calificacionesBuscadas = calificacionRepository.findByUsuarioAndProducto(usuarioEncontrado,productoEncontrado);
        for (Calificacion cal : calificacionesBuscadas) {
            valorSumado += cal.getValor();
        }
        log.info("Valor Sumado: "+valorSumado);
        log.info("Valor a dividir:"+productoEncontrado.getCalificacions().size());
        if (!calificacionesBuscadas.isEmpty()) {
            calPromedio = valorSumado / productoEncontrado.getCalificacions().size();
        }
        log.info("promedio: "+calPromedio);
        productoEncontrado.setPromedioCalificaciones(calPromedio);
        productRepository.save(productoEncontrado);

        return calificacionGuardada;
    }


}
