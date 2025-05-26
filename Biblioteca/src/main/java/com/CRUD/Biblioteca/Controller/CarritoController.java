package com.CRUD.Biblioteca.Controller;

import com.CRUD.Biblioteca.DTO.CarritoDTO;
import com.CRUD.Biblioteca.DTO.DetalleCarritoDTO;
import com.CRUD.Biblioteca.Model.Carrito;
import com.CRUD.Biblioteca.Repository.CarritoRepository;
import com.CRUD.Biblioteca.Repository.UsuarioRepository;
import com.CRUD.Biblioteca.Service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @GetMapping("/listByEmail")
    public ResponseEntity<CarritoDTO> obtenerCarrito(@RequestParam("email") String correoUsuario) {
        Carrito carrito = carritoService.obtenerCarritoPorCorreo(correoUsuario);

        List<DetalleCarritoDTO> detallesDTO = carritoService.convertirDTO(carrito);

        CarritoDTO carritoDTO = new CarritoDTO(
                carrito.getId(),
                carrito.getTotal(),
                detallesDTO
        );

        return ResponseEntity.ok(carritoDTO);
    }


    @DeleteMapping("/usuario/vaciar/{correoUsuario}")
    public ResponseEntity<CarritoDTO> vaciarCarrito(@PathVariable String correoUsuario) {
        Carrito carrito = carritoService.vaciarCarrito(correoUsuario);

        List<DetalleCarritoDTO> detallesDTO = carritoService.convertirDTO(carrito);

        CarritoDTO carritoDTO = new CarritoDTO(
                carrito.getId(),
                carrito.getTotal(),
                detallesDTO
        );

        return ResponseEntity.ok(carritoDTO);
    }
}
