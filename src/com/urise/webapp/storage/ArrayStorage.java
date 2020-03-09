package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

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
    protected void deleteElementByPointerFromArrayStorage(Integer pointer) {
        storage[pointer] = storage[size - 1];
    }

    @Override
    protected void insertIntoArrayStorage(Resume resume, Integer pointer) {
        storage[size] = resume;
    }
}

