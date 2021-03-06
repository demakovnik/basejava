package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deleteElementByPointerFromArrayStorage(Integer pointer) {
        int index = pointer;
        int shiftIndex = size - index - 1;
        if (shiftIndex > 0) {
            System.arraycopy(storage, index + 1, storage, index, shiftIndex);
        }
    }

    @Override
    protected void insertIntoArrayStorage(Resume resume, Integer pointer) {
        int index = -pointer - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected Integer getPointerToResume(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid, ""),
                Comparator.comparing(Resume::getUuid));
    }
}

