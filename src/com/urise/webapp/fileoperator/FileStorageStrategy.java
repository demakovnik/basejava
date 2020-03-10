package com.urise.webapp.fileoperator;

import com.urise.webapp.model.Resume;

import java.io.*;

public interface FileStorageStrategy {

    Resume doRead(InputStream is) throws IOException;

    void doWrite(Resume resume, OutputStream os) throws IOException;
}

