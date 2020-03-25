package com.urise.webapp;

public class MainConcurrency {

    public static void main(String[] args) throws InterruptedException {
        LockObject resource1 = new LockObject("resource1");
        LockObject resource2 = new LockObject("resource2");
        resource1.setLockingObject(resource2);
        resource2.setLockingObject(resource1);

        new Thread(() -> {
            deadLock(resource1, resource2);
        }).start();

        new Thread(() -> {
            deadLock(resource2, resource1);
        }).start();
    }

    private static void deadLock(LockObject lockObject1, LockObject lockObject2) {
        synchronized (lockObject1) {
            lockObject1.printLockingString();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockObject2) {
                lockObject2.printLockingString();
            }
        }
    }

    static class LockObject {

        private LockObject lockingObject;
        private String name;

        public LockObject(String name, LockObject lockingObject) {
            this.name = name;
            this.lockingObject = lockingObject;
        }

        public LockObject(String name) {
            this(name, null);
        }

        @Override
        public String toString() {
            return "LockObject{" +
                    "name='" + name + '\'' +
                    '}';
        }

        public void printLockingString() {
            System.out.println(getLockingObject());

        }

        public LockObject getLockingObject() {
            return lockingObject;
        }

        public void setLockingObject(LockObject lockingObject) {
            this.lockingObject = lockingObject;
        }
    }

}