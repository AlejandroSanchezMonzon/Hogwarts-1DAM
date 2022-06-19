package es.dam.repaso05.repositories;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ICRUDRepository<T> {
    public ObservableList<T> findAll() throws SQLException;

    void save(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(T entity) throws SQLException;

    void deleteAll() throws SQLException;
}
