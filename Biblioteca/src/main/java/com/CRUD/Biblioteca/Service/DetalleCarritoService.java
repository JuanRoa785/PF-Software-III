package com.CRUD.Biblioteca.Service;

import com.CRUD.Biblioteca.DTO.DetalleCarritoAgregarDTO;
import com.CRUD.Biblioteca.Exception.ResourceNotFoundException;
import com.CRUD.Biblioteca.Model.Carrito;
import com.CRUD.Biblioteca.Model.DetalleCarrito;
import com.CRUD.Biblioteca.Model.Libro;
import com.CRUD.Biblioteca.Repository.CarritoRepository;
import com.CRUD.Biblioteca.Repository.DetalleCarritoRepository;
import com.CRUD.Biblioteca.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DetalleCarritoService implements DetalleCarritoRepository {

    @Autowired
    private DetalleCarritoRepository detalleCarritoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends DetalleCarrito> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends DetalleCarrito> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<DetalleCarrito> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public DetalleCarrito getOne(Integer integer) {
        return null;
    }

    @Override
    public DetalleCarrito getById(Integer integer) {
        return detalleCarritoRepository.findById(integer).
                orElseThrow(() -> new ResourceNotFoundException("Detail not found"));

    }

    @Override
    public DetalleCarrito getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends DetalleCarrito> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends DetalleCarrito> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends DetalleCarrito> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends DetalleCarrito> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends DetalleCarrito> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends DetalleCarrito> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends DetalleCarrito, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends DetalleCarrito> S save(S entity) {
        return detalleCarritoRepository.save(entity);
    }

    @Override
    public <S extends DetalleCarrito> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<DetalleCarrito> findById(Integer integer) {
        return detalleCarritoRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<DetalleCarrito> findAll() {
        return List.of();
    }

    @Override
    public List<DetalleCarrito> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        detalleCarritoRepository.deleteById(integer);
    }


    @Override
    public void delete(DetalleCarrito entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends DetalleCarrito> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<DetalleCarrito> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<DetalleCarrito> findAll(Pageable pageable) {
        return null;
    }

    public DetalleCarrito agregarDetalleCarrito(DetalleCarritoAgregarDTO dto) {
        double total = 0;
        Carrito carrito = carritoRepository.findById(dto.getIdCarrito())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Libro libro = libroRepository.findById(dto.getIdLibro())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        double subtotal = (libro.getPrecio() + libro.getImpuesto()) * dto.getCantidad();

        // Buscar si ya hay un detalle con ese libro en el carrito
        Optional<DetalleCarrito> existente = carrito.getDetalleCarrito().stream()
                .filter(det -> det.getLibro().getId().equals(libro.getId()))
                .findFirst();

        if (existente.isPresent()) {
            // Si existe, sumar la cantidad
            DetalleCarrito detalle = existente.get();
            detalle.setCantidad(detalle.getCantidad() + dto.getCantidad());
            carrito.setTotal(carrito.getTotal() + subtotal);
            carritoRepository.save(carrito);
            return detalleCarritoRepository.save(detalle);
        } else {
            // Si no existe, crear un nuevo detalle
            DetalleCarrito nuevo = new DetalleCarrito();
            nuevo.setCarrito(carrito);
            nuevo.setLibro(libro);
            nuevo.setCantidad(dto.getCantidad());

            carrito.setTotal(carrito.getTotal() + subtotal);
            carritoRepository.save(carrito);

            return detalleCarritoRepository.save(nuevo);
        }
    }

    // Actualizar la cantidad de un detalle del carrito
    public DetalleCarrito actualizarDetalleCarrito(Integer idDetalleCarrito, Integer cantidad) {
        DetalleCarrito detalle = detalleCarritoRepository.findById(idDetalleCarrito)
                .orElseThrow(() -> new RuntimeException("Detalle del carrito no encontrado"));

        detalle.setCantidad(cantidad);
        return detalleCarritoRepository.save(detalle);
    }

    // Eliminar un detalle del carrito por su ID
    public void eliminarDetalleCarrito(Integer idDetalleCarrito) {
        DetalleCarrito detalle = detalleCarritoRepository.findById(idDetalleCarrito)
                .orElseThrow(() -> new RuntimeException("Detalle del carrito no encontrado"));

        detalleCarritoRepository.delete(detalle);
    }

}
