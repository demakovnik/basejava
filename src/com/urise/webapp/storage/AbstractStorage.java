package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractStorage<SK> implements Storage {

    protected static final Comparator<Resume> COMPARATOR = Comparator.comparing(Resume::getFullName).
            thenComparing(Resume::getUuid);

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

    private SK getPointerIfExistElement(String uuid) {
        SK pointer = getPointerToResume(uuid);
        if (isExistPointer(pointer)) {
            return pointer;
        }
        throw new NotExistStorageException(uuid);
    }

    private SK getPointerIfNotExistElement(String uuid) {
        SK pointer = getPointerToResume(uuid);
        if (isExistPointer(pointer)) {
            throw new ExistStorageException(uuid);
        }
        return pointer;
    }

    @Override
    public List<Resume> getAllSorted() {
        return getList().stream().sorted(COMPARATOR).collect(Collectors.toList());
    }

    protected abstract List<Resume> getList();

    protected abstract Resume getResumeByPointer(SK pointer);

    protected abstract void deleteElementByPointer(SK pointer);

    protected abstract boolean isExistPointer(SK pointer);

    protected abstract SK getPointerToResume(String uuid);

    protected abstract void insertIntoStorage(Resume resume, SK pointer);

    protected abstract void updateByPointer(SK pointer, Resume resume);
}
