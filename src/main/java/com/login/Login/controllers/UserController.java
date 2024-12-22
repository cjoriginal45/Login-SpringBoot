/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.login.Login.controllers;

import com.login.Login.Service.UserService;
import com.login.Login.models.LoginRequest;
import com.login.Login.models.User;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    // Marca esta clase como un controlador REST, que maneja solicitudes HTTP y devuelve respuestas JSON.
    // Las solicitudes a este controlador se mapean a la ruta base "/api/usuarios".

    @Autowired
    private UserService userService;
    // Inyecta automáticamente una instancia de `UserService`, que contiene la lógica de negocio.

    @GetMapping
    public ResponseEntity<?> getUsers() {
        // Maneja solicitudes GET a "/api/usuarios".
        // Devuelve la lista de todos los usuarios.

        List<User> users = userService.getUsers(); // Obtiene los usuarios desde el servicio.
        return ResponseEntity.ok(users); // Retorna una respuesta con estado 200 OK y la lista de usuarios en el cuerpo.
    }

    @GetMapping("/check")
    public ResponseEntity<?> isUserRegistered(@RequestBody User user) {
        // Maneja solicitudes GET a "/api/usuarios/check".
        // Verifica si un usuario está registrado según los datos proporcionados en el cuerpo de la solicitud.

        boolean response = userService.isUserRegistered(user); // Llama al servicio para verificar al usuario.

        if (response) {
            return ResponseEntity.ok(user); // Retorna estado 200 OK si el usuario está registrado.
        }
        return ResponseEntity.notFound().build(); // Retorna estado 404 si no se encuentra el usuario.
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Maneja solicitudes POST a "/api/usuarios/register".
        // Permite el registro de un nuevo usuario desde un cliente que corre en "http://localhost:3000".

        try {
            if (userService.createUser(user)) {
                // Si el registro es exitoso, crea una URI con la ubicación del nuevo recurso.
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(user.getId())
                        .toUri();

                return ResponseEntity.created(location).body(user); // Retorna estado 201 Created.
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo registrar el usuario");
            // Si no se pudo registrar, retorna estado 400 Bad Request.
        } catch (DataIntegrityViolationException e) {
            // Captura excepciones relacionadas con violaciones de integridad de datos (por ejemplo, duplicados).
            String errorMessage = "Error al registrar el usuario";

            if (e.getMessage().contains("username")) {
                errorMessage = "El nombre de usuario ya está en uso";
            } else if (e.getMessage().contains("email")) {
                errorMessage = "El correo electrónico ya está en uso";
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
            // Retorna estado 409 Conflict si hay datos duplicados.
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Maneja solicitudes POST a "/api/usuarios/login".
        // Permite iniciar sesión con un objeto `LoginRequest` que contiene username/email y contraseña.

        try {
            boolean isAuthenticated = userService.authenticate(
                    loginRequest.getUsername(), loginRequest.getEmail(), loginRequest.getPassword());
            // Llama al servicio para autenticar al usuario con los datos proporcionados.

            if (isAuthenticated) {
                return ResponseEntity.ok("Login exitoso"); // Retorna estado 200 OK si las credenciales son válidas.
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
                // Retorna estado 401 Unauthorized si las credenciales son incorrectas.
            }
        } catch (Exception e) {
            // Captura cualquier excepción inesperada durante el inicio de sesión.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor: " + e.getMessage());
        }
    }
}
