package com.urise.webapp;


import java.io.File;
import java.util.Objects;

public class MainFile {

    public static void main(String[] args) {
        printListOfFiles(new File("."));
    }

    private static void printListOfFiles(File file) {
        File[] listFiles = file.listFiles();
        Objects.requireNonNull(listFiles, "listFiles must not be null");
        for (File f : listFiles) {
            if (f.isDirectory()) {
                System.out.println(f.getName());
                printListOfFiles(f);

            } else System.out.println(f.getName());
        }


    }
}




