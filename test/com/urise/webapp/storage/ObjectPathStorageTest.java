package com.urise.webapp.storage;

import com.urise.webapp.fileoperator.ObjectToByteStreamOperator;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGEDIR,new ObjectToByteStreamOperator()));
    }
}
