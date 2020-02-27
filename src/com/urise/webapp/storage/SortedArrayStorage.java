package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;


/**
 * Array based storage for Resumes
 */

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deleteElementByPointerFromArrayStorage(Object pointer) {
        int index = (Integer) pointer;
        int shiftIndex = size - index - 1;
        if (shiftIndex > 0) {
            System.arraycopy(storage, index + 1, storage, index, shiftIndex);
        }
    }

    @Override
    protected void insertIntoArrayStorage(Resume resume, Object pointer) {
        int index = -((Integer) pointer) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected Object getPointerToResume(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

