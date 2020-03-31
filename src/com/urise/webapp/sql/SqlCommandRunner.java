package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlCommandRunner {
    void run(PreparedStatement preparedStatement) throws SQLException;
}
