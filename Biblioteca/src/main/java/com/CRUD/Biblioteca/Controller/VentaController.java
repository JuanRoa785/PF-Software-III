package com.CRUD.Biblioteca.Controller;

import com.CRUD.Biblioteca.DTO.DetalleCarritoDTO;
import com.CRUD.Biblioteca.DTO.VentaDTO;
import com.CRUD.Biblioteca.Model.Carrito;
import com.CRUD.Biblioteca.Model.Venta;
import com.CRUD.Biblioteca.Service.CarritoService;
import com.CRUD.Biblioteca.Service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/listar/todos")
    public ResponseEntity<List<VentaDTO>> listarTodos() {
        List<Venta> ventas = ventaService.listarTodos();
        List<VentaDTO> ventasDTO = ventas.stream().map(VentaDTO::new).toList();
        return ResponseEntity.ok(ventasDTO);
    }

    @GetMapping("/listar/todos/filtro")
    public ResponseEntity<List<VentaDTO>> listarTodosFiltro(
            @RequestParam(value = "idLibro", required = false) Integer idLibro,
            @RequestParam(value = "descFecha", defaultValue = "true") Boolean descFecha
    ) {
        List<Venta> ventas = ventaService.listarTodosFiltro(idLibro, descFecha);
        List<VentaDTO> ventasDTO = ventas.stream().map(VentaDTO::new).toList();
        return ResponseEntity.ok(ventasDTO);
    }

    @GetMapping("/listar/usuario/{correoUsuario}")
    public ResponseEntity<List<VentaDTO>> listarTodosUsuario(@PathVariable String correoUsuario) {
        List<Venta> ventas = ventaService.listarTodosUsuario(correoUsuario);
        List<VentaDTO> ventasDTO = ventas.stream().map(VentaDTO::new).toList();
        return ResponseEntity.ok(ventasDTO);
    }

    @GetMapping("/listar/filtro/usuario")
    public ResponseEntity<List<VentaDTO>> listarFiltroUsuario(
            @RequestParam String correoUsuario,
            @RequestParam(defaultValue = "") String nombre,
            @RequestParam(defaultValue = "true") Boolean descFecha
    ) {
        List<Venta> ventas = ventaService.listarFiltroUsuario(correoUsuario, nombre, descFecha);
        List<VentaDTO> ventasDTO = ventas.stream().map(VentaDTO::new).toList();
        return ResponseEntity.ok(ventasDTO);
    }

    @PostMapping("/registrar")
    public ResponseEntity<VentaDTO> registrar(
            @RequestParam String correoUsuario,
            @RequestParam String observaciones
    ) {
        Venta venta = ventaService.registrar(correoUsuario, observaciones);
        Carrito carritoVacio = carritoService.vaciarCarrito(correoUsuario);
        List<DetalleCarritoDTO> detallesDTO = carritoService.convertirDTO(carritoVacio);


        return ResponseEntity.ok(new VentaDTO(venta));
    }

    @GetMapping("/reporte-individual")
    public ResponseEntity<List<VentaDTO>> reporteIndividual(
            @RequestParam(defaultValue = "") String cliente,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInferior,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaSuperior,
            @RequestParam(defaultValue = "true") Boolean totalDesc
    ) {
        List<Venta> ventas = ventaService.reporteIndividual(cliente, fechaInferior, fechaSuperior, totalDesc);
        List<VentaDTO> ventasDTO = ventas.stream().map(VentaDTO::new).toList();
        return ResponseEntity.ok(ventasDTO);
    }

    @PostMapping("/reporte-conjunto")
    public ResponseEntity<List<VentaDTO>> reporteConjunto(
            @RequestParam(defaultValue = "") String cliente,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInferior,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaSuperior,
            @RequestParam(defaultValue = "true") Boolean totalDesc,
            @RequestBody List<String> sucursales
    ) {
        List<Venta> ventas = ventaService.reporteConjunto(cliente, fechaInferior, fechaSuperior, totalDesc, sucursales);
        List<VentaDTO> ventasDTO = ventas.stream().map(VentaDTO::new).toList();
        return ResponseEntity.ok(ventasDTO);
    }
}

