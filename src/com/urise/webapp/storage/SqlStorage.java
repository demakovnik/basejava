package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper = new SqlHelper();

    @Override
    public void clear() {
        sqlHelper.runCommand("DELETE FROM resumes", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        AtomicReference<Resume> resume = new AtomicReference<>();
        sqlHelper.runCommand("SELECT * FROM resumes WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            resume.set(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
        });
        return resume.get();
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.runCommand("UPDATE resumes SET uuid = ?, full_name = ? WHERE uuid = ?",
                preparedStatement -> {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, resume.getFullName());
                    preparedStatement.setString(3, resume.getUuid());
                    int result = preparedStatement.executeUpdate();
                    if (result == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }
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
                });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.runCommand("INSERT into resumes (uuid, full_name) VALUES (?,?)",
                preparedStatement -> {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, resume.getFullName());
                    preparedStatement.execute();
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();
        sqlHelper.runCommand("SELECT * FROM resumes",
                preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        result.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
                    }
                });
        return result.stream().sorted(Comparator.comparing(Resume::getFullName)
                .thenComparing(Resume::getFullName)).collect(Collectors.toList());
    }

    @Override
    public int size() {
        AtomicInteger count = new AtomicInteger();
        sqlHelper.runCommand("SELECT FROM resumes",
                preparedStatement -> {
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        count.getAndIncrement();
                    }
                });
        return count.get();
    }
}
