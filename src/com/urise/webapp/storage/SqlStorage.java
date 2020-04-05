package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper = new SqlHelper();

    @Override
    public void clear() {

        sqlHelper.runCommand("DELETE FROM resumes");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.runCommand("SELECT * FROM resumes r " +
                        "LEFT JOIN contacts c ON r.uuid=c.resume_uuid " +
                        "WHERE uuid=?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));

                    do {
                        String nameOfType = resultSet.getString("type");
                        if (nameOfType != null) {
                            ContactType contactType = ContactType.valueOf(nameOfType);
                            String value = resultSet.getString("value");
                            resume.putContact(contactType, value);
                        }
                    } while (resultSet.next());
                    return resume;
                });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resumes SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                deleteContacts(connection,resume.getUuid());
                putContacts(connection,resume);
            }
            return null;
        });
    }

        @Override
        public void delete (String uuid){
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
        public void save (Resume resume){
            sqlHelper.transactionalExecute(connection -> {
                        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT into resumes (uuid, full_name) VALUES (?,?)")) {
                            preparedStatement.setString(1, resume.getUuid());
                            preparedStatement.setString(2, resume.getFullName());
                            preparedStatement.execute();
                            putContacts(connection,resume);
                        }
                        return null;
                    }
            );
        }

        private void deleteContacts (Connection connection, String uuid) throws SQLException {
            try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM  contacts WHERE resume_uuid=?")) {
                preparedStatement.setString(1, uuid);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
        }

        private void putContacts (Connection connection, Resume resume) throws SQLException {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contacts (resume_uuid,type,value ) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> c : resume.getContacts().entrySet()) {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, c.getKey().name());
                    preparedStatement.setString(3, c.getValue());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        }

        @Override
        public List<Resume> getAllSorted () {
            return sqlHelper.runCommand("SELECT * FROM resumes r " +
                    "LEFT JOIN contacts c " +
                    "ON r.uuid = c.resume_uuid  " +
                    "ORDER BY full_name, uuid", ps -> {
                HashMap<String,Resume> resumeMap = new LinkedHashMap();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    if (!resumeMap.containsKey(uuid)) {
                        String fullName = rs.getString("full_name");
                        resumeMap.put(uuid, new Resume(uuid, fullName));
                    }
                    String nameOfType = rs.getString("type");
                    if (nameOfType != null) {
                        ContactType contactType = ContactType.valueOf(nameOfType);
                        String value = rs.getString("value");
                        resumeMap.get(uuid).putContact(contactType, value);
                    }
                }
                return resumeMap.values().stream().collect(Collectors.toList());
            });
        }

        @Override
        public int size () {
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