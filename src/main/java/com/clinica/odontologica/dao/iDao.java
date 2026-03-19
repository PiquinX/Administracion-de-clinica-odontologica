package com.clinica.odontologica.dao;

import java.util.List;

public interface iDao<T> {
    T guardar(T object);

    T buscar(Integer id);

    void actualizar(T object);

    void eliminar(Integer id);

    List<T> listar();
}
