package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorageUuidKey extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getResumeByPointer(Object pointer) {
        return storage.get((String) pointer);
    }

    @Override
    protected void deleteElementByPointer(Object pointer) {
        storage.remove((String) pointer);
    }

    @Override
    protected boolean isExistPointer(Object pointer) {
        return storage.containsKey((String) pointer);
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
        storage.remove((String) pointer);
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }
}