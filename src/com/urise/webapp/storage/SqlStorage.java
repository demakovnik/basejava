package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper = new SqlHelper();

    @Override
    public void clear() {
        sqlHelper.runCommand("DELETE FROM resumes", preparedStatement -> {
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.runCommand("SELECT * FROM resumes WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.runCommand("UPDATE resumes SET full_name = ? WHERE uuid = ?",
                preparedStatement -> {
                    preparedStatement.setString(1, resume.getFullName());
                    preparedStatement.setString(2, resume.getUuid());
                    int result = preparedStatement.executeUpdate();
                    if (result == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }
                    return null;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.runCommand("DELETE FROM resumes WHERE uuid = ?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    int result = preparedStatement.executeUpdate();
                    if (result == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.runCommand("INSERT into resumes (uuid, full_name) VALUES (?,?)",
                preparedStatement -> {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, resume.getFullName());
                    preparedStatement.execute();
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {

        return sqlHelper.runCommand("SELECT * FROM resumes ORDER BY full_name, uuid",
                preparedStatement -> {
                    List<Resume> result = new ArrayList<>();
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        result.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
                    }
                    return result;
                });
    }

    @Override
    public int size() {
        return sqlHelper.runCommand("SELECT count(*) FROM resumes",
                preparedStatement -> {
                    ResultSet rs = preparedStatement.executeQuery();
                    int count = 0;
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                    return count;
                });
    }
}
