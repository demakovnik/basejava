package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    private final Storage storage;
    private final static String UUID1 = "uuid1";
    private final static String UUID2 = "uuid2";
    private final static String UUID3 = "uuid3";
    private final static String UUID4 = "uuid4";
    private final static String DUMMY = "dummy";

    private final Resume resume1 = new Resume(UUID1);
    private final Resume resume2 = new Resume(UUID2);
    private final Resume resume3 = new Resume(UUID3);
    private final Resume resume4 = new Resume(UUID4);
    private final Resume resumeDummy = new Resume(DUMMY);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(resume1);
        Assert.assertTrue(resume1 == storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resumeDummy);
    }

    @Test
    public void save() {
        storage.save(resume4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resume4, storage.get(resume4.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException ex) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID2);
        assertEquals(2, storage.size());
        storage.get(UUID2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY);
    }

    @Test
    public void get() {
        Resume result = storage.get(UUID2);
        Assert.assertEquals(resume2, result);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void getAll() {
        Resume[] result = new Resume[]{resume1, resume2, resume3};
        assertEquals(3, storage.size());
        Assert.assertArrayEquals(storage.getAll(), result);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

}