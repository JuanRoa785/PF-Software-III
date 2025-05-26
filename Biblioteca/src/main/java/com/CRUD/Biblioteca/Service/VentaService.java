package com.CRUD.Biblioteca.Service;

import com.CRUD.Biblioteca.Exception.ResourceNotFoundException;
import com.CRUD.Biblioteca.Model.*;
import com.CRUD.Biblioteca.Repository.DetalleVentaRepository;
import com.CRUD.Biblioteca.Repository.LibroRepository;
import com.CRUD.Biblioteca.Repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class VentaService implements VentaRepository {

    @Autowired
    private VentaRepository ventaRepository;

    private final UserDetailsServiceImpl usuarioService;
    private final CarritoService carritoService;
    private final DetalleVentaRepository detalleVentaRepository;
    private final LibroRepository libroRepository;
    private final DireccionService direccionService;

    public VentaService(LibroRepository libroRepository, DireccionService direccionService, DetalleVentaRepository detalleVentaRepository, CarritoService carritoService, UserDetailsServiceImpl usuarioService, VentaRepository ventaRepository) {

        this.libroRepository = libroRepository;
        this.direccionService = direccionService;
        this.detalleVentaRepository = detalleVentaRepository;
        this.carritoService = carritoService;
        this.usuarioService = usuarioService;
        this.ventaRepository = ventaRepository;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Venta> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Venta> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Venta> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Venta getOne(Integer integer) {
        return null;
    }

    @Override
    public Venta getById(Integer integer) {
        return null;
    }

    @Override
    public Venta getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Venta> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Venta> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Venta> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Venta> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Venta> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Venta> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Venta, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Venta> S save(S entity) {
        return ventaRepository.save(entity);
    }

    @Override
    public <S extends Venta> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Venta> findById(Integer integer) {
        return ventaRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Override
    public List<Venta> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        ventaRepository.deleteById(integer);
    }

    @Override
    public void delete(Venta entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Venta> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Venta> findAll(Sort sort) {
        return ventaRepository.findAll(sort);
    }

    @Override
    public Page<Venta> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Venta> listarTodosFiltroDesc(Integer idLibro) {
        return ventaRepository.listarTodosFiltroDesc(idLibro);
    }

    public List<Venta> listarTodos() {
        return ventaRepository.listarTodosFiltroDesc(null);
    }

    public List<Venta> listarTodosUsuario(String correoUsuario) {

        Usuario usuario = usuarioService.findByCorreo(correoUsuario);

        return ventaRepository.findByUsuarioId(usuario.getId());
    }

    public List<Venta> listarTodosFiltroAsc(Integer idLibro) {
        return ventaRepository.listarTodosFiltroAsc(idLibro);
    }

    public List<Venta> listarTodosFiltro(Integer idLibro, boolean descFecha) {
        if (descFecha) {
            return ventaRepository.listarTodosFiltroDesc(idLibro);
        } else {
            return ventaRepository.listarTodosFiltroAsc(idLibro);
        }
    }

    @Override
    public List<Venta> findByUsuarioId(Integer idUsuario) {
        return ventaRepository.findByUsuarioId(idUsuario);
    }

    @Override
    public List<Venta> findUsuarioAndNombreLibroDesc(Integer idUsuario, String nombreLibro) {
        return ventaRepository.findUsuarioAndNombreLibroDesc(idUsuario, nombreLibro);
    }

    @Override
    public List<Venta> findUsuarioAndNombreLibroAsc(Integer idUsuario, String nombreLibro) {
        return ventaRepository.findUsuarioAndNombreLibroAsc(idUsuario, nombreLibro);
    }

    public List<Venta> listarFiltroUsuario(String correoUsuario, String nombreLibro, boolean descFecha) {
        Usuario usuario = usuarioService.findByCorreo(correoUsuario);

        nombreLibro = nombreLibro.trim().toUpperCase().replaceAll("\\s+", " ");

        if (descFecha) {
            return ventaRepository.findUsuarioAndNombreLibroAsc(usuario.getId(), nombreLibro);
        } else {
            return ventaRepository.findUsuarioAndNombreLibroDesc(usuario.getId(), nombreLibro);
        }
    }

    @Override
    public List<Venta> reporteIndividualDesc(String cliente, Date fechaInferior, Date fechaSuperior) {
        return ventaRepository.reporteIndividualDesc(cliente, fechaInferior, fechaSuperior);
    }

    @Override
    public List<Venta> reporteIndividualAsc(String cliente, Date fechaInferior, Date fechaSuperior) {
        return ventaRepository.reporteIndividualAsc(cliente, fechaInferior, fechaSuperior);
    }

    public List<Venta> reporteIndividual(String cliente, Date fechaInferior, Date fechaSuperior, boolean descFecha) {

        fechaSuperior = new Date(fechaSuperior.getTime() + 24 * 60 * 60 * 1000 - 1);

        cliente = cliente.trim().toUpperCase().replaceAll("\\s+", " ");

        if (descFecha) {
            return ventaRepository.reporteIndividualDesc(cliente, fechaInferior, fechaSuperior);
        } else {
            return ventaRepository.reporteIndividualAsc(cliente, fechaInferior, fechaSuperior);
        }
    }

    public List<Venta> reporte2B() {
        return new ArrayList<>();
    }

    public List<Venta> reporteConjunto(String cliente, Date fechaInferior, Date fechaSuperior, boolean descFecha, List<String> sucursales) {

        List<String> sucursalesValidas = List.of("2A", "2B");

        if(sucursales.isEmpty()){
            sucursales = sucursalesValidas;
        }

        List<Venta> reporte = new ArrayList<>();

        for (String sucursal : sucursales) {
            if(sucursal == null){
                throw new ResourceNotFoundException("The branch not found");
            }
            sucursal = sucursal.trim().toUpperCase();

            if(sucursal.equals("2A")){
                reporte.addAll(reporteIndividual(cliente, fechaInferior, fechaSuperior, descFecha));
            }
            if(sucursal.equals("2B")){
                reporte.addAll(reporte2B());
            }

            if (!sucursalesValidas.contains(sucursal)) {
                throw new ResourceNotFoundException("The branch not found");
            }

        }

        return reporte;
    }

    @Transactional
    public Venta registrar(String correoUsuario, String observaciones) {
        //usuario
        Usuario usuario = usuarioService.findByCorreo(correoUsuario);

        if (observaciones == null) {
            observaciones = "";
        } else {
            observaciones = observaciones.trim().toLowerCase().replaceAll("\\s+", " ");
        }


        //creacion de la venta
        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setFecha(new java.util.Date());
        venta.setObservaciones(observaciones);
        venta.setTotal(0.0);

        //direccion
        Direccion direccion = direccionService.findByUsuarioId(usuario.getId());
        String direccionCompleta = direccion.getPais() + "-" +
                direccion.getRegion() + "-" +
                direccion.getCiudad() + "-" +
                direccion.getCodigo_postal() + "-" +
                direccion.getDireccion();
        venta.setDireccion(direccionCompleta);

        venta = ventaRepository.save(venta);

        //creacion de detalles venta
        venta.setDetalleVenta(crearDetallesVenta(venta, usuario));
        venta = ventaRepository.save(venta);

        //vaciar carrito
        carritoService.vaciarCarrito(usuario.getEmail());

        return venta;
    }


    private List<DetalleVenta> crearDetallesVenta(Venta venta, Usuario usuario) {
        Carrito carrito = carritoService.obtenerCarritoPorCorreo(usuario.getEmail());
        List<DetalleCarrito> detallesCarrito = carrito.getDetalleCarrito();

        if (detallesCarrito.isEmpty()) {
            throw new ResourceNotFoundException("The cart is empty");
        }

        Double total = 0.0;
        List<DetalleVenta> detallesVenta = new ArrayList<>();

        for (DetalleCarrito detalleCarrito : detallesCarrito) {
            Libro libro = detalleCarrito.getLibro();
            int cantidad = detalleCarrito.getCantidad();

            // Verificar stock antes de modificar
            if (libro.getStock() < cantidad) {
                throw new ResourceNotFoundException("Not enough stock for the product: " + libro.getNombre());
            }

            // Crear y guardar DetalleVenta
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setVenta(venta);
            detalleVenta.setLibro(libro);
            detalleVenta.setCantidad(cantidad);
            detalleVentaRepository.save(detalleVenta);

            // Actualizar stock del libro
            libro.setStock(libro.getStock() - cantidad);
            libroRepository.save(libro);

            // Calcular total de la venta
            total += libro.getPrecio() * cantidad * (1 + libro.getImpuesto());

            // Agregar a la lista que se devolverá
            detallesVenta.add(detalleVenta);
        }

        // Actualizar total en la venta
        venta.setTotal(total);

        return detallesVenta;
    }


}
