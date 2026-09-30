<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>医生管理 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-admin.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="doctor"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-admin.jsp" %>
        </div>

        <div class="content">
            <div class="page-header">
                <h2>医生管理</h2>
                <a href="${pageContext.request.contextPath}/doctor/add" class="btn-add">+ 添加医生</a>
            </div>

            <div class="search-container">
                <form method="get" action="${pageContext.request.contextPath}/doctor/list" class="search-row">
                    <div>
                        <label>医生姓名</label>
                        <input type="text" name="keyword" value="${param.keyword}" placeholder="输入医生姓名">
                    </div>
                    <div>
                        <button type="submit">搜索</button>
                        <a href="${pageContext.request.contextPath}/doctor/list" class="clear-link">清除</a>
                    </div>
                </form>
            </div>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>职称</th>
                            <th>职务</th>
                            <th>科室</th>
                            <th>出生日期</th>
                            <th>工作日期</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${doctors}" var="doc">
                            <tr>
                                <td>${doc.doctorId}</td>
                                <td>${doc.doctorName}</td>
                                <td>${doc.gender}</td>
                                <td>${doc.title}</td>
                                <td>${doc.duty}</td>
                                <td>${doc.department}</td>
                                <td>${doc.birthDate}</td>
                                <td>${doc.workDate}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/doctor/edit?id=${doc.doctorId}" class="btn-edit">编辑</a>
                                    <a href="${pageContext.request.contextPath}/doctor/delete?id=${doc.doctorId}" class="btn-delete" onclick="return confirm('确定删除吗？')">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty doctors}">
                            <tr><td colspan="9" style="text-align:center; padding:30px; color:#999;">暂无数据</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
