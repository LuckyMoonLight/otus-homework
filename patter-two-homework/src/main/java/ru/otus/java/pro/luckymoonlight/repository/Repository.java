package ru.otus.java.pro.luckymoonlight.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository <T> {
    void save(T t) throws SQLException;
    List<T> findAll() throws SQLException;
}
