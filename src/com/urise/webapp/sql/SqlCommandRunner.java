package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlCommandRunner<T> {
    T run(PreparedStatement preparedStatement) throws SQLException;
}
