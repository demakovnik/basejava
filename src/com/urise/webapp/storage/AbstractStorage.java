package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        if (isExistPointer(uuid)) {
            updateByPointer(getPointerToResume(uuid), resume);
            return;
        }
        throw new NotExistStorageException(resume.getUuid());
    }

    @Override
    public void save(Resume resume) {
        if (this.getClass() == AbstractArrayStorage.class && size() == AbstractArrayStorage.STORAGE_LIMIT) {
            throw new StorageException("Хранилище резюме заполнено", resume.getUuid());
        } else {
            String uuid = resume.getUuid();
            if (isExistPointer(uuid)) {
                throw new ExistStorageException(uuid);
            }
            insertIntoAbstractStorage(resume, getPointerToResume(uuid));
        }
    }

    @Override
    public void delete(String uuid) {
        if (isExistPointer(uuid)) {
            deleteElementByPointerFromAbstractStorage(getPointerToResume(uuid));
            return;
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume get(String uuid) {
        if (isExistPointer(uuid)) {
            return getResumeByPointer(getPointerToResume(uuid));
        }
        throw new NotExistStorageException(uuid);
    }

    protected abstract Resume getResumeByPointer(Object pointer);

    protected abstract void deleteElementByPointerFromAbstractStorage(Object pointer);

    protected abstract boolean isExistPointer(String uuid);

    protected abstract Object getPointerToResume(String uuid);

    protected abstract void updateByPointer(Object pointer, Resume resume);

    protected abstract void insertIntoAbstractStorage(Resume resume, Object pointerToResume);
}
