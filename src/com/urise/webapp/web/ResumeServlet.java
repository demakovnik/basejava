package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        boolean isEmptyUuid = uuid.isEmpty();
        if (isEmptyUuid) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.putContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            AbstractSection value = null;
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    String text = request.getParameter(type.name());
                    if (text.isEmpty()) {
                        r.getSections().remove(type);
                        break;
                    }
                    value = new PersonalOrObjectiveSection(text);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String strings = request.getParameter(type.name()).trim();
                    if (strings.isEmpty()) {
                        r.getSections().remove(type);
                        break;
                    }
                    List<String> list = Arrays.asList(strings.split("\n"));
                    value = new AchievementOrQualificationsSection(list);
                    if (value.isEmpty()) {
                        r.getSections().remove(type);
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Organization> organizations = new ArrayList<>();
                    String[] orgTitles = request.getParameterValues(type.name());
                    if (orgTitles == null || orgTitles[0].isEmpty()) {
                        r.getSections().remove(type);
                        break;
                    }
                    String[] orgUrls = request.getParameterValues(type.name() + "_url");
                    for (int i = 0; i < orgTitles.length; i++) {
                        if (orgTitles[i].trim().length() == 0) {
                            continue;
                        }
                        List<Position> positions = new ArrayList<>();
                        Link link = new Link(orgTitles[i], orgUrls[i]);
                        String[] posTitles = request.getParameterValues(type.name() + "_posTitle_" + i);
                        String[] posDescriptions = request.getParameterValues(type.name() + "_posDesc_" + i);
                        String[] startTimes = request.getParameterValues(type.name() + "_posStartTime_" + i);
                        String[] endTimes = request.getParameterValues(type.name() + "_posEndTime_" + i);
                        for (int j = 0; j < posTitles.length; j++) {
                            String positionTitle = posTitles[j];
                            if (positionTitle.trim().length() == 0) {
                                continue;
                            }
                            String positionDescription = posDescriptions[j];
                            LocalDate startTime = DateUtil.stringToLocalDate(startTimes[j]);
                            LocalDate endTime = DateUtil.stringToLocalDate(endTimes[j]);
                            positions.add(new Position(positionTitle, startTime, endTime, positionDescription));
                        }
                        if (!positions.isEmpty()) {
                            organizations.add(new Organization(link, positions));
                        } else organizations.clear();
                    }
                    value = new ExperienceOrEducationSection(organizations);
                    break;
            }
            if (value != null) {
                r.putSection(type, value);
            }
        }
        if (!isEmptyUuid) {
            storage.update(r);
        } else {
            storage.save(r);
        }
        response.sendRedirect("resume");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                addDataToResume(r);
                break;
            case "add":
                r = new Resume("","");
                addDataToResume(r);
                break;
            default:
                throw new IllegalStateException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private void addDataToResume(Resume resume) {
        for (SectionType type : SectionType.values()) {
            AbstractSection value = resume.getSection(type);
            if (value == null) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        value = new PersonalOrObjectiveSection("");
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        value = new AchievementOrQualificationsSection(new ArrayList<>());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        Position emptyPos = new Position("", DateUtil.NOW, DateUtil.NOW, "");
                        List<Position> positionList = new ArrayList<>();
                        positionList.add(emptyPos);
                        Organization emptyOrg = new Organization("", "", positionList);
                        List<Organization> organizations = new ArrayList<>();
                        organizations.add(emptyOrg);
                        value = new ExperienceOrEducationSection(organizations);
                }
                resume.putSection(type, value);
            }

        }
    }
}