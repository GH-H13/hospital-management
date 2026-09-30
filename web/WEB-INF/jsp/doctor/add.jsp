<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>添加医生 - 医院住院管理系统</title>
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
            <div class="form-container">
                <h2>添加医生</h2>
                
                <form action="${pageContext.request.contextPath}/doctor/add" method="post">
                    <div class="form-group">
                        <label for="doctorName">姓名</label>
                        <input type="text" id="doctorName" name="doctorName" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="gender">性别</label>
                        <select id="gender" name="gender" required>
                            <option value="">请选择</option>
                            <option value="男">男</option>
                            <option value="女">女</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="title">职称</label>
                        <select id="title" name="title" required>
                            <option value="">请选择</option>
                            <c:forEach items="${titles}" var="t">
                                <option value="${t}">${t}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="duty">职务</label>
                        <select id="duty" name="duty">
                            <option value="">无</option>
                            <c:forEach items="${duties}" var="d">
                                <c:if test="${d != ''}">
                                <option value="${d}">${d}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="department">科室</label>
                        <select id="department" name="department" required>
                            <option value="">请选择科室</option>
                            <c:forEach items="${departments}" var="dept">
                                <option value="${dept}">${dept}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="birthDate">出生日期</label>
                        <input type="date" id="birthDate" name="birthDate">
                    </div>
                    
                    <div class="form-group">
                        <label for="workDate">工作日期</label>
                        <input type="date" id="workDate" name="workDate">
                    </div>
                    
                    <div class="form-group">
                        <label for="password">密码</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    
                    <button type="submit" class="btn-submit">提交</button>
                    <a href="${pageContext.request.contextPath}/doctor/list" class="btn-cancel">取消</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
