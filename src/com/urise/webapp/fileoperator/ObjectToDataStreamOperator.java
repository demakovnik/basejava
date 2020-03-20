package com.urise.webapp.fileoperator;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ObjectToDataStreamOperator implements FileStorageStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeContacts(resume.getContacts(), dos);
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeCollectionToDataStream(sections.entrySet(), dos, value -> {
                SectionType type = value.getKey();
                AbstractSection section = value.getValue();
                dos.writeUTF(type.name());
                writeSection(type, section, dos);
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            EnumMap<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
            EnumMap<ContactType, String> contacts = readContacts(dis, new EnumMap<ContactType, String>(ContactType.class));
            readCollectionFromDataStream(sections.entrySet(), dis, (dataInputStream, collection) -> {
                        SectionType sectionType = SectionType.valueOf(dis.readUTF());
                        sections.put(sectionType, readSection(dis, sectionType));
                    }
            );
            resume.getContacts().putAll(contacts);
            resume.getSections().putAll(sections);
            return resume;
        }
    }

    private void writeLocalDate(LocalDate date, DataOutputStream dos) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }

    private void writePosition(Position position, DataOutputStream dos) throws IOException {
        dos.writeUTF(position.getTitle());
        writeLocalDate(position.getStartTime(), dos);
        writeLocalDate(position.getEndTime(), dos);
        String description = position.getDescription();
        description = description == null ? "" : description;
        dos.writeUTF(description);
    }

    private void writeLink(Link link, DataOutputStream dos) throws IOException {
        dos.writeUTF(link.getTitle());
        String url = link.getUrl();
        url = url == null ? "" : url;
        dos.writeUTF(url);
    }

    private void writeOrganization(Organization org, DataOutputStream dos) throws IOException {
        writeLink(org.getLink(), dos);
        writeCollectionToDataStream(org.getPositionList(), dos, value -> writePosition(value, dos));
    }

    private void writeSection(SectionType sectionType, AbstractSection section,
                              DataOutputStream dos) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(((PersonalOrObjectiveSection) section).getText());
                break;

            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeCollectionToDataStream(((AchievementOrQualificationsSection) section)
                                .getListOfAchievementsOrQualifications(), dos,
                        value -> dos.writeUTF(value));
                break;

            case EDUCATION:
            case EXPERIENCE:
                writeCollectionToDataStream(((ExperienceOrEducationSection) section)
                                .getListOfExperienceOrEducation(), dos,
                        value -> writeOrganization(value, dos));
                break;
        }
    }

    private <T> void writeCollectionToDataStream(Collection<T> collection, DataOutputStream dataOutputStream, CollectionWriter<T> writer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T value : collection) {
            writer.writeOperation(value);
        }
    }

    private <T> Collection<T> readCollectionFromDataStream(Collection<T> collection,
                                                           DataInputStream dataInputStream,
                                                           CollectionReader reader) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            reader.readOperation(dataInputStream, collection);
        }
        return collection;
    }

    @FunctionalInterface
    interface CollectionWriter<T> {
        void writeOperation(T value) throws IOException;
    }

    @FunctionalInterface
    interface CollectionReader<T> {
        void readOperation(DataInputStream dataInputStream, Collection<T> collection) throws IOException;
    }

    private void writeContacts(Map<ContactType, String> contacts, DataOutputStream dos) throws IOException {
        writeCollectionToDataStream(contacts.entrySet(), dos, value -> {
            dos.writeUTF(value.getKey().name());
            dos.writeUTF(value.getValue());
        });
    }

    private EnumMap<ContactType, String> readContacts(DataInputStream dis, EnumMap<ContactType, String> contacts) throws IOException {

        readCollectionFromDataStream(contacts.entrySet(), dis, (dataInputStream, collection) -> {
            ContactType contactType = ContactType.valueOf(dis.readUTF());
            String string = dis.readUTF();
            contacts.put(contactType, string);
        });
        return contacts;
    }

    private Link readLink(DataInputStream dis) throws IOException {
        String title = dis.readUTF();
        String url = dis.readUTF();
        url = url.equals("") ? null : url;
        return new Link(title, url);
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        int year = dis.readInt();
        Month month = Month.of(dis.readInt());
        return DateUtil.of(year, month);
    }

    private Position readPosition(DataInputStream dis) throws IOException {
        String title = dis.readUTF();
        LocalDate startTime = readLocalDate(dis);
        LocalDate endTime = readLocalDate(dis);
        String description = dis.readUTF();
        description = description.equals("") ? null : description;
        return new Position(title, startTime, endTime, description);
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        Link link = readLink(dis);
        List<Position> positionList = new ArrayList<>();
        readCollectionFromDataStream(positionList, dis, (dataInputStream, collection) -> positionList.add(readPosition(dis)));
        return new Organization(link, positionList);
    }

    private AbstractSection readSection(DataInputStream dis, SectionType st) throws IOException {
        AbstractSection result = null;
        switch (st) {
            case PERSONAL:
            case OBJECTIVE:
                result = new PersonalOrObjectiveSection(dis.readUTF());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> stringList = new ArrayList<>();
                readCollectionFromDataStream(stringList, dis, (dataInputStream, collection) -> stringList.add(dis.readUTF()));
                result = new AchievementOrQualificationsSection(stringList);
                break;

            case EXPERIENCE:
            case EDUCATION:
                List<Organization> organizationList = new ArrayList<>();
                readCollectionFromDataStream(organizationList,
                        dis,
                        (dataInputStream, collection) -> organizationList.add(readOrganization(dis)));
                result = new ExperienceOrEducationSection(organizationList);
                break;
        }
        return result;
    }
}