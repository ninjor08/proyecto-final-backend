package com.itsqmet.controller;

import com.itsqmet.entity.Inventario;
import com.itsqmet.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @PostMapping
    public Inventario crear(@RequestBody Inventario inventario) {
        return inventarioService.guardarInventario(inventario);
    }

    @GetMapping
    public List<Inventario> listar() {
        return inventarioService.listarInventarios();
    }

    @GetMapping("/{id}")
    public Inventario buscar(@PathVariable Long id) {
        return inventarioService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }

    @PutMapping("/{id}")
    public Inventario actualizar(@PathVariable Long id, @RequestBody Inventario inventario) {
        return inventarioService.actualizarInventario(id, inventario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        inventarioService.eliminarInventario(id);
    }
}

