package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected int size;

    @Override
    public void update(Resume resume) {
        Object pointer = getPointerToResume(resume.getUuid());
        if (isExistPointer(pointer)) {
            updateByPointer(pointer, resume);
            return;
        }
        throw new NotExistStorageException(resume.getUuid());
    }

    @Override
    public void save(Resume resume) {
        Object pointer = getPointerToResume(resume.getUuid());
        if (isExistPointer(pointer)) {
            throw new ExistStorageException(resume.getUuid());
        }
        insertIntoStorage(resume, pointer);
    }

    @Override
    public void delete(String uuid) {
        Object pointer = getPointerToResume(uuid);
        if (isExistPointer(pointer)) {
            deleteElementByPointer(pointer);
            return;
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume get(String uuid) {
        Object pointer = getPointerToResume(uuid);
        if (isExistPointer(pointer)) {
            return getResumeByPointer(pointer);
        }
        throw new NotExistStorageException(uuid);
    }

    protected abstract Resume getResumeByPointer(Object pointer);

    protected abstract void deleteElementByPointer(Object pointer);

    protected abstract boolean isExistPointer(Object pointer);

    protected abstract Object getPointerToResume(String uuid);

    protected abstract void insertIntoStorage(Resume resume, Object pointer);

    protected abstract void updateByPointer(Object pointer, Resume resume);
}
