<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>床位管理 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-admin.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="bed"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-admin.jsp" %>
        </div>
        
        <div class="content">
            <div class="page-header">
                <h2>床位管理</h2>
                <a href="${pageContext.request.contextPath}/bed/add" class="btn-add">+ 添加床位</a>
            </div>
            
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>科室</th>
                            <th>床位号</th>
                            <th>床位费</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${beds}" var="bed">
                            <tr>
                                <td>${bed.bedId}</td>
                                <td>${bed.department}</td>
                                <td>${bed.bedNo}</td>
                                <td>¥${bed.bedFee}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${bed.useStatus == '空闲'}">
                                            <span class="status-free">${bed.useStatus}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-occupied">${bed.useStatus}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/bed/edit?id=${bed.bedId}" class="btn-edit">编辑</a>
                                    <a href="${pageContext.request.contextPath}/bed/delete?id=${bed.bedId}" class="btn-delete" onclick="return confirm('确定删除吗？')">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
