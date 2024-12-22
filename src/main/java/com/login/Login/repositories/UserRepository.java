
package com.login.Login.repositories;

import com.login.Login.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Marca esta interfaz como un repositorio de Spring para manejar operaciones de acceso a datos.
    // Extiende JpaRepository, lo que proporciona métodos CRUD básicos y consultas personalizadas.

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.email = :email AND u.password = :password")
    Optional<User> findByUsernameAndEmail(@Param("username") String username, 
                                          @Param("email") String email, 
                                          @Param("password") String password);

    // Declara una consulta personalizada utilizando JPQL para encontrar un usuario según su username, email y password.
    // Devuelve un Optional<User> para manejar la posibilidad de que no exista un usuario que coincida.

    boolean existsByUsername(String username);
    // Método derivado que verifica si existe un usuario con un nombre de usuario específico.

    boolean existsByEmail(String email);
    // Método derivado que verifica si existe un usuario con un correo electrónico específico.
}

