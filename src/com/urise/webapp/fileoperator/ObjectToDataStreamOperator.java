package com.urise.webapp.fileoperator;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ObjectToDataStreamOperator implements FileStorageStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeContacts(resume.getContacts(), dos);
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                writeSection(entry.getKey(), section, dos);
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            EnumMap<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
            EnumMap<ContactType, String> contacts = readContacts(dis);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                sections.put(sectionType,readSection(dis));
            }
            resume.setContacts(contacts);
            resume.setSections(sections);

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
        dos.writeUTF(description);
    }

    private void writeLink(Link link, DataOutputStream dos) throws IOException {
        dos.writeUTF(link.getTitle());
        dos.writeUTF(link.getUrl());
    }

    private void writeOrganization(Organization org, DataOutputStream dos) throws IOException {
        writeLink(org.getLink(), dos);
        List<Position> positionList = org.getPositionList();
        dos.writeInt(positionList.size());
        for (Position position : positionList) {
            writePosition(position, dos);
        }
    }

    private void writeSection(SectionType sectionType, AbstractSection section,
                              DataOutputStream dos) throws IOException {
        List<String> stringList;
        List<Organization> listOfExperienceOrEducation;
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(sectionType.name());
                dos.writeUTF(((PersonalOrObjectiveSection) section).getText());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                dos.writeUTF(sectionType.name());
                stringList = ((AchievementOrQualificationsSection) section)
                        .getListOfAchievementsOrQualifications();
                dos.writeInt(stringList.size());
                for (String s : stringList) {
                    dos.writeUTF(s);
                }
                break;
            case EDUCATION:
            case EXPERIENCE:
                dos.writeUTF(sectionType.name());
                listOfExperienceOrEducation = ((ExperienceOrEducationSection) section)
                        .getListOfExperienceOrEducation();
                dos.writeInt(listOfExperienceOrEducation.size());
                for (Organization organization : listOfExperienceOrEducation) {
                    writeOrganization(organization, dos);
                }
                break;
        }
    }

    private void writeContacts(Map<ContactType, String> contacts, DataOutputStream dos) throws IOException {
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private EnumMap<ContactType, String> readContacts(DataInputStream dis) throws IOException {
        EnumMap<ContactType, String> result = new EnumMap<>(ContactType.class);
        int sizeOfContacts = dis.readInt();
        for (int i = 0; i < sizeOfContacts; i++) {
            ContactType contactType = ContactType.valueOf(dis.readUTF());
            result.put(contactType, dis.readUTF());
        }
        return result;
    }

    private Link readLink(DataInputStream dis) throws IOException {
        String title = dis.readUTF();
        String url = dis.readUTF();
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
        return new Position(title, startTime, endTime, description);
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        Link link = readLink(dis);
        List<Position> positionList = new ArrayList<>();
        int numberOfPositions = dis.readInt();
        for (int i = 0; i < numberOfPositions; i++) {
            positionList.add(readPosition(dis));
        }
        return new Organization(link, positionList);
    }

    private AbstractSection readSection(DataInputStream dis) throws IOException {
        List<String> stringList = new ArrayList<>();
        List<Organization> organizationList = new ArrayList<>();
        SectionType st = SectionType.valueOf(dis.readUTF());
        switch (st) {
            case PERSONAL:
            case OBJECTIVE:
                return new PersonalOrObjectiveSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                int numberOfStrings = dis.readInt();
                for (int i = 0; i < numberOfStrings; i++) {
                    stringList.add(dis.readUTF());
                }
                return new AchievementOrQualificationsSection(new ArrayList<>(stringList));
            case EXPERIENCE:
            case EDUCATION:
                int numberOfOrgs = dis.readInt();
                for (int i = 0; i < numberOfOrgs; i++) {
                    organizationList.add(readOrganization(dis));
                }
                return new ExperienceOrEducationSection(new ArrayList<>(organizationList));
            default:
                throw new IllegalArgumentException("Error");
        }
    }
}