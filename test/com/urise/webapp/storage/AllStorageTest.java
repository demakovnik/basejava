package com.urise.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageResumeKeyTest.class,
        MapStorageUuidKeyTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        ObjectXmlPathStorageTest.class,
        ObjectJsonPathStorageTest.class,
        ObjectXmlFileStorageTest.class,
        ObjectJsonFileStorageTest.class,
        ObjectPathDataStreamStorageTest.class,
        ObjectFileDataStreamStorageTest.class,
        SqlStorageTest.class
})
public class AllStorageTest {
}