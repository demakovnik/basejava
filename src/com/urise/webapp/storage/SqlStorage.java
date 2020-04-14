package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper();
    }

    @Override
    public void clear() {
        sqlHelper.runCommand("DELETE FROM resumes");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resumes r " +
                    "LEFT JOIN contacts c ON r.uuid=c.resume_uuid " +
                    "WHERE uuid=?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
                do {
                    insertContactIntoResume(resultSet, resume);
                } while (resultSet.next());
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sections WHERE resume_uuid=?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    insertSectionIntoResume(resultSet, resume);
                }
            }
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
            }
            deleteContacts(connection, resume.getUuid());
            deleteSections(connection, resume.getUuid());
            putContacts(connection, resume);
            putSections(connection, resume);
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
        sqlHelper.transactionalExecute(connection -> {
                    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT into resumes (uuid, full_name) VALUES (?,?)")) {
                        preparedStatement.setString(1, resume.getUuid());
                        preparedStatement.setString(2, resume.getFullName());
                        preparedStatement.execute();
                    }
                    putContacts(connection, resume);
                    putSections(connection, resume);
                    return null;
                }
        );
    }

    private void deleteContacts(Connection connection, String uuid) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM contacts WHERE resume_uuid=?")) {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
        }
    }

    private void deleteSections(Connection connection, String uuid) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM sections WHERE resume_uuid=?")) {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
        }
    }

    private void putContacts(Connection connection, Resume resume) throws SQLException {
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

    private void putSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sections (resume_uuid,type,value ) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> c : resume.getSections().entrySet()) {
                String value = "";
                switch (c.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        value = ((PersonalOrObjectiveSection) c.getValue()).getText();
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        value = String.join("\n", ((AchievementOrQualificationsSection) c.getValue())
                                .getListOfAchievementsOrQualifications());
                        break;
                }
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, c.getKey().name());
                preparedStatement.setString(3, value);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            HashMap<String, Resume> resumeMap = new LinkedHashMap();

            insertItemToResume(connection, "SELECT * FROM resumes ORDER BY (full_name,uuid)", rs -> {
                String uuid = rs.getString("uuid");
                resumeMap.putIfAbsent(uuid, new Resume(uuid, rs.getString("full_name")));
            });

            insertItemToResume(connection, "SELECT * FROM contacts", rs -> {
                insertContactIntoResume(rs, resumeMap.get(rs.getString("resume_uuid")));
            });

            insertItemToResume(connection, "SELECT * FROM sections", rs ->
                    insertSectionIntoResume(rs, resumeMap.get(rs.getString("resume_uuid"))));
            return new ArrayList<>(resumeMap.values());
        });
    }

    private void insertContactIntoResume(ResultSet resultSet, Resume resume) throws SQLException {
        String nameOfType = resultSet.getString("type");
        if (nameOfType != null) {
            ContactType contactType = ContactType.valueOf(nameOfType);
            String value = resultSet.getString("value");
            resume.putContact(contactType, value);
        }
    }

    private void insertSectionIntoResume(ResultSet resultSet, Resume resume) throws SQLException {
        String nameOfType = resultSet.getString("type");
        if (nameOfType != null) {
            SectionType sectionType = SectionType.valueOf(nameOfType);
            String value = resultSet.getString("value");
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.putSection(sectionType, new PersonalOrObjectiveSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> stringList = Arrays.asList(value.split("\n"));
                    resume.putSection(sectionType, new AchievementOrQualificationsSection(stringList));
                    break;
            }
        }
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

    private void insertItemToResume(Connection connection, String sqlCommand, ResumeDataInserter resumeDataInserter) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resumeDataInserter.insertToResume(resultSet);
            }
        }
    }

    @FunctionalInterface
    private interface ResumeDataInserter {
        void insertToResume(ResultSet rs) throws SQLException;
    }
}