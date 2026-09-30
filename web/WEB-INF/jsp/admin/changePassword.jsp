<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>修改密码 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-admin.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="main"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-admin.jsp" %>
        </div>

        <div class="content">
            <div class="form-container">
                <h2>修改密码</h2>
                <form method="post" action="${pageContext.request.contextPath}/admin/changePassword">
                    <div class="form-group">
                        <label>旧密码</label>
                        <input type="password" name="oldPassword" required>
                    </div>
                    <div class="form-group">
                        <label>新密码</label>
                        <input type="password" name="newPassword" required>
                    </div>
                    <div class="form-group">
                        <label>确认新密码</label>
                        <input type="password" name="confirmPassword" required>
                    </div>
                    <button type="submit" class="btn">修改密码</button>
                </form>
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="message">${message}</div>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
