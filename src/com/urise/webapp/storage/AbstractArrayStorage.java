package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

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
        System.out.println("Резюме c uuid " + resume.getUuid() + " отсутствует в списке");
    }

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Хранилище резюме заполнено");
            return;
        }
        int indexOfResume = getIndexOfResume(resume.getUuid());
        if (indexOfResume >= 0) {
            System.out.println("Резюме c uuid " + resume.getUuid() + " уже есть в списке");
            return;
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
        System.out.println("Резюме c uuid " + uuid + " отсутствует в списке");
    }

    public Resume get(String uuid) {
        int indexOfResume = getIndexOfResume(uuid);
        if (indexOfResume >= 0) {
            return storage[indexOfResume];
        }
        System.out.println("Резюме c uuid " + uuid + " отсутствует в списке");
        return null;
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
