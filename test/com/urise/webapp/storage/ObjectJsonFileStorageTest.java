package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.fileoperator.ObjectToJsonOperator;

public class ObjectJsonFileStorageTest extends AbstractStorageTest {

    public ObjectJsonFileStorageTest() {
        super(new FileStorage(Config.getInstance().getStorageDir(),new ObjectToJsonOperator()));
    }
}