package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    void clear();

    Resume get(String uuid);

    void update(Resume resume);

    void delete(String Uuid);

    void save(Resume resume);


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();

    int size();

    void showStorageInfo();
}
