package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    Resume get(String uuid);

    void update(Resume resume);

    void delete(String Uuid);

    void save(Resume resume);

    List<Resume> getAllSorted();

    int size();
}