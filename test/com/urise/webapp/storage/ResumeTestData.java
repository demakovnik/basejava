package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.getContacts().put(ContactType.PHONENUMBER, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContacts().put(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.getContacts().put(ContactType.HOMEPAGE, "http://gkislin.ru/");
        resume.getSections().put(SectionType.OBJECTIVE, new PersonalOrObjectiveSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.getSections().put(SectionType.PERSONAL, new PersonalOrObjectiveSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        List<String> achievements = new ArrayList<>();
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии " +
                "через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы " +
                "по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, " +
                "Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, " +
                "Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, " +
                "Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, " +
                "XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, " +
                "OAuth2, " + "JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix," +
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, " +
                "OpenCmis, Bonita, pgBouncer.");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных " +
                "шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");

        /**
         * Добавляем организации
         *
         */
        List<Organization> organizations = new ArrayList<>();
        organizations.add(new Organization("Java Online Projects", "Автор проекта",
                LocalDate.of(2013, 10, 1), LocalDate.now(),
                "Создание, организация и проведение Java онлайн проектов и стажировок.", "http://javaops.ru/"));
        organizations.add(new Organization("Wrike", "Старший разработчик (backend)",
                LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 31),
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, " +
                        "Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация " +
                        "по OAuth1, OAuth2, JWT SSO.", "https://www.wrike.com/"));
        organizations.add(new Organization("RIT Center", "Java архитектор",
                LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 31),
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                        "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование " +
                        "системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка " +
                        "интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, " +
                        "экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера " +
                        "документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, " +
                        "Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting " +
                        "via ssh tunnels, PL/Python", null));
        organizations.add(new Organization("Luxoft (Deutsche Bank)", "Ведущий программист",
                LocalDate.of(2010, 12, 1), LocalDate.of(2012, 4, 1),
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, " +
                        "GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения " +
                        "для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. " +
                        "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.", "http://www.luxoft.ru/"));
        organizations.add(new Organization("Yota", "Ведущий специалист",
                LocalDate.of(2008, 6, 1), LocalDate.of(2010, 12, 1),
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, " +
                        "v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, " +
                        "статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)",
                "https://www.yota.ru/"));
        organizations.add(new Organization("Enkata", "Разработчик ПО",
                LocalDate.of(2007, 3, 1), LocalDate.of(2008, 6, 1),
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                        "частей кластерного J2EE приложения (OLAP, Data mining).", "http://enkata.com/"));
        organizations.add(new Organization("Siemens AG", "Разработчик ПО",
                LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1),
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на " +
                        "мобильной IN платформе Siemens @vantage (Java, Unix).", "https://www.siemens.com/ru/ru/home.html"));
        organizations.add(new Organization("Alcatel", "Инженер по аппаратному и программному тестированию",
                LocalDate.of(1997, 9, 1), LocalDate.of(2005, 1, 1),
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                "http://www.alcatel.ru/"));
        /*********************************************************************************************************/
        /**
         * Добавляем секцию организаций в резюме
         *
         */
        resume.getSections().put(SectionType.EXPERIENCE, new ExperienceOrEducationSection(organizations));

        /**
         * Добавляем учебные заведения
         *
         */
        List<Organization> educationals = new ArrayList<>();
        educationals.add(new Organization("Coursera", "\"Functional Programming Principles in Scala\" by Martin Odersky",
                LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1),
                null, "https://www.coursera.org/course/progfun"));
        educationals.add(new Organization("Luxoft", "Курс \"Объектно-ориентированный анализ ИС. " +
                "Концептуальное моделирование на UML.\"", LocalDate.of(2011, 3, 1),
                LocalDate.of(2011, 4, 1), null, "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"));
        educationals.add(new Organization("Siemens AG", "3 месяца обучения мобильным IN сетям (Берлин)",
                LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1), null,
                "http://www.siemens.ru/"));
        educationals.add(new Organization("Alcatel", "6 месяцев обучения цифровым телефонным сетям (Москва)",
                LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1), null,
                "http://www.alcatel.ru/"));
        educationals.add(new Organization("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", "Аспирантура (программист С, С++)",
                LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1), null,
                "http://www.ifmo.ru/"));
        educationals.add(new Organization("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", "Инженер (программист Fortran, C)",
                LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1),
                null, "http://www.ifmo.ru/"));
        educationals.add(new Organization("Заочная физико-техническая школа при МФТИ", "Закончил с отличием",
                LocalDate.of(1984, 9, 1), LocalDate.of(1987, 6, 1), null,
                "http://www.school.mipt.ru/"));
        /**********************************************************************************************************/
        /**
         * Добавляем секцию учебных заведений в резюме
         *
         */
        resume.getSections().put(SectionType.EDUCATION, new ExperienceOrEducationSection(educationals));
        resume.getSections().put(SectionType.ACHIEVEMENT, new AchievementOrQualificationsSection(achievements));
        resume.getSections().put(SectionType.QUALIFICATIONS, new AchievementOrQualificationsSection(qualifications));
        System.out.println(resume.getFullName());
        for (Map.Entry<ContactType, String> set : resume.getContacts().entrySet()) {
            System.out.println(set.getKey().getTitle() + ": " + set.getValue());
        }

        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            System.out.println(entry.getKey().getTitle() + "\n");
            Object value = entry.getValue();

            if (value instanceof ExperienceOrEducationSection) {
                List<Organization> list = ((ExperienceOrEducationSection) value).getListOfExperienceOrEducation();
                for (Organization organization : list) {
                    System.out.println(organization);
                }
            } else if (value instanceof PersonalOrObjectiveSection) {
                String text = ((PersonalOrObjectiveSection) value).getText();
                System.out.println(text);
            } else if (value instanceof AchievementOrQualificationsSection) {
                List<String> list = ((AchievementOrQualificationsSection) value).getListOfAchievementsOrQualifications();
                for (String text : list) {
                    System.out.println(text);
                }
            }
        }
    }
}
