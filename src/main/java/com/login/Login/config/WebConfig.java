package com.login.Login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Marca esta clase como una configuración de Spring para personalizar el comportamiento de Spring MVC.
    // Implementa WebMvcConfigurer para sobreescribir métodos relacionados con la configuración de Spring MVC.

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configura CORS (Cross-Origin Resource Sharing) para permitir solicitudes entre dominios.
        registry.addMapping("/**")
                // Define que se permita el acceso a todas las rutas del servidor.
                .allowedOrigins("http://localhost:8080", "http://localhost:3000")
                // Especifica los orígenes permitidos, como las URL donde se encuentran las aplicaciones cliente.
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // Lista los métodos HTTP permitidos para las solicitudes entre dominios.
                .allowedHeaders("*")
                // Permite todos los encabezados en las solicitudes.
                .allowCredentials(true);
                // Permite el uso de credenciales, como cookies o cabeceras de autenticación, en solicitudes CORS.
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configura cómo se manejan los recursos estáticos en el proyecto.
        registry.addResourceHandler("/**")
                // Indica que todos los recursos (archivos estáticos) deben estar disponibles en cualquier ruta.
                .addResourceLocations("classpath:/static/");
                // Especifica la ubicación de los recursos estáticos, en este caso, dentro del directorio `/static/`.
                // Spring buscará archivos estáticos en `src/main/resources/static`.
    }
}
