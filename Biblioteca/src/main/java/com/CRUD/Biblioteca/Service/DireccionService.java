package com.CRUD.Biblioteca.Service;

import com.CRUD.Biblioteca.Model.Direccion;
import com.CRUD.Biblioteca.Repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DireccionService implements DireccionRepository {

    @Autowired
    private DireccionRepository direccionRepository;

    @Override
    public List<Direccion> findAllByUsuarioId(Integer id_usuario) {
        return direccionRepository.findAllByUsuarioId(id_usuario);
    }

    @Override
    public Direccion findByUsuarioId(Integer id_usuario) {
        return direccionRepository.findByUsuarioId(id_usuario);
    }

    @Override
    public void flush() {
    }

    @Override
    public <S extends Direccion> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Direccion> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Direccion> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Direccion getOne(Integer integer) {
        return null;
    }

    @Override
    public Direccion getById(Integer integer) {
        return Objects.requireNonNull(direccionRepository.findById(integer).orElse(null));
    }

    @Override
    public Direccion getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Direccion> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Direccion> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Direccion> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Direccion> findAll() {
        return direccionRepository.findAll();
    }

    @Override
    public List<Direccion> findAllById(Iterable<Integer> integers) {
        return direccionRepository.findAllById(integers);
    }

    @Override
    public <S extends Direccion> S save(S entity) {
        return direccionRepository.save(entity);
    }

    @Override
    public Optional<Direccion> findById(Integer integer) {
        return direccionRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return direccionRepository.existsById(integer);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        direccionRepository.deleteById(integer);
    }

    @Override
    public void delete(Direccion entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Direccion> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Direccion> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Direccion> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Direccion> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Direccion> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Direccion> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Direccion> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Direccion, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

}
