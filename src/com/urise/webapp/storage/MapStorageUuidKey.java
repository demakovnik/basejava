package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorageUuidKey extends AbstractStorage<String> {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getResumeByPointer(String pointer) {
        return storage.get(pointer);
    }

    @Override
    protected void deleteElementByPointer(String pointer) {
        storage.remove(pointer);
    }

    @Override
    protected boolean isExistPointer(String pointer) {
        return storage.containsKey(pointer);
    }

    @Override
    protected String getPointerToResume(String uuid) {
        return uuid;
    }

    @Override
    protected void insertIntoStorage(Resume resume, String pointer) {
        storage.put(pointer, resume);
    }

    @Override
    protected void updateByPointer(String pointer, Resume resume) {
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