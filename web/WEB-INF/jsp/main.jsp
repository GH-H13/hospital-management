<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>首页 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-admin.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="main"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-admin.jsp" %>
        </div>

        <div class="content">
            <div class="dashboard-grid">
                <!-- 左侧：最近入院病人 + 最近收费记录 -->
                <div class="left-column">
                    <div class="info-container" style="margin-bottom: 20px;">
                        <h2>最近入院病人</h2>
                        <c:choose>
                            <c:when test="${empty recentPatients}">
                                <p class="empty">暂无最近入院记录</p>
                            </c:when>
                            <c:otherwise>
                                <table class="data-table">
                                    <tr>
                                        <th>姓名</th>
                                        <th>科室</th>
                                        <th>病症</th>
                                        <th>入院日期</th>
                                    </tr>
                                    <c:forEach items="${recentPatients}" var="p">
                                        <tr>
                                            <td>${p.patientName}</td>
                                            <td>${p.department}</td>
                                            <td>${p.illness}</td>
                                            <td><fmt:formatDate value="${p.admissionDate}" pattern="yyyy-MM-dd"/></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="info-container">
                        <h2>最近收费记录</h2>
                        <c:choose>
                            <c:when test="${empty recentCharges}">
                                <p class="empty">暂无收费记录</p>
                            </c:when>
                            <c:otherwise>
                                <table class="data-table">
                                    <tr>
                                        <th>病人姓名</th>
                                        <th>收费项目</th>
                                        <th>金额</th>
                                        <th>收费日期</th>
                                    </tr>
                                    <c:forEach items="${recentCharges}" var="c">
                                        <tr>
                                            <td>${c.patientName}</td>
                                            <td>${c.chargeItem}</td>
                                            <td>¥${c.amount}</td>
                                            <td><fmt:formatDate value="${c.chargeDate}" pattern="yyyy-MM-dd"/></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- 右侧：今日收入 + 床位使用概况 -->
                <div class="right-column">
                    <div class="info-container" style="margin-bottom: 20px;">
                        <h2>今日收入</h2>
                        <p class="big-number">¥${todayIncome}</p>
                    </div>

                    <div class="info-container">
                        <h2>床位使用概况</h2>
                        <c:choose>
                            <c:when test="${empty bedStatsByDept}">
                                <p class="empty">暂无床位数据</p>
                            </c:when>
                            <c:otherwise>
                                <table class="data-table">
                                    <tr>
                                        <th>科室</th>
                                        <th>占用/总数</th>
                                        <th>使用率</th>
                                    </tr>
                                    <c:forEach items="${bedStatsByDept}" var="entry">
                                        <tr>
                                            <td>${entry.key}</td>
                                            <td>${entry.value[1]} / ${entry.value[0]}</td>
                                            <td>
                                                <c:set var="rate" value="${bedUtilizationByDept[entry.key]}"/>
                                                <c:choose>
                                                    <c:when test="${rate >= 80}">
                                                        <div class="util-bar">
                                                            <div class="util-fill high" style="width: ${rate}%">${rate}%</div>
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${rate >= 50}">
                                                        <div class="util-bar">
                                                            <div class="util-fill mid" style="width: ${rate}%">${rate}%</div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="util-bar">
                                                            <div class="util-fill low" style="width: ${rate}%">${rate}%</div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
