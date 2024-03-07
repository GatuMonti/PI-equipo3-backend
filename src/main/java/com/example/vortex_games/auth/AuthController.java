package com.example.vortex_games.auth;

import com.example.vortex_games.exception.ExistingProductException;
import com.example.vortex_games.exception.ResourceNotFoundException;
import com.example.vortex_games.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PutMapping (value = "changeRol")
    public ResponseEntity<?> changeRol(@RequestBody User user)  {
        return ResponseEntity.ok(authService.changeRole(user));
    }

    @GetMapping(value = "listar-usuarios")
    public ResponseEntity<List<User>> listUsers() throws ResourceNotFoundException{
        List<User> listaUsuarios=authService.listUsers();
        if(!listaUsuarios.isEmpty()){
            return ResponseEntity.ok(listaUsuarios);
        }
        else {
            throw  new ResourceNotFoundException("No hay usuarios registrados");
        }
    }
}
