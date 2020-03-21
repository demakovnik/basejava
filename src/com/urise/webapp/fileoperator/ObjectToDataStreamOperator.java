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
            writeCollectionToDataStream(resume.getContacts().entrySet(), dos, (value) -> {
                dos.writeUTF(value.getKey().name());
                dos.writeUTF(value.getValue());
            });

            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeCollectionToDataStream(sections.entrySet(), dos, value -> {
                SectionType sectionType = value.getKey();
                AbstractSection section = value.getValue();
                dos.writeUTF(sectionType.name());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((PersonalOrObjectiveSection) section).getText());
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollectionToDataStream(((AchievementOrQualificationsSection) section)
                                .getListOfAchievementsOrQualifications(), dos, dos::writeUTF);
                        break;

                    case EDUCATION:
                    case EXPERIENCE:
                        writeCollectionToDataStream(((ExperienceOrEducationSection) section)
                                        .getListOfExperienceOrEducation(), dos,
                                org -> {
                                    writeLink(org.getLink(), dos);
                                    writeCollectionToDataStream(org.getPositionList(), dos,
                                            position -> {
                                                dos.writeUTF(position.getTitle());
                                                LocalDate startTime = position.getStartTime();
                                                LocalDate endTime = position.getEndTime();
                                                dos.writeInt(startTime.getYear());
                                                dos.writeInt(startTime.getMonthValue());
                                                dos.writeInt(endTime.getYear());
                                                dos.writeInt(endTime.getMonthValue());
                                                String description = position.getDescription();
                                                description = description == null ? "" : description;
                                                dos.writeUTF(description);
                                            });
                                });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readCollectionFromDataStream(dis, () -> {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String string = dis.readUTF();
                resume.putContact(contactType, string);
            });

            readCollectionFromDataStream(dis, () -> {
                AbstractSection section;
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new PersonalOrObjectiveSection(dis.readUTF());
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> stringList = new ArrayList<>();
                        readCollectionFromDataStream(dis,
                                () -> stringList.add(dis.readUTF()));
                        section = new AchievementOrQualificationsSection(stringList);
                        break;

                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = new ArrayList<>();
                        readCollectionFromDataStream(dis,
                                () -> {
                                    Link link = readLink(dis);
                                    List<Position> positionList = new ArrayList<>();
                                    readCollectionFromDataStream(dis,
                                            () -> {
                                                String title = dis.readUTF();
                                                LocalDate startTime = DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
                                                LocalDate endTime = DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
                                                String description = dis.readUTF();
                                                description = description.equals("") ? null : description;
                                                positionList.add(new Position(title, startTime, endTime, description));
                                            }
                                    );
                                    organizationList.add(new Organization(link, positionList));
                                });

                        section = new ExperienceOrEducationSection(organizationList);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionType);
                }
                resume.putSection(sectionType, section);
            });
            return resume;
        }
    }

    @FunctionalInterface
    interface CollectionWriter<T> {
        void writeOperation(T value) throws IOException;
    }

    private void writeLink(Link link, DataOutputStream dos) throws IOException {
        dos.writeUTF(link.getTitle());
        String url = link.getUrl();
        url = url == null ? "" : url;
        dos.writeUTF(url);
    }

    private <T> void writeCollectionToDataStream(Collection<T> collection,
                                                 DataOutputStream dataOutputStream, CollectionWriter<T> writer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T value : collection) {
            writer.writeOperation(value);
        }
    }

    @FunctionalInterface
    interface CollectionReader {
        void readOperation() throws IOException;
    }

    private void readCollectionFromDataStream(DataInputStream dataInputStream,
                                              CollectionReader reader) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            reader.readOperation();
        }
    }

    private Link readLink(DataInputStream dis) throws IOException {
        String title = dis.readUTF();
        String url = dis.readUTF();
        url = url.equals("") ? null : url;
        return new Link(title, url);
    }
}