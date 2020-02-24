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

    private final static Resume RESUME_1 = new Resume(UUID1);
    private final static Resume RESUME_2 = new Resume(UUID2);
    private final static Resume RESUME_3 = new Resume(UUID3);
    private final static Resume RESUME_4 = new Resume(UUID4);
    private final static Resume RESUME_DUMMY = new Resume(DUMMY);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume updateResume = new Resume(UUID1);
        storage.update(updateResume);
        Assert.assertSame(updateResume, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_DUMMY);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(RESUME_4.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void saveOverflow() {
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException ex) {
            Assert.fail("Storage overflow!");
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID2);
        assertEquals(2, storage.getAll().length);
        storage.get(UUID2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY);
    }

    @Test
    public void get() {
        Resume result = storage.get(UUID2);
        Assert.assertEquals(RESUME_2, result);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void getAll() {
        Resume[] expectedArray = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Resume[] actualArray = storage.getAll();
        assertEquals(3, actualArray.length);
        Assert.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

}