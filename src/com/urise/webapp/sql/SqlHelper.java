package com.urise.webapp.sql;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.*;


public class SqlHelper {
    private final ConnectionFactory connectionFactory;
    private String databaseName;

    public SqlHelper() {
        connectionFactory = () -> {
            Connection connection = DriverManager.getConnection(Config.getInstance().getDbUrl(),
                    Config.getInstance().getDbUser(), Config.getInstance().getDbPassword());
            databaseName = connection.getMetaData().getDatabaseProductName();
            return connection;
        };
    }

    public <T> T runCommand(String command, SqlCommandRunner<T> runner) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(command)) {
            return runner.run(statement);
        } catch (SQLException e) {
            int errorCode = -1;
            switch (databaseName) {
                case "PostgreSQL":
                    errorCode = 0;
                    break;
                case "SQLite":
                    errorCode = 19;
                    break;
            }
            if (e.getErrorCode() == errorCode) {
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }
}