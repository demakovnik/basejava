package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


/**
 * Array based storage for Resumes
 */

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertIntoStorage(Resume resume, Object pointer) {
        int index = -((Integer) pointer) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
        size++;
    }

    @Override
    protected void deleteElementByPointer(Object pointer) {
        int index = (Integer) pointer;
        int shiftIndex = size - index - 1;
        if (shiftIndex > 0) {
            System.arraycopy(storage, index + 1, storage, index, shiftIndex);
        }
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Object getPointerToResume(String uuid) {
        Resume searchKey = new Resume(uuid);
        return new Integer(Arrays.binarySearch(storage, 0, size, searchKey));
    }

    @Override
    public void showStorageInfo() {
        System.out.println("Хранилище на основе отсортированного массива");
    }
}

