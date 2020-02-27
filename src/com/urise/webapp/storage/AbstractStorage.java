package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected int size;

    protected Object getPointerIfExistElement(String uuid) {
        Object pointer = getPointerToResume(uuid);
        if (isExistPointer(pointer)) {
            return pointer;
        }
        throw new NotExistStorageException(uuid);
    }

    protected Object getPointerIfNotExistElement(String uuid) {
        Object pointer = getPointerToResume(uuid);
        if (isExistPointer(pointer)) {
            throw new ExistStorageException(uuid);
        }
        return pointer;
    }

    @Override
    public void update(Resume resume) {
        Object pointer = getPointerIfExistElement(resume.getUuid());
        updateByPointer(pointer, resume);
    }

    @Override
    public void save(Resume resume) {
        Object pointer = getPointerIfNotExistElement(resume.getUuid());
        insertIntoStorage(resume, pointer);
    }

    @Override
    public void delete(String uuid) {
        Object pointer = getPointerIfExistElement(uuid);
        deleteElementByPointer(pointer);
    }

    @Override
    public Resume get(String uuid) {
        Object pointer = getPointerIfExistElement(uuid);
        return getResumeByPointer(pointer);
    }

    protected abstract Resume getResumeByPointer(Object pointer);

    protected abstract void deleteElementByPointer(Object pointer);

    protected abstract boolean isExistPointer(Object pointer);

    protected abstract Object getPointerToResume(String uuid);

    protected abstract void insertIntoStorage(Resume resume, Object pointer);

    protected abstract void updateByPointer(Object pointer, Resume resume);
}
