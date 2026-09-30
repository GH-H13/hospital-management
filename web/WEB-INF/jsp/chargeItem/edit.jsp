<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    com.hospital.entity.ChargeItem item = (com.hospital.entity.ChargeItem) request.getAttribute("chargeItem");
    if (item == null) {
        response.sendRedirect(request.getContextPath() + "/chargeItem/list");
        return;
    }
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>编辑收费项目 - 医院住院管理系统</title>
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
                <h2>编辑收费项目</h2>

                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/chargeItem/update" method="post">
                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>">

                    <div class="form-group">
                        <label for="itemName">项目名称</label>
                        <input type="text" id="itemName" name="itemName" value="<%= item.getItemName() %>" required>
                    </div>

                    <div class="form-group">
                        <label for="itemType">项目类型</label>
                        <select id="itemType" name="itemType" required>
                            <option value="">请选择类型</option>
                            <option value="诊疗" ${chargeItem.itemType eq '诊疗' ? 'selected' : ''}>诊疗</option>
                            <option value="护理" ${chargeItem.itemType eq '护理' ? 'selected' : ''}>护理</option>
                            <option value="检查" ${chargeItem.itemType eq '检查' ? 'selected' : ''}>检查</option>
                            <option value="化验" ${chargeItem.itemType eq '化验' ? 'selected' : ''}>化验</option>
                            <option value="手术" ${chargeItem.itemType eq '手术' ? 'selected' : ''}>手术</option>
                            <option value="药品" ${chargeItem.itemType eq '药品' ? 'selected' : ''}>药品</option>
                            <option value="治疗" ${chargeItem.itemType eq '治疗' ? 'selected' : ''}>治疗</option>
                            <option value="材料" ${chargeItem.itemType eq '材料' ? 'selected' : ''}>材料</option>
                            <option value="住宿" ${chargeItem.itemType eq '住宿' ? 'selected' : ''}>住宿</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="defaultPrice">默认单价（元）</label>
                        <input type="number" id="defaultPrice" name="defaultPrice" step="0.01" min="0" value="<%= item.getDefaultPrice() %>" required>
                    </div>

                    <button type="submit" class="btn-submit">更新</button>
                    <a href="${pageContext.request.contextPath}/chargeItem/list" class="btn-cancel">取消</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
