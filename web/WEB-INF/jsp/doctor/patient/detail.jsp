<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    com.hospital.entity.Patient patient =
            (com.hospital.entity.Patient) request.getAttribute("patient");
    if (patient == null) {
        response.sendRedirect(request.getContextPath() + "/doctor/main");
        return;
    }
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>病人详情 - 医院住院管理系统</title>
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
            <a href="${pageContext.request.contextPath}/doctor/main" class="back-link">← 返回列表</a>

            <div class="patient-card">
                <h3>病人信息：<%= patient.getPatientName() %></h3>
                <div class="info-grid">
                    <div class="info-item">
                        <div class="info-label">姓名</div>
                        <div class="info-value"><%= patient.getPatientName() %></div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">性别</div>
                        <div class="info-value"><%= patient.getGender() != null ? patient.getGender() : "-" %></div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">年龄</div>
                        <div class="info-value"><%= patient.getAge() != null ? patient.getAge() : "-" %></div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">科室</div>
                        <div class="info-value"><%= patient.getDepartment() != null ? patient.getDepartment() : "-" %></div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">床位</div>
                        <div class="info-value"><%= patient.getBedNoStr() != null ? patient.getBedNoStr() : "-" %></div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">病症</div>
                        <div class="info-value"><%= patient.getIllness() != null ? patient.getIllness() : "-" %></div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">入院日期</div>
                        <div class="info-value"><%= patient.getAdmissionDate() != null ? patient.getAdmissionDate() : "-" %></div>
                    </div>
                </div>
            </div>

            <div class="table-container">
                <h3>收费记录</h3>
                <table>
                    <thead>
                        <tr>
                            <th>收费项目</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>金额</th>
                            <th>收费日期</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${charges}" var="c">
                            <tr>
                                <td>${c.chargeItem}</td>
                                <td>${c.unitPrice}</td>
                                <td>${c.quantity}</td>
                                <td>${c.amount}</td>
                                <td>${c.chargeDate}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty charges}">
                            <tr>
                                <td colspan="5" style="text-align:center; padding:30px; color:#999;">暂无收费记录</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
