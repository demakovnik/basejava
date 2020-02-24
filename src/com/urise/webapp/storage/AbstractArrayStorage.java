package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected int size;
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
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
    protected boolean isExistPointer(String uuid) {
        Integer index = (Integer) getPointerToResume(uuid);
        return  index != null && index >= 0;
    }
}
