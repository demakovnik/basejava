package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public void update(Resume resume) {
        updateByPointer(getPointerIfExistElement(resume.getUuid()), resume);
    }

    @Override
    public void save(Resume resume) {
        insertIntoStorage(resume, getPointerIfNotExistElement(resume.getUuid()));
    }

    @Override
    public void delete(String uuid) {
        deleteElementByPointer(getPointerIfExistElement(uuid));
    }

    @Override
    public Resume get(String uuid) {
        return getResumeByPointer(getPointerIfExistElement(uuid));
    }

    private Object getPointerIfExistElement(String uuid) {
        Object pointer = getPointerToResume(uuid);
        if (isExistPointer(pointer)) {
            return pointer;
        }
        throw new NotExistStorageException(uuid);
    }

    private Object getPointerIfNotExistElement(String uuid) {
        Object pointer = getPointerToResume(uuid);
        if (isExistPointer(pointer)) {
            throw new ExistStorageException(uuid);
        }
        return pointer;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        Collections.sort(list, COMPARATOR);
        return list;
    }


    protected abstract List<Resume> getList();

    protected abstract Resume getResumeByPointer(Object pointer);

    protected abstract void deleteElementByPointer(Object pointer);

    protected abstract boolean isExistPointer(Object pointer);

    protected abstract Object getPointerToResume(String uuid);

    protected abstract void insertIntoStorage(Resume resume, Object pointer);

    protected abstract void updateByPointer(Object pointer, Resume resume);
}
