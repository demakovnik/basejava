package com.urise.webapp.sql;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper () {
        connectionFactory = () -> DriverManager.getConnection(Config.getInstance().getDbUrl(),
                Config.getInstance().getDbUser(), Config.getInstance().getDbPassword());
    }

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void runCommand(String command, SqlCommandRunner runner) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(command)) {
            runner.run(statement);
        } catch (SQLException e) {
            if (e instanceof PSQLException) {
                throw new ExistStorageException(e.getMessage());
            }
            throw new StorageException(e);
        }
    }
}