package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage{
    public static final int STORAGE_LIMIT = 10000;
    Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

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

    private int getIndexOfResume(String Uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(Uuid)) {
                return i;
            }
        }
        return -1;
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
}
