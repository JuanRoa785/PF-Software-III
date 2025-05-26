package com.CRUD.Biblioteca.Service;

import com.CRUD.Biblioteca.Model.DetalleVenta;
import com.CRUD.Biblioteca.Repository.DetalleVentaRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DetalleVentaService implements DetalleVentaRepository {

    private DetalleVentaRepository detalleVentaRespository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends DetalleVenta> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends DetalleVenta> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<DetalleVenta> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public DetalleVenta getOne(Integer integer) {
        return null;
    }

    @Override
    public DetalleVenta getById(Integer integer) {
        return null;
    }

    @Override
    public DetalleVenta getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends DetalleVenta> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends DetalleVenta> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends DetalleVenta> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends DetalleVenta> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends DetalleVenta> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends DetalleVenta> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends DetalleVenta, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends DetalleVenta> S save(S entity) {
        return detalleVentaRespository.save(entity);
    }

    @Override
    public <S extends DetalleVenta> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<DetalleVenta> findById(Integer integer) {
        return detalleVentaRespository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<DetalleVenta> findAll() {
        return List.of();
    }

    @Override
    public List<DetalleVenta> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        detalleVentaRespository.deleteById(integer);
    }

    @Override
    public void delete(DetalleVenta entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends DetalleVenta> entities) {

    }

    @Override
    public void deleteAll() {}

    @Override
    public List<DetalleVenta> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<DetalleVenta> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<DetalleVenta> findByVentaId(Integer ventaId) {
        return detalleVentaRespository.findByVentaId(ventaId);
    }

    @Override
    public List<DetalleVenta> findByProductoId(Integer idLibro) {
        return detalleVentaRespository.findByProductoId(idLibro);
    }

    @Override
    public List<DetalleVenta> findByUsuarioId(Integer usuarioId) {
        return detalleVentaRespository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<DetalleVenta> findByFechaBetween(Date fechaInicio, Date fechaFin) {
        return detalleVentaRespository.findByFechaBetween(fechaInicio, fechaFin);
    }
}
