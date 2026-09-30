<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>病人管理 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:choose>
    <c:when test="${isDoctor}">
        <%@ include file="/WEB-INF/jsp/includes/header-doctor.jsp" %>
    </c:when>
    <c:otherwise>
        <%@ include file="/WEB-INF/jsp/includes/header-admin.jsp" %>
    </c:otherwise>
</c:choose>
    <div class="container">
        <!-- 侧边栏：管理员和医生看到不同的菜单 -->
        <div class="sidebar">
            <c:choose>
                <c:when test="${isDoctor}">
                    <c:set var="activeMenu" value="myPatients"/>
                    <%@ include file="/WEB-INF/jsp/includes/sidebar-doctor.jsp" %>
                </c:when>
                <c:otherwise>
                    <c:set var="activeMenu" value="patient"/>
                    <%@ include file="/WEB-INF/jsp/includes/sidebar-admin.jsp" %>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="content">
            <div class="page-header">
                <h2>${isDoctor ? '我的病人' : '病人管理'}</h2>
                <c:if test="${not isDoctor}">
                    <a href="${pageContext.request.contextPath}/patient/add" class="btn-add">+ 添加病人</a>
                </c:if>
            </div>

            <!-- 搜索筛选：仅管理员可见 -->
            <c:if test="${not isDoctor}">
            <div class="search-container">
                <form method="get" action="${pageContext.request.contextPath}/patient/list" class="search-row">
                    <div>
                        <label>姓名</label>
                        <input type="text" name="keyword" value="${param.keyword}" placeholder="输入病人姓名">
                    </div>
                    <div>
                        <label>科室</label>
                        <select name="department">
                            <option value="">全部</option>
                            <c:forEach items="${departments}" var="dept">
                                <option value="${dept}" ${param.department == dept ? "selected" : ""}>${dept}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label>主治医生</label>
                        <select name="doctorId">
                            <option value="">全部</option>
                            <c:forEach items="${doctors}" var="doc">
                                <option value="${doc.doctorId}" ${param.doctorId == doc.doctorId ? "selected" : ""}>${doc.doctorName}（${doc.department}）</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <button type="submit">搜索</button>
                        <a href="${pageContext.request.contextPath}/patient/list" class="clear-link">清除筛选</a>
                    </div>
                </form>
            </div>
            </c:if>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>姓名</th>
                            <th>科室</th>
                            <th>床位</th>
                            <th>性别</th>
                            <th>年龄</th>
                            <th>病症</th>
                            <th>主治医生</th>
                            <th>入院日期</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${patients}" var="patient">
                            <tr>
                                <td>${patient.patientId}</td>
                                <td>${patient.patientName}</td>
                                <td>${patient.department}</td>
                                <td>${patient.bedNoStr}</td>
                                <td>${patient.gender}</td>
                                <td>${patient.age}</td>
                                <td>${patient.illness}</td>
                                <td>${patient.attendingDoctorName}</td>
                                <td>${patient.admissionDate}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${isDoctor}">
                                            <a href="${pageContext.request.contextPath}/doctor/patient/detail?id=${patient.patientId}" class="btn-view">查看</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/patient/edit?id=${patient.patientId}" class="btn-edit">编辑</a>
                                            <a href="${pageContext.request.contextPath}/patient/delete?id=${patient.patientId}" class="btn-delete" onclick="return confirm('确定删除吗？')">删除</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty patients}">
                            <tr>
                                <td colspan="10" style="text-align: center; padding: 30px; color: #999;">暂无数据</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
