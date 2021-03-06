package com.urise.webapp.util;

public class LazySingleton {
    private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }




}
