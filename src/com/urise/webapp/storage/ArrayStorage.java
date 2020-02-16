package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public int getIndexOfResume(String Uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(Uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void baseDeleteElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void insertIntoArray(Resume resume, int index) {
        storage[size] = resume;
    }
}
