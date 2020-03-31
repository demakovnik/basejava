package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final String DBURL = Config.getInstance().getDbUrl();
    protected static final String DBUSER = Config.getInstance().getDbUser();
    protected static final String DBPASSWORD = Config.getInstance().getDbPassword();
    protected final Storage storage;

    private final static Resume RESUME_1 = ResumeTestData.RESUME1;
    private final static Resume RESUME_2 = ResumeTestData.RESUME2;
    private final static Resume RESUME_3 = ResumeTestData.RESUME3;
    private final static Resume RESUME_4 = ResumeTestData.RESUME4;
    private final static Resume RESUME_DUMMY = ResumeTestData.RESUMEDUMMY;

    protected AbstractStorageTest(Storage storage) {
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

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_DUMMY);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Resume actualResume = storage.get(RESUME_4.getUuid());
        Assert.assertEquals(RESUME_4, actualResume);
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
        storage.delete(RESUME_DUMMY.getUuid());
    }

    @Test
    public void get() {
        Resume result = storage.get(RESUME_1.getUuid());
        Assert.assertEquals(RESUME_1, result);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(RESUME_DUMMY.getUuid());
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

    @Test
    public void update() {
        Resume updateResume = new Resume(RESUME_1.getUuid(), "UpdateName");
        storage.update(updateResume);
        Resume actualResume = storage.get(RESUME_1.getUuid());
        Assert.assertEquals(updateResume, actualResume);
    }
}
