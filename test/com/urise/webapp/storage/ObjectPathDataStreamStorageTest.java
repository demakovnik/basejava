package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.fileoperator.ObjectToDataStreamOperator;

public class ObjectPathDataStreamStorageTest extends AbstractStorageTest {

    public ObjectPathDataStreamStorageTest() {
        super(new PathStorage(Config.getInstance().getStorageDir(),new ObjectToDataStreamOperator()));
    }
}