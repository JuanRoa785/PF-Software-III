package com.CRUD.Biblioteca.Controller;

import com.CRUD.Biblioteca.DTO.DetalleCarritoAgregarDTO;
import com.CRUD.Biblioteca.DTO.DetalleCarritoDTO;
import com.CRUD.Biblioteca.Model.DetalleCarrito;
import com.CRUD.Biblioteca.Service.DetalleCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/detalle-carrito")
public class DetalleCarritoController {

    @Autowired
    private DetalleCarritoService detalleCarritoService;

    @PostMapping("/agregar")
    public ResponseEntity<DetalleCarritoDTO> agregarDetalleCarrito(
            @RequestBody DetalleCarritoAgregarDTO dto
    ) {
        DetalleCarrito detalleCarrito = detalleCarritoService.agregarDetalleCarrito(dto);

        DetalleCarritoDTO responseDTO = new DetalleCarritoDTO(
                detalleCarrito.getId(),
                detalleCarrito.getCarrito().getId(),
                detalleCarrito.getLibro(),
                detalleCarrito.getCantidad()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<DetalleCarritoDTO> actualizarDetalleCarrito(
            @RequestParam Integer idDetalleCarrito,
            @RequestParam Integer cantidad
    ) {
        DetalleCarrito detalleCarrito = detalleCarritoService.actualizarDetalleCarrito(idDetalleCarrito, cantidad);

        DetalleCarritoDTO responseDTO = new DetalleCarritoDTO(
                detalleCarrito.getId(),
                detalleCarrito.getCarrito().getId(),
                detalleCarrito.getLibro(),
                detalleCarrito.getCantidad()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/eliminar/{idDetalleCarrito}")
    public ResponseEntity<Void> eliminarDetalleCarrito(@PathVariable Integer idDetalleCarrito) {
        detalleCarritoService.eliminarDetalleCarrito(idDetalleCarrito);
        return ResponseEntity.noContent().build();
    }


}
