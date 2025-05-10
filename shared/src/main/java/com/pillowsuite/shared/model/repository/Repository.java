package com.pillowsuite.shared.model.repository;

import com.pillowsuite.shared.infrastructure.DatabaseConnection;
import com.pillowsuite.shared.model.dto.Mover;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface Repository<T>{

    void save(T t) throws SQLException;
    default void bulkSave(List<T> tList) throws SQLException{};

    default void buildInsertStatement(T t) throws SQLException{};

}
