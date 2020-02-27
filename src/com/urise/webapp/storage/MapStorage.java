package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getResumeByPointer(Object pointer) {
        return storage.get(pointer);
    }

    @Override
    protected void deleteElementByPointer(Object pointer) {
        storage.remove(pointer);
    }

    @Override
    protected boolean isExistPointer(Object pointer) {
        return storage.containsKey(pointer);
    }

    @Override
    protected String getPointerToResume(String uuid) {
        return uuid;
    }

    @Override
    protected void insertIntoStorage(Resume resume, Object pointer) {
        storage.put((String) pointer, resume);
    }

    @Override
    protected void updateByPointer(Object pointer, Resume resume) {
        insertIntoStorage(resume, pointer);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        ArrayList<Resume> list = new ArrayList<>(storage.values());
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
