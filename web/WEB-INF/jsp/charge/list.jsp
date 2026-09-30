<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>收费管理 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-admin.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="charge"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-admin.jsp" %>
        </div>

        <div class="content">
            <div class="page-header">
                <h2>收费管理</h2>
                <a href="${pageContext.request.contextPath}/charge/add" class="btn-add">+ 添加收费</a>
            </div>

            <div class="search-container">
                <form method="get" action="${pageContext.request.contextPath}/charge/list" class="search-row">
                    <div>
                        <label>病人姓名</label>
                        <input type="text" name="keyword" value="${param.keyword}" placeholder="输入病人姓名">
                    </div>
                    <div>
                        <button type="submit">搜索</button>
                        <a href="${pageContext.request.contextPath}/charge/list" class="clear-link">清除</a>
                    </div>
                </form>
            </div>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>病人姓名</th>
                            <th>科别</th>
                            <th>收费项目</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>金额</th>
                            <th>收费日期</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${charges}" var="c">
                            <tr>
                                <td>${c.chargeId}</td>
                                <td>${c.patientName}</td>
                                <td>${c.department}</td>
                                <td>${c.chargeItem}</td>
                                <td>${c.unitPrice}</td>
                                <td>${c.quantity}</td>
                                <td>${c.amount}</td>
                                <td>${c.chargeDate}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/charge/edit?id=${c.chargeId}" class="btn-edit">编辑</a>
                                    <a href="${pageContext.request.contextPath}/charge/delete?id=${c.chargeId}" class="btn-delete" onclick="return confirm('确定删除吗？')">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty charges}">
                            <tr><td colspan="9" style="text-align:center; padding:30px; color:#999;">暂无数据</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
