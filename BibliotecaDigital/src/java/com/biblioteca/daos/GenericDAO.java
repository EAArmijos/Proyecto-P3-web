package com.biblioteca.daos;

import java.util.List;

public interface GenericDAO<T> {
    List<T> listar();
    T obtenerPorId(int id);
    boolean insertar(T objeto);
    boolean actualizar(T objeto);
    boolean eliminar(int id);
}