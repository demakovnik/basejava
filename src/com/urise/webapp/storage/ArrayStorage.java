package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertIntoStorage(Resume resume, Object pointer) {
        storage[size] = resume;
    }

    @Override
    protected void deleteElementByPointer(Object pointer) {
        storage[(Integer) pointer] = storage[size - 1];
        storage[size - 1] = null;
    }

    @Override
    protected Object getPointerToResume(String uuid) {
        Integer result = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                result = i;
                return result;
            }
        }
        return result;
    }

    @Override
    public void showStorageInfo() {
        System.out.println("Хранилище на основе массива");
    }
}
