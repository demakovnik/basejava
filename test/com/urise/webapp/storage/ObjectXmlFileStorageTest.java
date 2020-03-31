package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.fileoperator.ObjectToXmlOperator;

public class ObjectXmlFileStorageTest extends AbstractStorageTest {

    public ObjectXmlFileStorageTest() {
        super(new FileStorage(Config.getInstance().getStorageDir(),new ObjectToXmlOperator()));
    }
}