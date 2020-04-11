package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        Resume resume;
        List<Resume> list;
        response.getWriter().write("<table border=\"1\"><tr><td>№</td><td>uuid</td><td>Full Name</td></tr>");
        if (uuid == null) {
            list = storage.getAllSorted();
            int count = 0;
            for (Resume r: list) {
                response.getWriter().write("<tr><td>" + (++count) + ".</td><td>" + r.getUuid() + "</td><td>" + r.getFullName() + "</td></tr>");
            }
            response.getWriter().write("</table>");
        } else {
            resume = storage.get(uuid);
            String fullName = resume.getFullName();
            response.getWriter().write("<tr><td>*</td><td>" + uuid + "</td><td>" + fullName + "</td></tr></table>");
        }
    }
}