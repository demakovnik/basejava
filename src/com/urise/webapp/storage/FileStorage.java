package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.fileoperator.FileStorageStrategy;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File directory;

    private FileStorageStrategy strategy;

    protected FileStorage(File directory, FileStorageStrategy strategy) {
        Objects.requireNonNull(strategy, "strategy must not be null");
        this.strategy = strategy;

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected Resume getResumeByPointer(File pointer) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(pointer)));
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
            strategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(pointer)));
        } catch (IOException e) {
            throw new StorageException("Can't write file", pointer.getName(), e);
        }
    }

    @Override
    public void clear() {
        File[] files = getFileList();
        for (File file : files) {
            deleteElementByPointer(file);
        }
    }

    @Override
    public int size() {
        return getFileList().length;
    }

    @Override
    protected List<Resume> getList() {
        File[] files = getFileList();
        List<Resume> result = new ArrayList<>();
        for (File file : files) {
            result.add(getResumeByPointer(file));
        }
        return result;
    }

    File[] getFileList() {
        File[] files = directory.listFiles();
        if (files != null) {
            return files;
        }
        throw new StorageException("Error file list","");
    }
}
