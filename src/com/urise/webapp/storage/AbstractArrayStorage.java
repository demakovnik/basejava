package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;


    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        int indexOfResume = getIndexOfResume(resume.getUuid());
        if (indexOfResume >= 0) {
            storage[indexOfResume] = resume;
            return;
        }
        throw new NotExistStorageException(resume.getUuid());
    }

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Хранилище резюме заполнено", resume.getUuid());
        }
        int indexOfResume = getIndexOfResume(resume.getUuid());
        if (indexOfResume >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        insertIntoArray(resume, indexOfResume);
        size++;
    }

    public void delete(String uuid) {
        int indexOfResume = getIndexOfResume(uuid);
        if (indexOfResume >= 0) {
            baseDeleteElement(indexOfResume);
            storage[size - 1] = null;
            size--;
            return;
        }
        throw new NotExistStorageException(uuid);
    }

    public Resume get(String uuid) {
        int indexOfResume = getIndexOfResume(uuid);
        if (indexOfResume >= 0) {
            return storage[indexOfResume];
        }
        throw new NotExistStorageException(uuid);
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected abstract int getIndexOfResume(String uuid);

    protected abstract void baseDeleteElement(int index);

    protected abstract void insertIntoArray(Resume resume, int index);

}
