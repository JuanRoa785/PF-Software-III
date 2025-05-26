package com.CRUD.Biblioteca.Service;

import com.CRUD.Biblioteca.Model.Libro;
import com.CRUD.Biblioteca.Repository.LibroRepository;
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
public class LibroService implements LibroRepository {

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends Libro> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Libro> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Libro> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Libro getOne(Integer integer) {
        return null;
    }

    @Override
    public Libro getById(Integer integer) {
        return Objects.requireNonNull(libroRepository.findById(integer).orElse(null));
    }

    @Override
    public Libro getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Libro> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Libro> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Libro> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Libro> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Libro> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Libro> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Libro, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Libro> S save(S entity) {
        return libroRepository.save(entity);
    }

    @Override
    public <S extends Libro> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Libro> findById(Integer integer) {
        return libroRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return libroRepository.existsById(integer);
    }

    @Override
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    @Override
    public List<Libro> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        libroRepository.deleteById(integer);
    }

    @Override
    public void delete(Libro entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Libro> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Libro> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Libro> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Libro> Filtro_libros(String nombre, String genero) {
        if (nombre == null) nombre = "";
        if (genero == null) genero = "";
        return libroRepository.Filtro_libros(nombre, genero);
    }

    @Override
    public List<String> findDistinctEditoriales(){
        return libroRepository.findDistinctEditoriales();
    }

    public Libro actualizarLibro(Integer id, Libro libroActualizado) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));

        libro.setNombre(libroActualizado.getNombre());
        libro.setSinopsis(libroActualizado.getSinopsis());
        libro.setPortada(libroActualizado.getPortada());
        libro.setPrecio(libroActualizado.getPrecio());
        libro.setImpuesto(libroActualizado.getImpuesto());
        libro.setStock(libroActualizado.getStock());
        libro.setAutor(libroActualizado.getAutor());
        libro.setEditorial(libroActualizado.getEditorial());
        libro.setEdicion(libroActualizado.getEdicion());
        libro.setFecha_publicacion(libroActualizado.getFecha_publicacion());
        libro.setIdioma(libroActualizado.getIdioma());
        libro.setNumero_paginas(libroActualizado.getNumero_paginas());
        libro.setDimensiones(libroActualizado.getDimensiones());
        libro.setColeccion(libroActualizado.getColeccion());
        libro.setGeneroLiterario(libroActualizado.getGeneroLiterario());

        return libroRepository.save(libro);
    }


}
