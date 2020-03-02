package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
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

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_3);
        storage.save(RESUME_2);
        storage.save(RESUME_1);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume updateResume = new Resume(RESUME_2.getUuid(), "UpdateName");
        storage.update(updateResume);
        Assert.assertSame(updateResume, storage.get(RESUME_2.getUuid()));
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

    @Test
    public void getAll() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(RESUME_1, list.get(0));
        assertEquals(RESUME_2, list.get(1));
        assertEquals(RESUME_3, list.get(2));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}
