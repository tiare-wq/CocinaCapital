package com.cocinacapital.usuarios.repository;

import com.cocinacapital.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class UsuarioRepository extends <Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
