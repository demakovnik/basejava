package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.*;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainArray {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid5");
        final Resume r2 = new Resume("uuid4");
        final Resume r3 = new Resume("uuid7");
        final Resume r4 = new Resume("uuid2");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        printAll();
        ARRAY_STORAGE.save(r4);


        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();


        System.out.println("Update r2: ");
        ARRAY_STORAGE.update(r2.getUuid(),r4);
        printAll();

        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
