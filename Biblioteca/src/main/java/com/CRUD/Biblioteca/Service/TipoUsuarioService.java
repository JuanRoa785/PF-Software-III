package com.CRUD.Biblioteca.Service;

import com.CRUD.Biblioteca.Model.TipoUsuario;
import com.CRUD.Biblioteca.Repository.TipoUsuarioRepository;
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
public class TipoUsuarioService implements TipoUsuarioRepository {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends TipoUsuario> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends TipoUsuario> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<TipoUsuario> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TipoUsuario getOne(Integer integer) {
        return null;
    }

    @Override
    public TipoUsuario getById(Integer integer) {
        return null;
    }

    @Override
    public TipoUsuario getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends TipoUsuario> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TipoUsuario> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends TipoUsuario> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends TipoUsuario> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TipoUsuario> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TipoUsuario> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends TipoUsuario, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends TipoUsuario> S save(S entity) {
        return tipoUsuarioRepository.save(entity);
    }

    @Override
    public <S extends TipoUsuario> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<TipoUsuario> findById(Integer integer) {
        return tipoUsuarioRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<TipoUsuario> findAll() {
        return List.of();
    }

    @Override
    public List<TipoUsuario> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        tipoUsuarioRepository.deleteById(integer);
    }

    @Override
    public void delete(TipoUsuario entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends TipoUsuario> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<TipoUsuario> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<TipoUsuario> findAll(Pageable pageable) {
        return null;
    }
}
