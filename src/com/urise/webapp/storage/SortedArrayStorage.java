package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    public void update(Resume resume) {
        int indexOfResume = getIndexOfResume(resume.getUuid());
        if (indexOfResume >= 0) {
            storage[indexOfResume] = resume;
            sort();
            return;
        }
        System.out.println("Резюме c uuid " + resume.getUuid() + " отсутствует в списке");
    }

    @Override
    public void delete(String uuid) {
        int indexOfResume = getIndexOfResume(uuid);
        if (indexOfResume >= 0) {
            storage[indexOfResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            sort();
            return;
        }
        System.out.println("Резюме c uuid " + uuid + " отсутствует в списке");
    }

    @Override
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
        sort();
    }

    @Override
    public int getIndexOfResume(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage,0,size,searchKey);
    }

    private void sort() {
        for (int k = 1; k < size; k++) {
            Resume newResume = storage[k];
            int index = Arrays.binarySearch(storage, 0, k, newResume);
            if (index < 0) {
                index = -(index) - 1;
            }
            System.arraycopy(storage, index, storage, index + 1, k - index);
            storage[index] = newResume;
        }
    }
}
