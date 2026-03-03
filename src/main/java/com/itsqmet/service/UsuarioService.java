package com.itsqmet.service;

import com.itsqmet.component.JwtUtil;
import com.itsqmet.entity.Usuario;
import com.itsqmet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // LISTAR
    public java.util.List<Usuario> mostrarUsuarios() {
        return usuarioRepository.findAll();
    }

    // GUARDAR
    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    // BUSCAR
    public Optional<Usuario> buscarUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    // ACTUALIZAR
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = buscarUsuarioById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setRol(usuario.getRol());

        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    // ELIMINAR
    public void eliminarUsuario(Long id) {
        Usuario usuarioEliminar = buscarUsuarioById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        usuarioRepository.delete(usuarioEliminar);
    }

    // LOGIN (devuelve usuario con token "temporal" en un campo opcional)
    public Usuario validarLogin(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        boolean ok = passwordEncoder.matches(password, usuario.getPassword());
        if (!ok) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // Generar token
        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().name());

        // OJO: si NO quieres agregar campo token en Usuario, dímelo y lo hacemos como Map.
        usuario.setToken(token);

        return usuario;
    }

    // requerido por interfaz, pero en este proyecto no lo usamos (mantener simple)
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        throw new RuntimeException("No usado en este proyecto");
    }
}

