<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>添加收费 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        function onItemChange() {
            var select = document.getElementById("itemId");
            var selected = select.options[select.selectedIndex];
            var price = selected.getAttribute("data-price");
            if (price) {
                document.getElementById("unitPrice").value = price;
                document.getElementById("itemName").value = selected.text;
                calculateAmount();
            } else {
                document.getElementById("unitPrice").value = "";
                document.getElementById("itemName").value = "";
            }
        }
        
        function calculateAmount() {
            var unitPrice = document.getElementById("unitPrice").value;
            var quantity = document.getElementById("quantity").value;
            if (unitPrice && quantity) {
                var amount = parseFloat(unitPrice) * parseInt(quantity);
                document.getElementById("amountPreview").innerText = "金额: ¥" + amount.toFixed(2);
            } else {
                document.getElementById("amountPreview").innerText = "金额: ¥0.00";
            }
        }
    </script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-admin.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="charge"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-admin.jsp" %>
        </div>
        
        <div class="content">
            <div class="form-container">
                <h2>添加收费</h2>
                
                <form action="${pageContext.request.contextPath}/charge/add" method="post">
                    <input type="hidden" id="itemName" name="chargeItem">
                    
                    <div class="form-group">
                        <label for="patientId">病人</label>
                        <select id="patientId" name="patientId" required>
                            <option value="">请选择病人</option>
                            <c:forEach items="${patients}" var="patient">
                                <option value="${patient.patientId}">${patient.patientName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="itemId">收费项目</label>
                        <select id="itemId" name="itemId" onchange="onItemChange()" required>
                            <option value="">请选择收费项目</option>
                            <c:forEach items="${chargeItems}" var="ci">
                                <option value="${ci.itemId}" data-price="${ci.defaultPrice}">${ci.itemName}（¥${ci.defaultPrice}）</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="unitPrice">单价（元）</label>
                        <input type="number" id="unitPrice" name="unitPrice" step="0.01" min="0" readonly required>
                    </div>
                    
                    <div class="form-group">
                        <label for="quantity">数量</label>
                        <input type="number" id="quantity" name="quantity" min="1" onchange="calculateAmount()" onkeyup="calculateAmount()" required>
                    </div>
                    
                    <div class="amount-preview" id="amountPreview">金额: ¥0.00</div>
                    
                    <div class="form-group">
                        <label for="chargeDate">收费日期</label>
                        <input type="date" id="chargeDate" name="chargeDate">
                    </div>
                    
                    <button type="submit" class="btn-submit">提交</button>
                    <a href="${pageContext.request.contextPath}/charge/list" class="btn-cancel">取消</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
