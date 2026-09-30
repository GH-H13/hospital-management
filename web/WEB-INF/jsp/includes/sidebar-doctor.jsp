<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%-- 医生侧边栏 - 使用 activeMenu 变量控制高亮 --%>
<a href="${pageContext.request.contextPath}/doctor/main" class="menu-item ${activeMenu eq 'myPatients' ? 'active' : ''}">我的病人</a>
<a href="${pageContext.request.contextPath}/doctor/charges" class="menu-item ${activeMenu eq 'doctorCharges' ? 'active' : ''}">收费记录</a>
<a href="${pageContext.request.contextPath}/doctor/profile" class="menu-item ${activeMenu eq 'doctorProfile' ? 'active' : ''}">个人信息</a>
