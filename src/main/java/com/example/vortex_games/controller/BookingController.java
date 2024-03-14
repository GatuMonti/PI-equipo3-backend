package com.example.vortex_games.controller;

import com.example.vortex_games.Dto.DtoFechasBusqueda;
import com.example.vortex_games.entity.Booking;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.entity.User;
import com.example.vortex_games.exception.ResourceNotFoundException;
import com.example.vortex_games.service.BookingService;
import com.example.vortex_games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @PostMapping("/add-booking")
    public ResponseEntity<Booking> agregarBooking(@RequestBody Booking booking) throws ResourceNotFoundException{
        Optional<User> usuarioEncontrado= userService.buscarUsuarioPorName(booking.getUsuario().getUsername());
        if(!usuarioEncontrado.isPresent()){
            throw new ResourceNotFoundException("Usuario no existe");
        }
        return ResponseEntity.ok(bookingService.addBooking(booking)) ;
    }

    @GetMapping("/list-bookings")
    public ResponseEntity<List<Booking>> listarBooking() throws ResourceNotFoundException {
        List<Booking> reservas= bookingService.listaReservas();
        if(reservas.size()<=0){
            throw new ResourceNotFoundException("No existen reservas");
        }
        else{
            return ResponseEntity.ok(reservas);
        }
    }

    @GetMapping("/list-productos-disponibles")
    public ResponseEntity<List<Product>>  productosDisponibles(@RequestBody DtoFechasBusqueda dtoFechasBusqueda) throws ResourceNotFoundException{
        List<Product> productDisponible= bookingService.ProductosDisponibles(dtoFechasBusqueda);
        if(productDisponible.size()<=0){
            throw new ResourceNotFoundException("No hay productos disponibles");
        }
        else {
            return ResponseEntity.ok(productDisponible);
        }
    }


}
