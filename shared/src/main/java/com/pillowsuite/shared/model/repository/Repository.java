package com.pillowsuite.shared.model.repository;

import com.pillowsuite.shared.util.PropertiesLoader;

import java.sql.SQLException;
import java.util.List;



public interface Repository<T>{

    PropertiesLoader values = new PropertiesLoader("values.properties");

    void save(T t) throws SQLException;
    default void bulkSave(List<T> tList) throws SQLException{};

    default void buildInsertStatement(T t) throws SQLException{};

}
