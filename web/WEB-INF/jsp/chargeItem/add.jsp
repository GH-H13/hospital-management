<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>添加收费项目 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-admin.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="chargeItem"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-admin.jsp" %>
        </div>

        <div class="content">
            <div class="form-container">
                <h2>添加收费项目</h2>

                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/chargeItem/save" method="post">
                    <div class="form-group">
                        <label for="itemName">项目名称</label>
                        <input type="text" id="itemName" name="itemName" required placeholder="如：诊查费">
                    </div>

                    <div class="form-group">
                        <label for="itemType">项目类型</label>
                        <select id="itemType" name="itemType" required>
                            <option value="">请选择类型</option>
                            <option value="诊疗">诊疗</option>
                            <option value="护理">护理</option>
                            <option value="检查">检查</option>
                            <option value="化验">化验</option>
                            <option value="手术">手术</option>
                            <option value="药品">药品</option>
                            <option value="治疗">治疗</option>
                            <option value="材料">材料</option>
                            <option value="住宿">住宿</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="defaultPrice">默认单价（元）</label>
                        <input type="number" id="defaultPrice" name="defaultPrice" step="0.01" min="0" required placeholder="请输入默认单价">
                    </div>

                    <button type="submit" class="btn-submit">提交</button>
                    <a href="${pageContext.request.contextPath}/chargeItem/list" class="btn-cancel">取消</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
