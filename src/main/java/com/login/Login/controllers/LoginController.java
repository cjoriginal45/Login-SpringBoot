
package com.login.Login.controllers;

import com.login.Login.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    // Marca esta clase como un controlador que maneja solicitudes web y devuelve vistas.

    @Autowired
    private UserService userService;
    // Inyección de la clase `UserService` para acceder a la lógica de autenticación de usuarios.

    @PostMapping("/logear")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {
        // Mapea las solicitudes POST a "/logear" para procesar el inicio de sesión.

        if (authenticateUser(username, email, password)) {
            // Si las credenciales son válidas, autentica al usuario.
            
            // Almacena el nombre de usuario en la sesión para identificar al usuario en otras vistas.
            session.setAttribute("username", username);

            return "redirect:/principal";
            // Redirige al usuario a la página principal tras un inicio de sesión exitoso.
        } else {
            // Si las credenciales son incorrectas, muestra un mensaje de error en el formulario de login.

            model.addAttribute("error", "Credenciales incorrectas");
            // Agrega un atributo llamado "error" al modelo para que sea accesible en la vista.

            return "index"; 
            // Devuelve la vista del formulario de login para que el usuario intente de nuevo.
        }
    }

    private boolean authenticateUser(String username, String email, String password) {
        // Método auxiliar para autenticar al usuario mediante el servicio de usuarios.
        return userService.authenticate(username, email, password);
    }
}

