package com.itsqmet.service;

import com.itsqmet.entity.Inventario;
import com.itsqmet.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    // Crear
    public Inventario guardarInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    // Listar
    public List<Inventario> listarInventarios() {
        return inventarioRepository.findAll();
    }

    // Buscar por ID
    public Optional<Inventario> obtenerPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    // Actualizar
    public Inventario actualizarInventario(Long id, Inventario inventario) {
        Inventario inv = inventarioRepository.findById(id).orElse(null);

        if (inv != null) {
            inv.setNombre(inventario.getNombre());
            inv.setDescripcion(inventario.getDescripcion());
            inv.setPrecio(inventario.getPrecio());
            inv.setStock(inventario.getStock());
            return inventarioRepository.save(inv);
        }

        return null;
    }

    // Eliminar
    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }
}

