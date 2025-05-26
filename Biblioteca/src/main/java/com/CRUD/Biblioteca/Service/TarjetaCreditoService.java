package com.CRUD.Biblioteca.Service;

import com.CRUD.Biblioteca.Model.TarjetaCredito;
import com.CRUD.Biblioteca.Repository.TarjetaCreditoRepository;
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
public class TarjetaCreditoService implements TarjetaCreditoRepository {

    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends TarjetaCredito> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends TarjetaCredito> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<TarjetaCredito> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TarjetaCredito getOne(Integer integer) {
        return null;
    }

    @Override
    public TarjetaCredito getById(Integer integer) {
        return null;
    }

    @Override
    public TarjetaCredito getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends TarjetaCredito> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TarjetaCredito> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends TarjetaCredito> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends TarjetaCredito> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TarjetaCredito> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TarjetaCredito> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends TarjetaCredito, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends TarjetaCredito> S save(S entity) {
        return tarjetaCreditoRepository.save(entity);
    }

    @Override
    public <S extends TarjetaCredito> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<TarjetaCredito> findById(Integer integer) {
        return tarjetaCreditoRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<TarjetaCredito> findAll() {
        return tarjetaCreditoRepository.findAll();
    }

    @Override
    public List<TarjetaCredito> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        tarjetaCreditoRepository.deleteById(integer);
    }

    @Override
    public void delete(TarjetaCredito entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends TarjetaCredito> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<TarjetaCredito> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<TarjetaCredito> findAll(Pageable pageable) {
        return null;
    }
}
