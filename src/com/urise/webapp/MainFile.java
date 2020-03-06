package com.urise.webapp;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainFile {

    public static void main(String[] args) {
        printListOfFiles(".");
    }

    private static void printListOfFiles(String path) {
        File file = new File(path);
        File[] listFiles = file.listFiles();
        Objects.requireNonNull(listFiles, "listFiles must not be null");
        for (File f : listFiles) {
            if (f.isDirectory()) {
                try {
                    printListOfFiles(f.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else System.out.println(f.getName());
        }


    }
}




