<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Integer patientCount = (Integer) request.getAttribute("patientCount");
    if (patientCount == null) patientCount = 0;

    Double totalAmount = (Double) request.getAttribute("totalAmount");
    if (totalAmount == null) totalAmount = 0.0;

    Integer totalCharges = (Integer) request.getAttribute("totalCharges");
    if (totalCharges == null) totalCharges = 0;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>医生工作台 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-doctor.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="myPatients"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-doctor.jsp" %>
        </div>

        <div class="content">
            <div class="welcome">
                <h2>欢迎回来，<%= doctor.getDoctorName() %>医生！</h2>
                <p>科室：<%= doctor.getDepartment() %> | 职称：<%= doctor.getTitle() != null ? doctor.getTitle() : "" %></p>
            </div>

            <div class="stats-grid">
                <div class="stat-card">
                    <h3><%= patientCount %></h3>
                    <p>我的病人</p>
                </div>
                <div class="stat-card">
                    <h3><%= totalCharges %></h3>
                    <p>收费条目</p>
                </div>
                <div class="stat-card">
                    <h3>¥<%= String.format("%.2f", totalAmount) %></h3>
                    <p>总费用</p>
                </div>
            </div>

            <div class="table-container">
                <h3>我的病人列表</h3>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>年龄</th>
                            <th>病症</th>
                            <th>入院日期</th>
                            <th>床位</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${patients}" var="patient">
                            <tr>
                                <td>${patient.patientId}</td>
                                <td>${patient.patientName}</td>
                                <td>${patient.gender}</td>
                                <td>${patient.age}</td>
                                <td>${patient.illness}</td>
                                <td>${patient.admissionDate}</td>
                                <td>${patient.bedNoStr}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/doctor/patient/detail?id=${patient.patientId}" class="btn-view">查看</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty patients}">
                            <tr><td colspan="8" style="text-align:center; padding:30px; color:#999;">暂无病人</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
