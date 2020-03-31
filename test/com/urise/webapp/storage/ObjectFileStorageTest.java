package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.fileoperator.ObjectToByteStreamOperator;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(Config.getInstance().getStorageDir(),new ObjectToByteStreamOperator()));
    }
}