package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException {

    public NotExistStorageException(String uuid) {
        super("Резюме с uuid " + uuid + " отсутствует в хранилище.", uuid);
    }
}
