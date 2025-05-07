package com.pillowsuite.shared.model.repository;

import com.pillowsuite.shared.infrastructure.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface Repository<T>{

    void save(T t) throws SQLException;
    void bulkSave(List<T> tList) throws SQLException;
    private void buildInsertStatement(T t) throws SQLException{};

}
