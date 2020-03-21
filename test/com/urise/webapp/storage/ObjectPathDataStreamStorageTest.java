package com.urise.webapp.storage;

import com.urise.webapp.fileoperator.ObjectToDataStreamOperator;

public class ObjectPathDataStreamStorageTest extends AbstractStorageTest {

    public ObjectPathDataStreamStorageTest() {
        super(new PathStorage(STORAGEDIR,new ObjectToDataStreamOperator()));
    }
}