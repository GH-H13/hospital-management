<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%-- 医生头部 - 包含欢迎语、退出 --%>
<%
    com.hospital.entity.Doctor doctor = (com.hospital.entity.Doctor) session.getAttribute("doctor");
%>
<div class="header">
    <h1>医院住院管理系统 - 医生工作台</h1>
    <div>
        <span>欢迎，<%= doctor.getDoctorName() %>（<%= doctor.getDepartment() %>）</span>
        <a href="${pageContext.request.contextPath}/doctor/logout" style="color:white; margin-left:20px;">退出</a>
    </div>
</div>
