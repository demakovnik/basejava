package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

    @Override
    protected Resume getResumeByPointer(Object pointer) {
        int index = (Integer) pointer;
        return storage.get(index);
    }

    @Override
    protected void deleteElementByPointer(Object pointer) {
        int index = (Integer) pointer;
        storage.remove(index);
        size = storage.size();
    }

    @Override
    protected boolean isExistPointer(Object pointer) {
        return (pointer != null);
    }

    @Override
    protected Object getPointerToResume(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void insertIntoStorage(Resume resume, Object pointer) {
        storage.add(resume);
        size = storage.size();
    }

    @Override
    protected void updateByPointer(Object pointer, Resume resume) {
        int index = (Integer) pointer;
        storage.remove(index);
        storage.add(index, resume);
    }

    @Override
    public void clear() {
        storage.clear();

    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storage.size()];
        storage.toArray(resumes);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
