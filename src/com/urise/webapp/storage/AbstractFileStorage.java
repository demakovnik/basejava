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
        if (files == null) {
            throw new StorageException("Error file list", null);
        }
        List<Resume> result = new ArrayList<>();
        for (File file : files) {
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
            throw new StorageException("Can't delete file", pointer.getName());
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
        } catch (IOException e) {
            throw new StorageException("Can't create file", pointer.getName(), e);
        }
        updateByPointer(pointer, resume);
    }


    @Override
    protected void updateByPointer(File pointer, Resume resume) {
        try {
            doWrite(resume, pointer);
        } catch (IOException e) {
            throw new StorageException("Can't write file", pointer.getName(), e);
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
        String[] files = directory.list();
        if (files == null) {
            throw new StorageException("fileList Error", null);
        }
        return files.length;
    }

    protected abstract Resume doRead(File pointer) throws IOException;

    protected abstract void doWrite(Resume resume, File pointer) throws IOException;

}
