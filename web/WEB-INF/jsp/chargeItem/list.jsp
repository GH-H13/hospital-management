<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>收费项目管理 - 医院住院管理系统</title>
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
            <div class="page-header">
                <h2>收费项目管理</h2>
                <a href="${pageContext.request.contextPath}/chargeItem/add" class="btn-add">+ 添加收费项目</a>
            </div>

            <c:if test="${not empty error}">
                <div style="background:#f8d7da; color:#721c24; padding:10px; border-radius:5px; margin-bottom:15px;">${error}</div>
            </c:if>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>项目名称</th>
                            <th>项目类型</th>
                            <th>默认单价（元）</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${chargeItems}" var="item">
                            <tr>
                                <td>${item.itemId}</td>
                                <td>${item.itemName}</td>
                                <td>${item.itemType}</td>
                                <td>¥${item.defaultPrice}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/chargeItem/edit?itemId=${item.itemId}" class="btn-edit">编辑</a>
                                    <a href="javascript:void(0)" onclick="confirmDelete(${item.itemId}, '${item.itemName}')" class="btn-delete">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty chargeItems}">
                            <tr><td colspan="5" class="no-data">暂无收费项目数据</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        function confirmDelete(itemId, itemName) {
            if (confirm("确定要删除收费项目【" + itemName + "】吗？")) {
                window.location.href = "${pageContext.request.contextPath}/chargeItem/delete?itemId=" + itemId;
            }
        }
    </script>
</body>
</html>
