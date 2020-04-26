<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>
    <p>
    <h3>Контакты</h3>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().getTitle() + ":" + contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <table>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <tr>
                <td colspan=2><h2>${type.title}</h2></td>
            </tr>

            <tr>
                <td colspan=2>
                    <c:if test="${type.name().equals('PERSONAL') || type.name().equals('OBJECTIVE')}">
                        ${section.getText()}
                    </c:if>
                </td>
            </tr>
            <tr>
                <td colspan=2>
                    <c:if test="${type.name().equals('ACHIEVEMENT') || type.name().equals('QUALIFICATIONS')}">
                        <ul>
                            <c:forEach var="string" items="${section.getListOfAchievementsOrQualifications()}">
                                <li>${string}</li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </td>
            </tr>

            <c:if test="${type.name().equals('EXPERIENCE')}">
                <c:forEach var="organization" items="${section.getListOfExperienceOrEducation()}">
                    <tr>
                        <jsp:useBean id="organization" type="com.urise.webapp.model.Organization"/>
                        <td>
                            <h4><c:choose>
                                <c:when test="<%=organization.getLink().getUrl().isEmpty()%>">
                                    <%=organization.getLink().getTitle()%>
                                </c:when>
                                <c:otherwise>
                                    <a href="${organization.link.url}"><%=organization.getLink().getTitle()%>
                                    </a>
                                </c:otherwise>
                            </c:choose></h4>
                        </td>
                    </tr>
                    <c:forEach var="position" items="${organization.positionList}">
                        <jsp:useBean id="position" type="com.urise.webapp.model.Position"/>
                        <tr>
                            <td>
                                <dl>
                                    <dd>
                                            ${position.title}
                                    </dd>
                                    <dd><%=DateUtil.localDateToText(position.
                                            getStartTime())

                                    %> -
                                        <%=DateUtil.localDateToText(position.
                                                getEndTime())
                                        %>
                                    </dd>
                                </dl>
                            </td>
                            <td>
                                    ${position.description}
                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </c:if>
            <c:if test="${type.name().equals('EDUCATION')}">
                <c:forEach var="organization" items="${section.getListOfExperienceOrEducation()}">
                    <tr>
                        <td>
                            <h4><c:choose>
                                <c:when test="${organization.link.url==null}">
                                    ${organization.link.title}
                                </c:when>
                                <c:otherwise>
                                    <a href="${organization.link.url}">${organization.link.title}
                                    </a>
                                </c:otherwise>
                            </c:choose></h4>
                        </td>
                    </tr>
                    <c:forEach var="position" items="${organization.positionList}">
                        <tr>
                            <td>
                                <dl>
                                    <dd>${position.description}</dd>
                                    <dd>${DateUtil.localDateToText(position.
                                            startTime)
                                            } -
                                            ${DateUtil.localDateToText(position.
                                                    endTime)
                                                    }</dd>
                                </dl>
                            </td>
                            <td>
                                    ${position.title}
                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </c:if>
        </c:forEach>
    </table>
</section>
</body>
</html>
