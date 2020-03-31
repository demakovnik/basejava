package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    protected static final File PROPS = new File("config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private Properties props = new Properties();
    private File storageDir;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public static void main(String[] args) {
        System.out.println(getInstance().dbUrl);
    }

    private Config() {
        try(InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");
        } catch (IOException ex) {
            throw new IllegalStateException("Invalid config file " +  PROPS.getAbsolutePath());
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbUrl() {
        return dbUrl;
    }
}
