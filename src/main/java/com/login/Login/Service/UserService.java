package com.login.Login.Service;

import com.login.Login.models.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.login.Login.repositories.UserRepository;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class UserService {
    // Marca esta clase como un servicio que contiene la lógica de negocio relacionada con los usuarios.
    // Esta clase será inyectada donde sea necesario, como en `UserController`.

    @Autowired
    private UserRepository repository;
    // Inyecta automáticamente una instancia de `UserRepository` para interactuar con la base de datos.

    public List<User> getUsers() {
        // Método para obtener todos los usuarios registrados en la base de datos.
        return repository.findAll(); // Llama al método `findAll` del repositorio para devolver la lista completa de usuarios.
    }

    public boolean isUserRegistered(User user) {
        // Verifica si un usuario específico ya está registrado.
        List<User> users = repository.findAll(); // Obtiene la lista de todos los usuarios.

        for (User u : users) { // Recorre cada usuario en la lista.
            if (u.equals(user)) { // Compara el usuario actual con el proporcionado.
                return true; // Devuelve `true` si encuentra una coincidencia.
            }
        }
        return false; // Devuelve `false` si no encuentra el usuario.
    }

    public boolean createUser(User user) {
        // Registra un nuevo usuario en la base de datos.

        if (repository.existsByUsername(user.getUsername())) {
            // Verifica si el nombre de usuario ya está en uso.
            throw new DataIntegrityViolationException("El nombre de usuario ya está en uso");
            // Lanza una excepción específica si el username ya existe.
        }

        if (repository.existsByEmail(user.getEmail())) {
            // Verifica si el correo electrónico ya está en uso.
            throw new DataIntegrityViolationException("El correo electrónico ya está en uso");
            // Lanza una excepción específica si el email ya existe.
        }

        repository.save(user); // Guarda el usuario en la base de datos.
        return true; // Devuelve `true` si el usuario fue registrado exitosamente.
    }

    public boolean authenticate(String username, String email, String password) {
        // Autentica un usuario verificando el username, email y contraseña.

        Optional<User> user = repository.findByUsernameAndEmail(username, email, password);
        // Busca un usuario que coincida con el username, email y contraseña.

        if (user.isPresent()) {
            // Si el usuario existe, retorna `true` (autenticación exitosa).
            return true;
        }
        return false; // Retorna `false` si no encuentra al usuario (credenciales inválidas).
    }
}

