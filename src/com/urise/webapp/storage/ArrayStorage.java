package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

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

    @Override
    public int getIndexOfResume(String Uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(Uuid)) {
                return i;
            }
        }
        return -1;
    }
}
