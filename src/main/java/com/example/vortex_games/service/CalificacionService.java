package com.example.vortex_games.service;

import com.example.vortex_games.Dto.DtoCalificacion;
import com.example.vortex_games.Dto.DtoCalificacionPromedio;
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

    public DtoCalificacion calificar(Calificacion calificacion) {
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

        return calificacionADto(calificacionGuardada) ;
    }

    public DtoCalificacionPromedio devolverPromedio(Long id){
        DtoCalificacionPromedio calificacionPromedio = new DtoCalificacionPromedio();
        Product productoBuscado = productRepository.findById(id).get();
        calificacionPromedio.setProductName(productoBuscado.getName());
        calificacionPromedio.setCalificacionPromedio(productoBuscado.getPromedioCalificaciones());
        return calificacionPromedio;

    }

    private DtoCalificacion calificacionADto(Calificacion calificacion){
        DtoCalificacion dtoCalificacion = new DtoCalificacion();
        dtoCalificacion.setId(calificacion.getId());
        dtoCalificacion.setUsername(calificacion.getUsuario().getUsername());
        dtoCalificacion.setProductoName(calificacion.getProducto().getName());
        dtoCalificacion.setValorCalificacion(calificacion.getValor());
        return dtoCalificacion;
    }


}
