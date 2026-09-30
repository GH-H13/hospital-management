<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>编辑病人 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-admin.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="patient"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-admin.jsp" %>
        </div>

        <div class="content">
            <div class="form-container">
                <h2>编辑病人信息</h2>

                <form action="${pageContext.request.contextPath}/patient/edit" method="post">
                    <input type="hidden" name="patientId" value="${patient.patientId}">

                    <div class="form-group">
                        <label for="patientName">姓名</label>
                        <input type="text" id="patientName" name="patientName" value="${patient.patientName}" required>
                    </div>

                    <div class="form-group">
                        <label for="department">科室</label>
                        <select id="department" name="department" required>
                            <option value="">请选择科室</option>
                            <c:forEach items="${departments}" var="dept">
                                <option value="${dept}" ${patient.department eq dept ? 'selected' : ''}>${dept}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="gender">性别</label>
                        <select id="gender" name="gender" required>
                            <option value="">请选择</option>
                            <option value="男" ${patient.gender eq '男' ? 'selected' : ''}>男</option>
                            <option value="女" ${patient.gender eq '女' ? 'selected' : ''}>女</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="age">年龄</label>
                        <input type="number" id="age" name="age" min="0" max="150" value="${patient.age}" required>
                    </div>

                    <div class="form-group">
                        <label for="illness">病症</label>
                        <input type="text" id="illness" name="illness" value="${patient.illness}">
                    </div>

                    <div class="form-group">
                        <label for="bedNo">分配床位</label>
                        <select id="bedNo" name="bedNo">
                            <option value="">暂不分配</option>
                            <c:forEach items="${availableBeds}" var="bed">
                                <option value="${bed.bedId}" ${patient.bedNo == bed.bedId ? 'selected' : ''}>${bed.bedNo}（${bed.department}，${bed.bedFee}元/天）</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="attendingDoctorId">主治医生</label>
                        <select id="attendingDoctorId" name="attendingDoctorId">
                            <option value="">请选择</option>
                            <c:forEach items="${doctors}" var="doc">
                                <option value="${doc.doctorId}" ${patient.attendingDoctorId == doc.doctorId ? 'selected' : ''}>${doc.doctorName}（${doc.department}，${doc.title}）</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="admissionDate">入院日期</label>
                        <input type="date" id="admissionDate" name="admissionDate" value="${patient.admissionDateStr}">
                    </div>

                    <div class="form-group">
                        <label for="dischargeDate">出院日期</label>
                        <input type="date" id="dischargeDate" name="dischargeDate" value="${patient.dischargeDateStr}">
                    </div>

                    <button type="submit" class="btn-submit">更新</button>
                    <a href="${pageContext.request.contextPath}/patient/list" class="btn-cancel">取消</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
