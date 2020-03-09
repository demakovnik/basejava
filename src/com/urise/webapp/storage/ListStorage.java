package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> storage = new ArrayList<>();

    @Override
    protected Resume getResumeByPointer(Integer pointer) {
        int index = pointer;
        return storage.get(index);
    }

    @Override
    protected void deleteElementByPointer(Integer pointer) {
        int index = pointer;
        storage.remove(index);
    }

    @Override
    protected boolean isExistPointer(Integer pointer) {
        return pointer != null;
    }

    @Override
    protected Integer getPointerToResume(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void insertIntoStorage(Resume resume, Integer pointer) {
        storage.add(resume);
    }

    @Override
    protected void updateByPointer(Integer pointer, Resume resume) {
        storage.set(pointer, resume);
    }

    @Override
    public void clear() {
        storage.clear();

    }

    @Override
    protected List<Resume> getList() {
        return storage.subList(0, size());
    }

    @Override
    public int size() {
        return storage.size();
    }


}
