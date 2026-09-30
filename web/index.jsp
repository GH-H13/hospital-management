<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 自动跳转到登录页面
    response.sendRedirect(request.getContextPath() + "/login");
%>
