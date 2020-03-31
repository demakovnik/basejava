package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlCommandExecutor {
    void execute(PreparedStatement preparedStatement) throws SQLException;
}
