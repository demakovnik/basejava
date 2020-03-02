package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageResumeKey extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getResumeByPointer(Object pointer) {
        return (Resume) pointer;
    }

    @Override
    protected void deleteElementByPointer(Object pointer) {
        storage.remove(((Resume) pointer).getUuid());
    }

    @Override
    protected boolean isExistPointer(Object pointer) {
        return pointer != null;
    }

    @Override
    protected Resume getPointerToResume(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void insertIntoStorage(Resume resume, Object pointer) {

        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateByPointer(Object pointer, Resume resume) {
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