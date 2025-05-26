package com.CRUD.Biblioteca.Service;

import com.CRUD.Biblioteca.Repository.GeneroLiterarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeneroLiterarioService  {

    @Autowired
    private GeneroLiterarioRepository generoLiterarioRepository;

    public List<Map<String, Object>> obtenerGenerosOrdenadosPorLibros() {
        return generoLiterarioRepository.findGenerosConCantidadLibrosNative()
                .stream()
                .map(result -> {
                    Map<String, Object> generoMap = new HashMap<>();
                    generoMap.put("cantidadLibros", result[0]); // COUNT(*)
                    generoMap.put("id", result[1]);             // gl.id
                    generoMap.put("nombre", result[2]);         // gl.nombre
                    generoMap.put("portada", result[3]);        // gl.portada
                    return generoMap;
                })
                .collect(Collectors.toList());
    }

}
