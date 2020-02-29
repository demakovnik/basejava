package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

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
    protected Resume getResumeByPointer(Object pointer) {
        return storage[(Integer) pointer];
    }

    @Override
    protected void updateByPointer(Object pointer, Resume resume) {
        storage[(Integer) pointer] = resume;
    }

    @Override
    protected boolean isExistPointer(Object pointer) {
        return pointer != null && (Integer) pointer >= 0;
    }

    @Override
    protected void insertIntoStorage(Resume resume, Object pointer) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Хранилище заполнено.", resume.getUuid());
        }
        insertIntoArrayStorage(resume, pointer);
        size++;
    }

    @Override
    protected void deleteElementByPointer(Object pointer) {
        deleteElementByPointerFromArrayStorage(pointer);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void sortTheList(List<Resume> list) {
        Collections.addAll(list, Arrays.copyOfRange(storage, 0, size));
        Collections.sort(list, AbstractStorage.COMPARATOR);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(size);
    }

    protected abstract void deleteElementByPointerFromArrayStorage(Object pointer);

    protected abstract void insertIntoArrayStorage(Resume resume, Object pointer);


}
