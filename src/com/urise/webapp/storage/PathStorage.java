package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.fileoperator.FileStorageStrategy;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private FileStorageStrategy strategy;

    public PathStorage(String dir, FileStorageStrategy strategy) {
        Objects.requireNonNull(strategy, "strategy must not be null");
        this.strategy = strategy;
        Path localpath = Paths.get(dir);
        if (!Files.isDirectory(localpath) || !Files.isWritable(localpath) || !Files.isReadable(localpath)) {
            throw new IllegalArgumentException(dir +
                    " is not directory or is not readable/writable");
        }
        directory = localpath;
    }

    @Override
    protected Resume getResumeByPointer(Path pointer) {

        try {
            return strategy.doRead(new BufferedInputStream((Files.newInputStream(pointer))));
        } catch (IOException e) {
            throw new StorageException("Path read error", pointer.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteElementByPointer(Path pointer) {
        try {
            Files.delete(pointer);
        } catch (IOException e) {
            throw new StorageException("Error while deleting", pointer.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isExistPointer(Path pointer) {
        return Files.isRegularFile(pointer);
    }

    @Override
    protected Path getPointerToResume(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void insertIntoStorage(Resume resume, Path pointer) {
        try {
            Files.createFile(pointer);
        } catch (IOException e) {
            throw new StorageException("Can't create file", pointer.getFileName().toString(), e);
        }
        updateByPointer(pointer, resume);
    }


    @Override
    protected void updateByPointer(Path pointer, Resume resume) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(pointer)));
        } catch (IOException e) {
            throw new StorageException("Can't write Path", pointer.getFileName().toString());
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

    private Stream<Path> getStreamItems() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("IO Error", null, e);
        }
    }
}
