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

    public Resume get(String uuid) {
        int indexOfResume = getIndexOfResume(uuid);
        if (indexOfResume >= 0) {
            return storage[indexOfResume];
        }
        System.out.println("Резюме c uuid " + uuid + " отсутствует в списке");
        return null;
    }

    public void update(Resume resume) {
        int indexOfResume = getIndexOfResume(resume.getUuid());
        if (indexOfResume >= 0) {
            storage[indexOfResume] = resume;
            return;
        }
        System.out.println("Резюме c uuid " + resume.getUuid() + " отсутствует в списке");
    }

    public void delete(String Uuid) {
        int indexOfResume = getIndexOfResume(Uuid);
        if (indexOfResume >= 0) {
            storage[indexOfResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            return;
        }
        System.out.println("Резюме c uuid " + Uuid + " отсутствует в списке");
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
        storage[size++] = resume;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    public abstract int getIndexOfResume(String uuid);
}
