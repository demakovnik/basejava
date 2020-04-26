<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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

            <c:choose>
            <c:when test="${type.name().equals('PERSONAL') || type.name().equals('OBJECTIVE')}">
        <h3>${type.title}</h3>
        <textarea name="${type.name()}" cols=100
                  rows=4><%=((PersonalOrObjectiveSection) section).getText()%></textarea>
        </c:when>

        <c:when test="${type.name().equals('ACHIEVEMENT') || type.name().equals('QUALIFICATIONS')}">
            <h3>${type.title}</h3>
            <textarea name="${type.name()}" cols=100
                      rows=4><%=String.join("\n", ((AchievementOrQualificationsSection) section).
                    getListOfAchievementsOrQualifications())%>
                </textarea>

        </c:when>
        <c:when test="${type.name().equals('EXPERIENCE') || type.name().equals('EDUCATION')}">
            <c:if test="<%=!((ExperienceOrEducationSection) section).getListOfExperienceOrEducation().isEmpty()%>">
                <h3>${type.title}</h3>
                <c:forEach var="organization"
                           items="<%=((ExperienceOrEducationSection) section).getListOfExperienceOrEducation()%>"
                           varStatus="orgCounter">
                    <jsp:useBean id="organization" type="com.urise.webapp.model.Organization"/>
                    <p>Наименование организации:
                        <textarea name="${type.name()}" cols=50 rows=3>${organization.link.title}</textarea>
                    <p>Веб-сайт организации:
                        <input type="text" name=${type.name()}_url size=50
                               value="${organization.link.url}"></p>
                    <c:forEach var="position"
                               items="<%=organization.getPositionList()%>" varStatus="posCounter">
                        <p>
                        <h4>Позиция ${posCounter.index+1}</h4>
                        <p>Должность:
                            <input type="text" name=${type.name()}_posTitle_${orgCounter.index} size=50
                                   value="${position.title.trim()}"></p>
                        <p>Описание:
                            <textarea name="${type.name()}_posDesc_${orgCounter.index}" cols=50
                                      rows=3>${position.description.trim()}</textarea>
                        <h5>Период работы:</h5>
                        <p>с:
                            <input type="text" name=${type.name()}_posStartTime_${orgCounter.index} size=15
                                   value="${DateUtil.localDateToText(position.startTime)}"><br/>
                            по:
                            <input type="text" name=${type.name()}_posEndTime_${orgCounter.index} size=15
                                   value="${DateUtil.localDateToText(position.endTime)}"><br/>
                        </p>
                        </p>
                    </c:forEach>
                </c:forEach>
            </c:if>

        </c:when>
        </c:choose>
        </c:forEach>
        </p>

        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>