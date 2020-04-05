package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public interface ConnectionFactory {

    Connection getConnection() throws SQLException;

}
