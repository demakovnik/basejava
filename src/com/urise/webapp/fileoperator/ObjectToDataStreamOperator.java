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
            Map<ContactType, String> contacts = resume.getContacts();
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeContacts(contacts, dos);
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                writeSection(entry.getKey(), entry.getValue(), dos);
            }

        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            EnumMap<ContactType, String> contacts = readContacts(dis);
            EnumMap<SectionType, AbstractSection> sections = readSections(dis);
            System.out.println(sections);
            resume.setContacts(contacts);
            resume.setSections(sections);

            return resume;
        }
    }

    // private get

    private void addContacts(DataInputStream dis, Resume resume) throws IOException {
        int sizeOfContacts = dis.readInt();
        for (int i = 0; i < sizeOfContacts; i++) {
            String contactKey = dis.readUTF();
            String contactValue = dis.readUTF();
            if (contactKey.equals(ContactType.EMAIL.getTitle())) {
                resume.getContacts().put(ContactType.EMAIL, contactValue);
            } else if (contactKey.equals(ContactType.PHONENUMBER.getTitle())) {
                resume.getContacts().put(ContactType.PHONENUMBER, contactValue);
            } else if (contactKey.equals(ContactType.SKYPE.getTitle())) {
                resume.getContacts().put(ContactType.SKYPE, contactValue);
            } else if (contactKey.equals(ContactType.GITHUB.getTitle())) {
                resume.getContacts().put(ContactType.GITHUB, contactValue);
            } else if (contactKey.equals(ContactType.LINKEDIN.getTitle())) {
                resume.getContacts().put(ContactType.LINKEDIN, contactValue);
            } else if (contactKey.equals(ContactType.HOMEPAGE.getTitle())) {
                resume.getContacts().put(ContactType.HOMEPAGE, contactValue);
            } else if (contactKey.equals(ContactType.STACKOVERFLOW.getTitle())) {
                resume.getContacts().put(ContactType.STACKOVERFLOW, contactValue);
            }
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
        description = description == null ? "null" : description;
        dos.writeUTF(description);
    }

    private void writeLink(Link link, DataOutputStream dos) throws IOException {
        dos.writeUTF(link.getTitle());
        String url = link.getUrl();
        url = url == null ? "null" : url;
        dos.writeUTF(url);
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
                return;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                dos.writeUTF(sectionType.name());
                stringList = ((AchievementOrQualificationsSection) section)
                        .getListOfAchievementsOrQualifications();
                dos.writeInt(stringList.size());
                for (String s : stringList) {
                    dos.writeUTF(s);
                }
                return;
            case EDUCATION:
            case EXPERIENCE:
                dos.writeUTF(sectionType.name());
                listOfExperienceOrEducation = ((ExperienceOrEducationSection) section)
                        .getListOfExperienceOrEducation();
                dos.writeInt(listOfExperienceOrEducation.size());
                for (Organization organization : listOfExperienceOrEducation) {
                    writeOrganization(organization, dos);
                }
                return;
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
        url = url.equals("null") ? null : url;
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
        description = description.equals("null") ? null : description;
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

    private EnumMap<SectionType, AbstractSection> readSections(DataInputStream dis) throws IOException {
        EnumMap<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
        List<String> stringList = new ArrayList<>();
        List<Organization> organizationList = new ArrayList<>();
        for (SectionType st : SectionType.values()) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());

            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    sections.put(sectionType, new PersonalOrObjectiveSection(dis.readUTF()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    int numberOfStrings = dis.readInt();
                    for (int i = 0; i < numberOfStrings; i++) {
                        stringList.add(dis.readUTF());
                    }
                    sections.put(sectionType, new AchievementOrQualificationsSection(new ArrayList<>(stringList)));
                    stringList.clear();
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    int numberOfOrgs = dis.readInt();
                    for (int i = 0; i < numberOfOrgs; i++) {
                        organizationList.add(readOrganization(dis));
                    }
                    sections.put(sectionType, new ExperienceOrEducationSection(new ArrayList<>(organizationList)));
                    organizationList.clear();
                    break;
            }
        }
        return sections;
    }



}