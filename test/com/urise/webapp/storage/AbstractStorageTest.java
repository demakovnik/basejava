package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractStorageTest {
    protected final Storage storage;
    private final static String NAME1 = "name1";
    private final static String NAME2 = "name2";
    private final static String NAME3 = "name3";
    private final static String NAME4 = "name4";
    private final static String DUMMY = "dummy";

    private final static Resume RESUME_1 = new Resume(NAME1);
    private final static Resume RESUME_2 = new Resume(NAME2);
    private final static Resume RESUME_3 = new Resume(NAME3);
    private final static Resume RESUME_4 = new Resume(NAME4);
    private final static Resume RESUME_DUMMY = new Resume(DUMMY);

    public AbstractStorageTest(Storage storage) {
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
        Resume updateResume = RESUME_4;
        storage.update(RESUME_1.getUuid(), updateResume);
        Assert.assertSame(updateResume, storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_DUMMY.getUuid(), RESUME_DUMMY);
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

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        String deletingUuid = RESUME_2.getUuid();
        storage.delete(deletingUuid);
        assertEquals(2, storage.getAllSorted().size());
        storage.get(deletingUuid);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY);
    }

    @Test
    public void get() {
        Resume result = storage.get(RESUME_2.getUuid());
        Assert.assertEquals(RESUME_2, result);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    /*@Test
    public void getAll() {
        Resume[] array = storage.getAllSorted().toArray();
        assertEquals(3, array.length);
        assertEquals(RESUME_1, array[0]);
        assertEquals(RESUME_2, array[1]);
        assertEquals(RESUME_3, array[2]);
    }*/

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

}
