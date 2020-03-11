package com.urise.webapp.fileoperator;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ObjectToXmlOperator implements FileStorageStrategy {
    private XmlParser xmlParser;

    public ObjectToXmlOperator() {
        xmlParser = new XmlParser(Resume.class, Organization.class, Link.class,
                AchievementOrQualificationsSection.class, ExperienceOrEducationSection.class,
                PersonalOrObjectiveSection.class, Position.class);

    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
           return xmlParser.unmarshall(r);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try(Writer w = new OutputStreamWriter(os)) {
            xmlParser.marshall(resume, w);
        }
    }
}
