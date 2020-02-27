package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getPointerToResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteElementByPointerFromArrayStorage(Object pointer) {
        storage[(Integer) pointer] = storage[size - 1];
    }

    @Override
    protected void insertIntoArrayStorage(Resume resume, Object pointer) {
        storage[size] = resume;
    }
}

