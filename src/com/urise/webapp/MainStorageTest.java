package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainStorageTest {
    private final static Storage STORAGE = new MapStorageResumeKey();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume resume;
        while (true) {
            System.out.print("Введите одну из команд - (list | save fullName | delete uuid | get uuid | clear | update uuid fullName | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            String fullName = null;
            if (params.length == 2 && !params[0].equals("save")) {
                uuid = params[1].intern();
            } else if (params.length == 3 && !params[0].equals("save")) {
                uuid = params[1].intern();
                fullName = params[2].intern();
            } else if (params[0].equals("save")) {
                fullName = params[1].intern();
            }

            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(STORAGE.size());
                    break;
                case "save":
                    resume = new Resume(fullName);
                    STORAGE.save(resume);
                    printAll();
                    break;
                case "delete":
                    STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(STORAGE.get(uuid));
                    break;
                case "clear":
                    STORAGE.clear();
                    printAll();
                    break;
                case "update":
                    resume = new Resume(uuid, fullName);
                    STORAGE.update(uuid, resume);
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> all = STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}