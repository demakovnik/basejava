<%@ page import="com.urise.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <input type="text" name="fullName" size=50 value="${resume.fullName}">
        </dl>
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
        </dl>
        </c:forEach>
        </p>
        <hr>
        <p>
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(type)}"/>
                <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
        <h3>${type.title}</h3>
        <c:choose>
            <c:when test="${type.name().equals('PERSONAL') || type.name().equals('OBJECTIVE')}">
                <textarea name="${type.name()}" cols=100
                          rows=4><%=((PersonalOrObjectiveSection) section).getText()%></textarea>
            </c:when>

            <c:when test="${type.name().equals('ACHIEVEMENT') || type.name().equals('QUALIFICATIONS')}">
                <textarea name="${type.name()}" cols=100
                          rows=4><%=String.join("\n", ((AchievementOrQualificationsSection) section).
                        getListOfAchievementsOrQualifications())%>
                </textarea>
            </c:when>
            <c:when test="${type.name().equals('EXPERIENCE') || type.name().equals('EDUCATION')}">
                <c:forEach var="organization"
                           items="<%=((ExperienceOrEducationSection) section).getListOfExperienceOrEducation()%>"
                           varStatus="counter">
                    <jsp:useBean id="organization" type="com.urise.webapp.model.Organization"/>
                    <p>Наименование организации ${counter.index+1}
                    <input type="text" name=${type} size=30 value="${organization.link.title}"></p>
                </c:forEach>
            </c:when>
        </c:choose>
        </c:forEach>
        </p>

        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>