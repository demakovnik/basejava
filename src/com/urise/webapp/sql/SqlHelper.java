package com.urise.webapp.sql;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper() {
        connectionFactory = () -> DriverManager.getConnection(Config.getInstance().getDbUrl(),
                Config.getInstance().getDbUser(), Config.getInstance().getDbPassword());
    }

    public void runCommand(String command) {
        runCommand(command, PreparedStatement::execute);
    }


    public <T> T runCommand(String command, SqlCommandRunner<T> runner) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(command)) {
            return runner.run(statement);
        } catch (SQLException e) {
            throw getDesiredException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false); // устанавливаем false если хотим провести больше одной операции в одной транзакции
                T res = executor.runCommand(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback(); //откат транзакции назад
                throw getDesiredException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private RuntimeException getDesiredException(SQLException exception) throws RuntimeException {
        if (exception.getSQLState().equals("23505")) {
            return new ExistStorageException(exception);
        }
        return new StorageException(exception);
    }
}
