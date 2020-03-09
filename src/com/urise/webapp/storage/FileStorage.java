package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.fileoperator.FileStorageStrategy;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileStorage extends AbstractStorage<File>  {

    private File directory;

    private FileStorageStrategy strategy;

    protected FileStorage(String dir, FileStorageStrategy strategy) {
        File localFile = new File(dir);
        if (!localFile.isDirectory()) {
            throw new IllegalArgumentException(localFile.getAbsolutePath() + " is not directory");
        }
        if (!localFile.canRead() || !localFile.canWrite()) {
            throw new IllegalArgumentException(localFile.getAbsolutePath() + " is not readable/writable");
        }
        directory = localFile;
        Objects.requireNonNull(directory,"directory must not be null");
        this.strategy = strategy;
        Objects.requireNonNull(strategy, "strategy must not be null");
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
        getStreamItems().forEach(this::deleteElementByPointer);
    }

    @Override
    public int size() {
        return (int) getStreamItems().count();
    }

    @Override
    protected List<Resume> getList() {
        return getStreamItems().map(this::getResumeByPointer).collect(Collectors.toList());
    }

    protected Stream<File> getStreamItems() {
        return Arrays.stream(directory.listFiles());
    }
}
