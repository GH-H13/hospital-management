<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%-- 管理员头部 - 包含欢迎语、修改密码、退出 --%>
<div class="header">
    <h1>医院住院管理系统</h1>
    <div>
        <span>欢迎，${sessionScope.adminName}</span>
        <a href="${pageContext.request.contextPath}/admin/changePassword" style="color:white; margin-left:20px;">修改密码</a>
        <a href="${pageContext.request.contextPath}/logout" style="color:white; margin-left:20px;">退出</a>
    </div>
</div>
