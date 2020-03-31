package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.fileoperator.ObjectToXmlOperator;

public class ObjectXmlPathStorageTest extends AbstractStorageTest {

    public ObjectXmlPathStorageTest() {
        super(new PathStorage(Config.getInstance().getStorageDir(),new ObjectToXmlOperator()));
    }
}