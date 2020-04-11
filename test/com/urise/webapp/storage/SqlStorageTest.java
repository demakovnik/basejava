package com.urise.webapp.storage;

import java.io.IOException;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() throws IOException {
        super(new SqlStorage());

    }
}