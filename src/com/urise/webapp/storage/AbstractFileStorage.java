package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> getList() {
        File[] files = directory.listFiles();
        Objects.requireNonNull(files, "files must not be null");
        List<Resume> result = new ArrayList<>();
        for (File file : directory.listFiles()) {
            result.add(getResumeByPointer(file));
        }
        return result;
    }

    @Override
    protected Resume getResumeByPointer(File pointer) {
        try {
            return doRead(pointer);
        } catch (IOException e) {
            throw new StorageException("IO Error", pointer.getName(), e);
        }
    }

    @Override
    protected void deleteElementByPointer(File pointer) {
        if (!pointer.delete()) {
            throw new IllegalArgumentException("Can't delete file");
        }
    }

    @Override
    protected boolean isExistPointer(File pointer) {
        return pointer.exists();
    }

    @Override
    protected File getPointerToResume(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void insertIntoStorage(Resume resume, File pointer) {
        try {
            pointer.createNewFile();
            doWrite(resume, pointer);
        } catch (IOException e) {
            throw new StorageException("IO Error", pointer.getName(), e);
        }
    }


    @Override
    protected void updateByPointer(File pointer, Resume resume) {
        try {
            doWrite(resume, pointer);
        } catch (IOException e) {
            throw new StorageException("IO Error", pointer.getName(), e);
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        Objects.requireNonNull(files, "files must not be null");
        for (File file : files) {
            deleteElementByPointer(file);
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        Objects.requireNonNull(files, "files must not be null");
        return directory.list().length;
    }

    protected abstract Resume doRead(File pointer) throws IOException;

    protected abstract void doWrite(Resume resume, File pointer) throws IOException;

}
