package com.urise.webapp.storage;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage{

    public int getIndexOfResume(String Uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(Uuid)) {
                return i;
            }
        }
        return -1;
    }
}
