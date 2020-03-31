package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.fileoperator.ObjectToByteStreamOperator;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(Config.getInstance().getStorageDir(),new ObjectToByteStreamOperator()));
    }
}