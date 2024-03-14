package com.example.vortex_games.service;

import com.example.vortex_games.Dto.DtoFechasBusqueda;
import com.example.vortex_games.entity.Booking;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.entity.User;
import com.example.vortex_games.exception.ResourceNotFoundException;
import com.example.vortex_games.repository.BookingRepository;
import com.example.vortex_games.repository.ProductRepository;
import com.example.vortex_games.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
     private ProductRepository productRepository;

    //Methods Manual

  /* public Booking addBooking(Booking booking) {
        Optional<User> usuarioReserva = userRepository.findByUsername(booking.getUsuario().getUsername());
        if (usuarioReserva.isPresent()) {
            booking.setUsuario(usuarioReserva.get());
            Set<Product> productosEnReserva = new HashSet<>();
            for (Product producto : booking.getProductosReservados()) {
                Optional<Product> productoEncontrado = productRepository.findByName(producto.getName());
                productosEnReserva.add(productoEncontrado.get());
            }
            booking.setProductosReservados(productosEnReserva);
            bookingRepository.save(booking);
            return booking;
        }
        return null;
    }*/

    public Booking addBooking(Booking booking) {
        Optional<User> usuarioReserva = userRepository.findByUsername(booking.getUsuario().getUsername());
        if (usuarioReserva.isPresent()) {
            booking.setUsuario(usuarioReserva.get());
            Set<Product> productosEnReserva = new HashSet<>();
            for (Product producto : booking.getProductosReservados()) {
                boolean productoExistente = false;
                for (Booking reservaExistente : this.listaReservas()) {
                    if (reservaExistente.getProductosReservados().contains(producto)) {
                        productoExistente = true;
                        break;
                    }
                }
                // Si el producto no existe en ninguna reserva existente, agregarlo
                if (!productoExistente) {
                    Optional<Product> productoEncontrado = productRepository.findByName(producto.getName());
                    productosEnReserva.add(productoEncontrado.get());
                }
            }

            // Verificar que las fechas de la reserva no se crucen con ninguna reserva existente
            if (!fechasCoinciden(booking)) {
                // Asignar los productos únicos a la reserva
                booking.setProductosReservados(productosEnReserva);
                // Guardar la reserva
                return bookingRepository.save(booking);
            }
        }
        return null;
    }


    private boolean fechasCoinciden(Booking newBooking) {
        List<Booking> existingBookings = this.listaReservas();
        for (Booking existingBooking : existingBookings) {
            if (newBooking.getFechaInicio().isBefore(existingBooking.getFechaFin()) &&
                    newBooking.getFechaFin().isAfter(existingBooking.getFechaInicio())) {
                return true;
            }
        }
        return false;
    }

    public List<Booking> listaReservas(){
        return bookingRepository.findAll();
    }



    public List<Product> ProductosDisponibles(DtoFechasBusqueda dtoFechasBusqueda){
        List<Booking> reservas = bookingRepository.findAll();
        List<Product> productosDisponibles = new ArrayList<>();
        List<Product> productosDeLaAplicacion = productRepository.findAll();
        // Iterar sobre todas las reservas
        for (Booking book: reservas) {
            // Comprobar si las fechas de inicio y fin de la reserva están fuera del rango especificado
            if(dtoFechasBusqueda.getInicio().isAfter(book.getFechaFin()) || dtoFechasBusqueda.getFin().isBefore(book.getFechaInicio())) {
                productosDisponibles.addAll(book.getProductosReservados());
            }
        }
        // Iterar sobre todos los productos de la aplicación
        for (Product pro: productosDeLaAplicacion ) {
            boolean productoEnReserva = false;
            // Verificar si el producto está presente en alguna reserva dentro del rango especificado
            for (Booking reserva: reservas) {
                for (Product productoReservado: reserva.getProductosReservados()) {
                    if(pro.getId().equals(productoReservado.getId())){
                        productoEnReserva = true;
                        break;
                    }
                }
                if(productoEnReserva) {
                    break;
                }
            }
            // Si el producto no está presente en ninguna reserva, agregarlo a la lista de productos disponibles
            if (!productoEnReserva) {
                productosDisponibles.add(pro);
            }
        }
        return productosDisponibles;
    }


}
