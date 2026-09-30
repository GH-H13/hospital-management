<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Double totalAmount = (Double) request.getAttribute("totalAmount");
    if (totalAmount == null) totalAmount = 0.0;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>收费记录 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        function onChargeItemChange() {
            var select = document.getElementById("chargeItemId");
            var option = select.options[select.selectedIndex];
            var price = option.getAttribute("data-price");
            var name = option.getAttribute("data-name");
            var unitPriceInput = document.getElementById("unitPriceInput");
            if (price) {
                unitPriceInput.value = price;
                document.getElementById("chargeItemHidden").value = name;
                // 单价为0时（如药品费），允许手动输入
                if (parseFloat(price) === 0) {
                    unitPriceInput.readOnly = false;
                    unitPriceInput.style.background = "white";
                    unitPriceInput.placeholder = "请输入药品价格";
                } else {
                    unitPriceInput.readOnly = true;
                    unitPriceInput.style.background = "#f8f9fa";
                    unitPriceInput.placeholder = "自动填充";
                }
            } else {
                unitPriceInput.value = "";
                document.getElementById("chargeItemHidden").value = "";
            }
        }
    </script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-doctor.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="doctorCharges"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-doctor.jsp" %>
        </div>

    <div class="content">
        <%-- 显示消息 --%>
        <%
            String chargeSuccess = (String) session.getAttribute("chargeSuccess");
            String chargeError = (String) session.getAttribute("chargeError");
            if (chargeSuccess != null) {
                session.removeAttribute("chargeSuccess");
        %>
            <div style="background: #d4edda; color: #155724; padding: 15px; border-radius: 5px; margin-bottom: 20px;">
                <%= chargeSuccess %>
            </div>
        <%
            }
            if (chargeError != null) {
                session.removeAttribute("chargeError");
        %>
            <div style="background: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; margin-bottom: 20px;">
                <%= chargeError %>
            </div>
        <%
            }
        %>

        <div class="stats-grid">
            <div class="stat-card">
                <h3>¥<%= String.format("%.2f", totalAmount) %></h3>
                <p>总费用</p>
            </div>
        </div>

        <%-- 添加收费记录表单 --%>
        <div class="table-container" style="margin-bottom: 30px;">
            <h3>添加收费记录</h3>
            <form action="${pageContext.request.contextPath}/doctor/charges" method="post" style="display: grid; grid-template-columns: 1fr 1fr 1fr 1fr; gap: 15px; align-items: end;">
                <div class="form-group">
                    <label style="display: block; margin-bottom: 5px; color: #666; font-weight: bold;">选择病人</label>
                    <select name="patientId" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 5px;">
                        <option value="">-- 请选择病人 --</option>
                        <c:forEach items="${patients}" var="patient">
                            <option value="${patient.patientId}">${patient.patientName}（${patient.department}）</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label style="display: block; margin-bottom: 5px; color: #666; font-weight: bold;">收费项目</label>
                    <select name="itemId" id="chargeItemId" required onchange="onChargeItemChange()" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 5px;">
                        <option value="">-- 请选择项目 --</option>
                        <c:forEach items="${chargeItems}" var="ci">
                            <option value="${ci.itemId}" data-price="${ci.defaultPrice}" data-name="${ci.itemName}">${ci.itemName}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="chargeItem" id="chargeItemHidden" value="">
                </div>
                <div class="form-group">
                    <label style="display: block; margin-bottom: 5px; color: #666; font-weight: bold;">单价（元）</label>
                    <input type="number" name="unitPrice" id="unitPriceInput" required placeholder="0.00" step="0.01" min="0" readonly style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 5px; background:#f8f9fa;">
                </div>
                <div class="form-group">
                    <label style="display: block; margin-bottom: 5px; color: #666; font-weight: bold;">数量</label>
                    <input type="number" name="quantity" required placeholder="1" min="1" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 5px;">
                </div>
                <div class="form-group" style="grid-column: 1 / -1;">
                    <button type="submit" style="background: #3498db; color: white; padding: 10px 30px; border: none; border-radius: 5px; cursor: pointer; font-size: 15px;">添加收费</button>
                </div>
            </form>
        </div>

        <div class="table-container">
            <h3>病人收费记录</h3>
            <table>
                    <thead>
                        <tr>
                            <th>收费ID</th>
                            <th>病人姓名</th>
                            <th>科室</th>
                            <th>收费项目</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>金额</th>
                            <th>收费日期</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${charges}" var="charge">
                            <tr>
                                <td>${charge.chargeId}</td>
                                <td>${charge.patientName}</td>
                                <td>${charge.department}</td>
                                <td>${charge.chargeItem}</td>
                                <td>¥${charge.unitPrice}</td>
                                <td>${charge.quantity}</td>
                                <td>¥${charge.amount}</td>
                                <td>${charge.chargeDate}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty charges}">
                            <tr><td colspan="8" style="text-align:center; padding:30px; color:#999;">暂无收费记录</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
