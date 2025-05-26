package com.CRUD.Biblioteca.Service;

import com.CRUD.Biblioteca.DTO.DetalleCarritoDTO;
import com.CRUD.Biblioteca.Exception.ResourceNotFoundException;
import com.CRUD.Biblioteca.Model.Carrito;
import com.CRUD.Biblioteca.Repository.CarritoRepository;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.stream.Collectors;

@Service
public class CarritoService implements CarritoRepository {

    @Autowired
    private CarritoRepository carritoRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends Carrito> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Carrito> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Carrito> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Carrito getOne(Integer integer) {
        return null;
    }

    @Override
    public Carrito getById(Integer integer) {
        return null;
    }

    @Override
    public Carrito getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Carrito> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Carrito> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Carrito> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Carrito> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Carrito> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Carrito> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Carrito, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Carrito> S save(S entity) {
        return carritoRepository.save(entity);
    }

    @Override
    public <S extends Carrito> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Carrito> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Carrito> findAll() {
        return List.of();
    }

    @Override
    public List<Carrito> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Carrito entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Carrito> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Carrito> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Carrito> findAll(Pageable pageable) {
        return null;
    }

    public Carrito obtenerCarritoPorCorreo(String correoUsuario) {
        return carritoRepository.findByCorreoUsuario(correoUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado para el usuario con correo: " + correoUsuario));
    }

    public Carrito vaciarCarrito(String correoUsuario) {
        Carrito carrito = obtenerCarritoPorCorreo(correoUsuario);
        carrito.getDetalleCarrito().clear();
        carrito.setTotal(0);
        carritoRepository.save(carrito);

        return carritoRepository.findById(carrito.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo recargar el carrito después de vaciarlo"));
    }

    public List<DetalleCarritoDTO> convertirDTO(Carrito carrito){
        List<DetalleCarritoDTO> detallesDTO = carrito.getDetalleCarrito().stream()
                .map(detalle -> new DetalleCarritoDTO(
                        detalle.getId(),
                        detalle.getCarrito().getId(),
                        detalle.getLibro(),
                        detalle.getCantidad()
                ))
                .collect(Collectors.toList());

        return detallesDTO;
    }

    @Override
    public Optional<Carrito> findByUsuarioId(Integer idUsuario) {
        return carritoRepository.findByUsuarioId(idUsuario);
    }

    @Override
    public Optional<Carrito> findByCorreoUsuario(String correoUsuario) {
        return carritoRepository.findByCorreoUsuario(correoUsuario);
    }
}
