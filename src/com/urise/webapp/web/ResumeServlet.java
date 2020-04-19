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
import java.util.*;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
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
                    value = new PersonalOrObjectiveSection(request.getParameter(type.name()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:

                    value = new AchievementOrQualificationsSection(Arrays.asList(request.getParameter(type.name()).trim().split("\n")));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Organization> organizations = Arrays.asList(request.getParameterMap().get(type.name()))
                            .stream().filter(s -> s.trim().length() >0)
                            .map((s -> new Organization(s,"",new ArrayList<Position>()))).collect(Collectors.toList());
                    value = new ExperienceOrEducationSection(organizations);
                    break;
            }
            r.getSections().remove(type);
            if (value != null) {
                r.putSection(type, value);
            }
        }
        storage.update(r);
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
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection value = r.getSection(type);
                    if (value == null) {
                        switch (type) {
                            case PERSONAL:
                            case OBJECTIVE:
                                value = new PersonalOrObjectiveSection("");
                                break;
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
                                value = new AchievementOrQualificationsSection(new ArrayList<String>());
                                break;
                            case EXPERIENCE:
                            case EDUCATION:
                                Position emptyPos = new Position("", DateUtil.NOW,DateUtil.NOW,"");
                                List<Position> positionList = new ArrayList<>();
                                positionList.add(emptyPos);
                                Organization emptyOrg = new Organization("","",positionList);
                                List<Organization> organizations = new ArrayList<>();
                                organizations.add(emptyOrg);
                                value = new ExperienceOrEducationSection(organizations);
                        }
                        r.putSection(type, value);
                    }
                }
                break;
            default:
                throw new IllegalStateException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}