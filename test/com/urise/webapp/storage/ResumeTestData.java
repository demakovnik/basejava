package com.urise.webapp.storage;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.util.DateUtil.NOW;

public class ResumeTestData {

    public static final Resume RESUME1 = new Resume("Григорий Кислин");
    public static final Resume RESUME2 = new Resume("Иванов Иван");
    public static final Resume RESUME3 = new Resume("Петров Петр");
    public static final Resume RESUME4 = new Resume("Романов Роман");
    public static final Resume RESUMEDUMMY = new Resume("Dummy");


    //Инициализация RESUME1
    static {
        RESUME1.getContacts().put(ContactType.PHONENUMBER, "+7(921) 855-0482");
        RESUME1.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        RESUME1.getContacts().put(ContactType.EMAIL, "gkislin@yandex.ru");
        RESUME1.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        RESUME1.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        RESUME1.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        RESUME1.getContacts().put(ContactType.HOMEPAGE, "http://gkislin.ru/");
        /*RESUME1.getSections().put(SectionType.OBJECTIVE, new PersonalOrObjectiveSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        RESUME1.getSections().put(SectionType.PERSONAL, new PersonalOrObjectiveSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
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

        //Добавляем организации

        List<Organization> organizations = new ArrayList<>();

        //Добавляем 1 организацию
        List<Position> positionOneList = new ArrayList<>();
        positionOneList.add(new Position("Автор проекта", DateUtil.of(2013, Month.OCTOBER),
                NOW, "Создание, организация и проведение Java онлайн проектов и стажировок."));

        organizations.add(new Organization("Java Online Projects", "http://javaops.ru/", positionOneList));
        //************************************************************************************************************

        //Добавляем 2 организацию
        List<Position> positionTwoList = new ArrayList<>();
        positionTwoList.add(new Position("Старший разработчик (backend)", DateUtil.of(2014, Month.OCTOBER),
                DateUtil.of(2016, Month.JANUARY), "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, " +
                "Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация " +
                "по OAuth1, OAuth2, JWT SSO."));

        organizations.add(new Organization("Wrike", "https://www.wrike.com/", positionTwoList));
        //*******************************************************************************************************

        //Добавляем 3 организацию
        List<Position> positionThreeList = new ArrayList<>();
        positionThreeList.add(new Position("Java архитектор", DateUtil.of(2012, Month.APRIL),
                DateUtil.of(2014, Month.OCTOBER), "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование " +
                "системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка " +
                "интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, " +
                "экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера " +
                "документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, " +
                "Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting " +
                "via ssh tunnels, PL/Python"));

        organizations.add(new Organization("RIT Center", null, positionThreeList));
        //******************************************************************************************************

        //Добавляем 4 организацию
        List<Position> positionFourList = new ArrayList<>();
        positionFourList.add(new Position("Ведущий программист", DateUtil.of(2010, Month.DECEMBER),
                DateUtil.of(2012, Month.APRIL), "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, " +
                "GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения " +
                "для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. " +
                "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));

        organizations.add(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", positionFourList));
        //***************************************************************************************************

        //Добавляем 5 организацию
        List<Position> positionFiveList = new ArrayList<>();
        positionFiveList.add(new Position("Ведущий специалист", DateUtil.of(2008, Month.JUNE),
                DateUtil.of(2010, Month.DECEMBER), "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, " +
                "v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, " +
                "статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));

        organizations.add(new Organization("Yota", "https://www.yota.ru/", positionFiveList));
        //**************************************************************************************************

        //Добавляем 6 организацию
        List<Position> positionSixList = new ArrayList<>();
        positionSixList.add(new Position("Разработчик ПО", DateUtil.of(2007, Month.MARCH),
                DateUtil.of(2008, Month.JUNE), "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                "частей кластерного J2EE приложения (OLAP, Data mining)."));

        organizations.add(new Organization("Enkata", "http://enkata.com//", positionSixList));
        //**************************************************************************************************

        //Добавляем 7 организацию
        List<Position> positionSevenList = new ArrayList<>();
        positionSevenList.add(new Position("Разработчик ПО", DateUtil.of(2005, Month.JANUARY),
                DateUtil.of(2007, Month.FEBRUARY), "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на " +
                "мобильной IN платформе Siemens @vantage (Java, Unix)."));

        organizations.add(new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html", positionSevenList));
        //**************************************************************************************************

        //Добавляем 8 организацию
        List<Position> positionEightList = new ArrayList<>();
        positionEightList.add(new Position("Инженер по аппаратному и программному тестированию", DateUtil.of(1997, Month.SEPTEMBER),
                DateUtil.of(2005, Month.JANUARY), "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));

        organizations.add(new Organization("Alcatel", "http://www.alcatel.ru/", positionEightList));

        //Добавляем секцию организаций в резюме
        RESUME1.getSections().put(SectionType.EXPERIENCE, new ExperienceOrEducationSection(organizations));

        //Добавляем учебные заведения



        List<Organization> educationals = new ArrayList<>();

        //Добавляем 1 учебное заведение
        List<Position> positionOneListEducation = new ArrayList<>();
        positionOneListEducation.add(new Position("\"Functional Programming Principles in Scala\" by Martin Odersky",
                DateUtil.of(2013, Month.MARCH),
                DateUtil.of(2013, Month.MAY), null));

        educationals.add(new Organization("Coursera", "https://www.coursera.org/course/progfun", positionOneListEducation));
        //**************************************************************************************************

        //Добавляем 2 учебное заведение
        List<Position> positionTwoListEducation = new ArrayList<>();
        positionTwoListEducation.add(new Position("\"Объектно-ориентированный анализ ИС. " +
                "Концептуальное моделирование на UML.\"",
                DateUtil.of(2011, Month.MARCH),
                DateUtil.of(2011, Month.APRIL), null));

        educationals.add(new Organization("Coursera", "https://www.coursera.org/course/progfun", positionTwoListEducation));
        //**************************************************************************************************

        //Добавляем 3 учебное заведение
        List<Position> positionThreeListEducation = new ArrayList<>();
        positionThreeListEducation.add(new Position("\"Объектно-ориентированный анализ ИС. " +
                "Концептуальное моделирование на UML.\"",
                DateUtil.of(2011, Month.MARCH),
                DateUtil.of(2011, Month.APRIL), null));

        educationals.add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                positionThreeListEducation));
        //**************************************************************************************************

        //Добавляем 4 учебное заведение
        List<Position> positionFourListEducation = new ArrayList<>();
        positionFourListEducation.add(new Position("3 месяца обучения мобильным IN сетям (Берлин)",
                DateUtil.of(2005, Month.JANUARY),
                DateUtil.of(2005, Month.APRIL), null));

        educationals.add(new Organization("Siemens AG", "http://www.siemens.ru/",
                positionFourListEducation));
        //**************************************************************************************************

        //Добавляем 5 учебное заведение
        List<Position> positionFiveListEducation = new ArrayList<>();
        positionFiveListEducation.add(new Position("6 месяцев обучения цифровым телефонным сетям (Москва)",
                DateUtil.of(1997, Month.SEPTEMBER),
                DateUtil.of(1998, Month.MARCH), null));

        educationals.add(new Organization("Alcatel", "http://www.alcatel.ru/",
                positionFiveListEducation));
        //**************************************************************************************************

        //Добавляем 6 учебное заведение
        List<Position> positionSixListEducation = new ArrayList<>();
        positionSixListEducation.add(new Position("Аспирантура (программист С, С++)",
                DateUtil.of(1993, Month.SEPTEMBER),
                DateUtil.of(1996, Month.JULY), null));
        positionSixListEducation.add(new Position("Инженер (программист Fortran, C)",
                DateUtil.of(1987, Month.SEPTEMBER),
                DateUtil.of(1993, Month.JULY), null));

        educationals.add(new Organization("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                positionSixListEducation));
        //**************************************************************************************************

        //Добавляем 7 учебное заведение
        List<Position> positionSevenListEducation = new ArrayList<>();
        positionSevenListEducation.add(new Position("Закончил с отличием",
                DateUtil.of(1984, Month.SEPTEMBER),
                DateUtil.of(1987, Month.JUNE), null));

        educationals.add(new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                positionSevenListEducation));
        //**************************************************************************************************


        /*
         * Добавляем секцию учебных заведений в резюме
         *
         *//*
        RESUME1.getSections().put(SectionType.EDUCATION, new ExperienceOrEducationSection(educationals));

        RESUME1.getSections().put(SectionType.ACHIEVEMENT, new AchievementOrQualificationsSection(achievements));
        RESUME1.getSections().put(SectionType.QUALIFICATIONS, new AchievementOrQualificationsSection(qualifications));*/
    }


    //Инициализация RESUME2
    static {
        RESUME2.getContacts().put(ContactType.PHONENUMBER, "+0(000) 000-0000");
        RESUME2.getContacts().put(ContactType.SKYPE, "hello.world");
        RESUME2.getContacts().put(ContactType.EMAIL, "helloworld@yandex.ru");
        /*RESUME2.getSections().put(SectionType.OBJECTIVE, new PersonalOrObjectiveSection("Супер-пупер сотрудник"));
        RESUME2.getSections().put(SectionType.PERSONAL, new PersonalOrObjectiveSection("Очень крутой спец"));
        List<String> achievements = new ArrayList<>();
        achievements.add("В 2018 делал то");
        achievements.add("В 2019 делал сё");
        List<String> qualifications = new ArrayList<>();
        qualifications.add("На все руки мастер");


        // Добавляем организации
        List<Organization> organizations = new ArrayList<>();

        //Добавляем 1 организацию
        List<Position> positionOneList = new ArrayList<>();
        positionOneList.add(new Position("Директор", DateUtil.of(2018, Month.JANUARY),
                NOW, "Делал все что угодно"));

        organizations.add(new Organization("Рога и копыта", "http://roga.ru/", positionOneList));
        //************************************************************************************************************

        //Добавляем секцию организаций в резюме
        RESUME2.getSections().put(SectionType.EXPERIENCE, new ExperienceOrEducationSection(organizations));

        //Добавляем учебные заведения

        List<Organization> educationals = new ArrayList<>();

        //Добавляем 1 учебное заведение
        List<Position> positionOneListEducation = new ArrayList<>();
        positionOneListEducation.add(new Position("Воспитанник детского сада",
                DateUtil.of(2013, Month.MARCH),
                DateUtil.of(2013, Month.MAY), null));

        educationals.add(new Organization("Детский сад \"Малыш\"", "https://malysh.ru", positionOneListEducation));
        //**************************************************************************************************

        //Добавляем секцию учебных заведений в резюме
        RESUME2.getSections().put(SectionType.EDUCATION, new ExperienceOrEducationSection(educationals));

        RESUME2.getSections().put(SectionType.ACHIEVEMENT, new AchievementOrQualificationsSection(achievements));
        RESUME2.getSections().put(SectionType.QUALIFICATIONS, new AchievementOrQualificationsSection(qualifications));*/
    }

    //Инициализация RESUME3
    static {
        RESUME3.getContacts().put(ContactType.PHONENUMBER, "+1(111) 111-1111");
        RESUME3.getContacts().put(ContactType.SKYPE, "hello.world");
        RESUME3.getContacts().put(ContactType.EMAIL, "helloworld@yandex.ru");
        /*RESUME3.getSections().put(SectionType.OBJECTIVE, new PersonalOrObjectiveSection("Отличный сотрудник"));
        RESUME3.getSections().put(SectionType.PERSONAL, new PersonalOrObjectiveSection("Очень хороший спец"));
        List<String> achievements = new ArrayList<>();
        achievements.add("В 2017 делал то");
        achievements.add("В 2016 делал сё");
        List<String> qualifications = new ArrayList<>();
        qualifications.add("Все умею все могу");


        //Добавляем организации
        List<Organization> organizations = new ArrayList<>();

        //Добавляем 1 организацию
        List<Position> positionOneList = new ArrayList<>();
        positionOneList.add(new Position("Заместитель директора", DateUtil.of(2018, Month.JANUARY),
                NOW, "Делал все подряд"));

        organizations.add(new Organization("Рога и копыта", "http://roga.ru/", positionOneList));
        //************************************************************************************************************

        //**************************************************************************************************
        //Добавляем секцию организаций в резюме
        RESUME3.getSections().put(SectionType.EXPERIENCE, new ExperienceOrEducationSection(organizations));

        //Добавляем учебные заведения

        List<Organization> educationals = new ArrayList<>();

        //Добавляем 1 учебное заведение
        List<Position> positionOneListEducation = new ArrayList<>();
        positionOneListEducation.add(new Position("Воспитанник детского сада",
                DateUtil.of(2013, Month.MARCH),
                DateUtil.of(2013, Month.MAY), null));

        educationals.add(new Organization("Детский сад \"Бармалейка\"", "https://malysh.ru", positionOneListEducation));
        //**************************************************************************************************

        //Добавляем секцию учебных заведений в резюме
        RESUME3.getSections().put(SectionType.EDUCATION, new ExperienceOrEducationSection(educationals));

        RESUME3.getSections().put(SectionType.ACHIEVEMENT, new AchievementOrQualificationsSection(achievements));
        RESUME3.getSections().put(SectionType.QUALIFICATIONS, new AchievementOrQualificationsSection(qualifications));*/
    }

    //Инициализация RESUME4
    static {
        RESUME4.getContacts().put(ContactType.PHONENUMBER, "+2(222) 222-2222");
        RESUME4.getContacts().put(ContactType.SKYPE, "preved.medved");
        RESUME4.getContacts().put(ContactType.EMAIL, "medved@preved.ru");
        RESUME4.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/prevedmedved");
        RESUME4.getContacts().put(ContactType.GITHUB, "https://github.com/prevedmedved");
        /*RESUME4.getSections().put(SectionType.OBJECTIVE, new PersonalOrObjectiveSection("Инженер-программист"));
        RESUME4.getSections().put(SectionType.PERSONAL, new PersonalOrObjectiveSection("Аналитический склад ума."));
        List<String> achievements = new ArrayList<>();
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix," +
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, " +
                "OpenCmis, Bonita, pgBouncer.");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных " +
                "шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");

        //Добавляем организации
        List<Organization> organizations = new ArrayList<>();

        //Добавляем 1 организацию
        List<Position> positionOneList = new ArrayList<>();
        positionOneList.add(new Position("Автор проекта", DateUtil.of(2013, Month.OCTOBER),
                NOW, "Создание, организация и проведение Java онлайн проектов и стажировок."));

        organizations.add(new Organization("Java Online Projects", "http://javaops.ru/", positionOneList));
        //************************************************************************************************************

        //Добавляем 2 организацию
        List<Position> positionFourList = new ArrayList<>();
        positionFourList.add(new Position("Ведущий программист", DateUtil.of(2010, Month.DECEMBER),
                DateUtil.of(2012, Month.APRIL), "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, " +
                "GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения " +
                "для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. " +
                "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));

        organizations.add(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", positionFourList));
        //***************************************************************************************************


        //Добавляем 3 организацию
        List<Position> positionSixList = new ArrayList<>();
        positionSixList.add(new Position("Разработчик ПО", DateUtil.of(2007, Month.MARCH),
                DateUtil.of(2008, Month.JUNE), "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                "частей кластерного J2EE приложения (OLAP, Data mining)."));

        organizations.add(new Organization("Enkata", "http://enkata.com//", positionSixList));
        //**************************************************************************************************

        //Добавляем 4 организацию
        List<Position> positionSevenList = new ArrayList<>();
        positionSevenList.add(new Position("Разработчик ПО", DateUtil.of(2005, Month.JANUARY),
                DateUtil.of(2007, Month.FEBRUARY), "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на " +
                "мобильной IN платформе Siemens @vantage (Java, Unix)."));

        organizations.add(new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html", positionSevenList));
        //**************************************************************************************************

        //Добавляем секцию организаций в резюме
        RESUME4.getSections().put(SectionType.EXPERIENCE, new ExperienceOrEducationSection(organizations));

        //Добавляем учебные заведения

        List<Organization> educationals = new ArrayList<>();

        //Добавляем 1 учебное заведение
        List<Position> positionOneListEducation = new ArrayList<>();
        positionOneListEducation.add(new Position("\"Functional Programming Principles in Scala\" by Martin Odersky",
                DateUtil.of(2013, Month.MARCH),
                DateUtil.of(2013, Month.MAY), null));

        educationals.add(new Organization("Coursera", "https://www.coursera.org/course/progfun", positionOneListEducation));
        //**************************************************************************************************

        //Добавляем 2 учебное заведение
        List<Position> positionTwoListEducation = new ArrayList<>();
        positionTwoListEducation.add(new Position("\"Объектно-ориентированный анализ ИС. " +
                "Концептуальное моделирование на UML.\"",
                DateUtil.of(2011, Month.MARCH),
                DateUtil.of(2011, Month.APRIL), null));

        educationals.add(new Organization("Coursera", "https://www.coursera.org/course/progfun", positionTwoListEducation));
        //**************************************************************************************************

        //Добавляем 3 учебное заведение
        List<Position> positionThreeListEducation = new ArrayList<>();
        positionThreeListEducation.add(new Position("\"Объектно-ориентированный анализ ИС. " +
                "Концептуальное моделирование на UML.\"",
                DateUtil.of(2011, Month.MARCH),
                DateUtil.of(2011, Month.APRIL), null));

        educationals.add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                positionThreeListEducation));
        //**************************************************************************************************

        //Добавляем 4 учебное заведение
        List<Position> positionFourListEducation = new ArrayList<>();
        positionFourListEducation.add(new Position("3 месяца обучения мобильным IN сетям (Берлин)",
                DateUtil.of(2005, Month.JANUARY),
                DateUtil.of(2005, Month.APRIL), null));

        educationals.add(new Organization("Siemens AG", "http://www.siemens.ru/",
                positionFourListEducation));
        //**************************************************************************************************

        //Добавляем 5 учебное заведение
        List<Position> positionFiveListEducation = new ArrayList<>();
        positionFiveListEducation.add(new Position("6 месяцев обучения цифровым телефонным сетям (Москва)",
                DateUtil.of(1997, Month.SEPTEMBER),
                DateUtil.of(1998, Month.MARCH), null));

        educationals.add(new Organization("Alcatel", "http://www.alcatel.ru/",
                positionFiveListEducation));
        //**************************************************************************************************

        //Добавляем 6 учебное заведение
        List<Position> positionSixListEducation = new ArrayList<>();
        positionSixListEducation.add(new Position("Инженер (программист Fortran, C)",
                DateUtil.of(1987, Month.SEPTEMBER),
                DateUtil.of(1993, Month.JULY), null));

        educationals.add(new Organization("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                positionSixListEducation));
        //**************************************************************************************************

        //Добавляем секцию учебных заведений в резюме
        RESUME4.getSections().put(SectionType.EDUCATION, new ExperienceOrEducationSection(educationals));

        RESUME4.getSections().put(SectionType.ACHIEVEMENT, new AchievementOrQualificationsSection(achievements));
        RESUME4.getSections().put(SectionType.QUALIFICATIONS, new AchievementOrQualificationsSection(qualifications));*/
    }
}






