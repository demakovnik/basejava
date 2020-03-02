package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    protected Resume getResumeByPointer(Integer pointer) {
        return storage[pointer];
    }

    @Override
    protected void updateByPointer(Integer pointer, Resume resume) {
        storage[pointer] = resume;
    }

    @Override
    protected boolean isExistPointer(Integer pointer) {
        return pointer >= 0;
    }

    @Override
    protected void insertIntoStorage(Resume resume, Integer pointer) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Хранилище заполнено.", resume.getUuid());
        }
        insertIntoArrayStorage(resume, pointer);
        size++;
    }

    @Override
    protected void deleteElementByPointer(Integer pointer) {
        deleteElementByPointerFromArrayStorage(pointer);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> getList() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    protected abstract void deleteElementByPointerFromArrayStorage(Integer pointer);

    protected abstract void insertIntoArrayStorage(Resume resume, Integer pointer);
}
