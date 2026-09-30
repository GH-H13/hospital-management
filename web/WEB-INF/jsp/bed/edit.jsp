<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>编辑床位 - 医院住院管理系统</title>
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
            <div class="form-container">
                <h2>编辑床位信息</h2>

                <form action="${pageContext.request.contextPath}/bed/edit" method="post">
                    <input type="hidden" name="bedId" value="${bed.bedId}">

                    <div class="form-group">
                        <label for="department">科室</label>
                        <select id="department" name="department" required>
                            <option value="">请选择科室</option>
                            <c:forEach items="${departments}" var="dept">
                                <option value="${dept}" ${bed.department eq dept ? 'selected' : ''}>${dept}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="bedNo">床位号</label>
                        <input type="text" id="bedNo" name="bedNo" value="${bed.bedNo}" required>
                    </div>

                    <div class="form-group">
                        <label for="bedFee">床位费</label>
                        <input type="number" id="bedFee" name="bedFee" step="0.01" min="0" value="${bed.bedFee}" required>
                    </div>

                    <div class="form-group">
                        <label for="useStatus">状态</label>
                        <select id="useStatus" name="useStatus" required>
                            <option value="">请选择</option>
                            <option value="空闲" ${bed.useStatus eq '空闲' ? 'selected' : ''}>空闲</option>
                            <option value="占用" ${bed.useStatus eq '占用' ? 'selected' : ''}>占用</option>
                        </select>
                    </div>

                    <button type="submit" class="btn-submit">更新</button>
                    <a href="${pageContext.request.contextPath}/bed/list" class="btn-cancel">取消</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
