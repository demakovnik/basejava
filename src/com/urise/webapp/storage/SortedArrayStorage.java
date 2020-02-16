package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public int getIndexOfResume(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void baseDeleteElement(int index) {
        int shiftIndex = size - index - 1;
        if (shiftIndex > 0) {
            System.arraycopy(storage, index + 1, storage, index, shiftIndex);
        }
    }

    @Override
    protected void insertIntoArray(Resume resume, int index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }
}
