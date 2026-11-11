
package com.cocinacapital.usuarios.service;

import com.cocinacapital.usuarios.dto.UsuarioDto;
import com.cocinacapital.usuarios.entity.Usuario;
import com.cocinacapital.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // ==================================
    // CREATE
    // ==================================

    // REGISTRAR USUARIO
    public Usuario crearUsuario(UsuarioDto usuarioDto) {

        if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new IllegalArgumentException(
                "El email ya se encuentra registrado"
            );
        }

        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombre(usuarioDto.getNombre());

        // Encriptar contraseña
        usuario.setPassword(
            passwordEncoder.encode(usuarioDto.getPassword())
        );

        usuario.setUrlFoto(usuarioDto.getUrlFoto());
        usuario.setPreferencias(usuarioDto.getPreferencias());
        usuario.setTarjetas(usuarioDto.getTarjetas());
        usuario.setUbicacionID(usuarioDto.getUbicacionID());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        log.info("Usuario registrado correctamente: {}", usuarioGuardado.getEmail());
        log.debug("Usario: {}", usuarioGuardado);

        return usuarioGuardado;
    }

    // ==================================
    // READ
    // ==================================

    // OBTENER TODOS LOS USUARIOS
    public List<Usuario> obtenerUsuarios() {

        return usuarioRepository.findAll();
    }

    // OBTENER USUARIO BY ID
    public Usuario obtenerUsuarioById(Long id) {

        return usuarioRepository.findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "No existe un usuario con el ID: " + id
                )
            );
    }

    // OBTENER USUARIO BY EMAIL
    public Usuario obtenerUsuarioByEmail(String email) {

        return usuarioRepository.findByEmail(email)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "No existe un usuario con el email: " + email
                )
            );
    }

    // ==================================
    // UPDATE
    // ==================================

    // ACTUALIZAR USUARIO
    public Usuario actualizarUsuario(Long id, UsuarioDto usuarioDto) {

        Usuario usuario = obtenerUsuarioById(id);

        // Comprobar si el email ya pertenece a otro usuario
        if (!usuario.getEmail().equals(usuarioDto.getEmail())
                && usuarioRepository.existsByEmail(usuarioDto.getEmail())) {

            throw new IllegalArgumentException(
                "El email ya se encuentra registrado"
            );
        }

        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombre(usuarioDto.getNombre());

        // Actualizar contraseña solamente si fue proporcionada
        if (usuarioDto.getPassword() != null
                && !usuarioDto.getPassword().isBlank()) {

            usuario.setPassword(
                passwordEncoder.encode(usuarioDto.getPassword())
            );
        }

        usuario.setUrlFoto(usuarioDto.getUrlFoto());
        usuario.setPreferencias(usuarioDto.getPreferencias());
        usuario.setTarjetas(usuarioDto.getTarjetas());
        usuario.setUbicacionID(usuarioDto.getUbicacionID());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        log.info("Usuario actualizado: {}", usuarioActualizado.getEmail());

        return usuarioActualizado;
    }

    // ==================================
    // DELETE
    // ==================================

    // ELIMINAR USUARIO BY ID
    public void eliminarUsuario(Long id) {

        Usuario usuario = obtenerUsuarioById(id);

        usuarioRepository.delete(usuario);

        log.info("Usuario eliminado: {}", usuario.getEmail());
    }

    // ==================================
    // MÉTODOS DE AUTH
    // ==================================

    // LOGIN
    public Usuario login(String email, String password) {

        // Buscar usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Email o contraseña incorrectos"
                )
            );

        // Comprobar contraseña
        if (!passwordEncoder.matches(password, usuario.getPassword())) {

            throw new IllegalArgumentException(
                "Email o contraseña incorrectos"
            );
        }

        log.info("Inicio de sesión exitoso: {}", usuario.getEmail());

        return usuario;
    }
}
