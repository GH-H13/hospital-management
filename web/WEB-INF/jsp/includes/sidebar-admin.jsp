<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%-- 管理员侧边栏 - 使用 activeMenu 变量控制高亮 --%>
<a href="${pageContext.request.contextPath}/main" class="menu-item ${activeMenu eq 'main' ? 'active' : ''}">首页</a>
<a href="${pageContext.request.contextPath}/patient/list" class="menu-item ${activeMenu eq 'patient' ? 'active' : ''}">病人管理</a>
<a href="${pageContext.request.contextPath}/doctor/list" class="menu-item ${activeMenu eq 'doctor' ? 'active' : ''}">医生管理</a>
<a href="${pageContext.request.contextPath}/bed/list" class="menu-item ${activeMenu eq 'bed' ? 'active' : ''}">床位管理</a>
<a href="${pageContext.request.contextPath}/charge/list" class="menu-item ${activeMenu eq 'charge' ? 'active' : ''}">收费管理</a>
<a href="${pageContext.request.contextPath}/chargeItem/list" class="menu-item ${activeMenu eq 'chargeItem' ? 'active' : ''}">收费项目管理</a>
<a href="${pageContext.request.contextPath}/statistics" class="menu-item ${activeMenu eq 'statistics' ? 'active' : ''}">统计报表</a>
