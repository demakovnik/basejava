package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.fileoperator.ObjectToDataStreamOperator;

public class ObjectFileDataStreamStorageTest extends AbstractStorageTest {

    public ObjectFileDataStreamStorageTest() {
        super(new FileStorage(Config.getInstance().getStorageDir(),new ObjectToDataStreamOperator()));
    }
}