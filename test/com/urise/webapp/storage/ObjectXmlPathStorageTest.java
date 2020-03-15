package com.urise.webapp.storage;

import com.urise.webapp.fileoperator.ObjectToXmlOperator;

public class ObjectXmlPathStorageTest extends AbstractStorageTest {

    public ObjectXmlPathStorageTest() {
        super(new PathStorage(STORAGEDIR,new ObjectToXmlOperator()));
    }
}
