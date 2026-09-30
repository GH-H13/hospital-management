<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>统计报表 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-admin.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="statistics"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-admin.jsp" %>
        </div>
        
        <div class="content">
            <h1 class="page-title">数据统计分析</h1>

            <!-- 基础统计卡片 -->
            <div class="stats-grid">
                <div class="stat-card patients">
                    <h3>${patientCount}</h3>
                    <p>病人总数</p>
                </div>
                <div class="stat-card doctors">
                    <h3>${doctorCount}</h3>
                    <p>医生总数</p>
                </div>
                <div class="stat-card beds">
                    <h3>${bedCount}</h3>
                    <p>床位总数</p>
                </div>
                <div class="stat-card income">
                    <h3>¥${totalIncome}</h3>
                    <p>总收入</p>
                </div>
            </div>
            
            <!-- 各科室病人分布 -->
            <div class="chart-container">
                <h2>各科室病人分布</h2>
                <table class="dept-table">
                    <tr>
                        <th>科室</th>
                        <th>病人数</th>
                        <th>占比</th>
                    </tr>
                    <c:forEach items="${patientDeptCount}" var="item">
                        <tr>
                            <td>${item.key}</td>
                            <td>${item.value} 人</td>
                            <td>
                                <div class="utilization-bar">
                                    <div class="utilization-fill" style="width: ${item.value / patientCount * 100}%">
                                        <c:if test="${item.value / patientCount * 100 >= 5}">
                                            ${Math.round(item.value / patientCount * 100)}%
                                        </c:if>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            
            <!-- 各科室床位利用率 -->
            <div class="chart-container">
                <h2>各科室床位利用率</h2>
                <table class="dept-table">
                    <tr>
                        <th>科室</th>
                        <th>总床位</th>
                        <th>占用床位</th>
                        <th>利用率</th>
                    </tr>
                    <c:forEach items="${bedDeptTotal}" var="item">
                        <tr>
                            <td>${item.key}</td>
                            <td>${item.value} 张</td>
                            <td>${bedDeptUsed[item.key]} 张</td>
                            <td>
                                <div class="utilization-bar">
                                    <div class="utilization-fill" style="width: ${bedUtilization[item.key]}%">
                                        <c:if test="${bedUtilization[item.key] >= 5}">
                                            ${bedUtilization[item.key]}%
                                        </c:if>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
