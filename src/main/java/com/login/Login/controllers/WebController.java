package com.login.Login.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    // Marca esta clase como un controlador Spring MVC para manejar solicitudes web.

    // Ruta para mostrar el formulario de inicio de sesión
    @GetMapping("/login")
    public String showLogin() {
        return "index";
        // Devuelve la vista "index.html" ubicada en la carpeta `templates`.
        // Sirve como la página de inicio de sesión para los usuarios.
    }

    // Ruta para mostrar el formulario de registro
    @GetMapping("/register")
    public String showRegister() {
        return "register";
        // Devuelve la vista "register.html" ubicada en la carpeta `templates`.
        // Sirve como la página de registro para nuevos usuarios.
    }

    // Ruta para acceder a la página principal
    @GetMapping("/principal")
    public String getPrincipalPage(HttpSession session, Model model) {
        // Obtiene la sesión HTTP y el modelo para manejar datos en la vista.

        String username = (String) session.getAttribute("username");
        // Recupera el atributo "username" de la sesión. Esto identifica al usuario actual.

        if (username != null) {
            // Si el usuario está autenticado (existe un nombre de usuario en la sesión):

            model.addAttribute("name", username);
            // Agrega el nombre de usuario al modelo para que sea accesible en la vista.

            return "principal";
            // Devuelve la vista "principal.html", que corresponde a la página principal.
        } else {
            // Si no hay usuario autenticado (la sesión no contiene un nombre de usuario):

            return "redirect:/index";
            // Redirige al formulario de inicio de sesión para que el usuario se autentique.
        }
    }
}

