package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest {

    private final Storage storage;
    private final String UUID1 = "uuid1";
    private final String UUID2 = "uuid2";
    private final String UUID3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID1));
        storage.save(new Resume(UUID2));
        storage.save(new Resume(UUID3));
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(storage.size(),0);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID1);
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        Assert.assertEquals(resume,storage.get(resume.getUuid()));
    }

    @Test(expected = StorageException.class)
    public void arrayOverflow() {
        for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        Resume deletingResume = storage.get(UUID2);
        storage.delete(deletingResume.getUuid());
        storage.get(UUID2);
    }

    @Test
    public void get() {
        Resume result = storage.get(UUID2);
        Assert.assertEquals(result,new Resume(UUID2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Resume[] result = new Resume[]{new Resume(UUID1), new Resume(UUID2), new Resume(UUID3)};
        Assert.assertEquals(result, storage.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

}