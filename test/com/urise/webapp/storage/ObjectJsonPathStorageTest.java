package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.fileoperator.ObjectToJsonOperator;

public class ObjectJsonPathStorageTest extends AbstractStorageTest {

    public ObjectJsonPathStorageTest() {
        super(new PathStorage(Config.getInstance().getStorageDir(),new ObjectToJsonOperator()));
    }
}