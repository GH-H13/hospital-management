<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>个人信息 - 医院住院管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/header-doctor.jsp" %>
    <div class="container">
        <div class="sidebar">
            <c:set var="activeMenu" value="doctorProfile"/>
            <%@ include file="/WEB-INF/jsp/includes/sidebar-doctor.jsp" %>
        </div>

        <div class="content">
            <%-- 显示消息 --%>
            <%
                String passwordSuccess = (String) session.getAttribute("passwordSuccess");
                String passwordError = (String) session.getAttribute("passwordError");
                if (passwordSuccess != null) {
                    session.removeAttribute("passwordSuccess");
            %>
                <div style="background: #d4edda; color: #155724; padding: 15px; border-radius: 5px; margin-bottom: 20px;">
                    <%= passwordSuccess %>
                </div>
            <%
                }
                if (passwordError != null) {
                    session.removeAttribute("passwordError");
            %>
                <div style="background: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; margin-bottom: 20px;">
                    <%= passwordError %>
                </div>
            <%
                }
            %>

            <div class="profile-card">
                <h3>个人信息</h3>
                <form action="${pageContext.request.contextPath}/doctor/updateProfile" method="post">
                    <div class="profile-row">
                        <div class="profile-label">医生ID</div>
                        <div class="profile-value">${doctor.doctorId}</div>
                    </div>
                    <div class="profile-row">
                        <div class="profile-label">姓名</div>
                        <div class="profile-value">${doctor.doctorName}</div>
                    </div>
                    <div class="profile-row">
                        <div class="profile-label">科室</div>
                        <div class="profile-value">${doctor.department}</div>
                    </div>
                    <div class="profile-row">
                        <div class="profile-label">职称</div>
                        <div class="profile-value">${doctor.title != null ? doctor.title : ""}</div>
                    </div>
                    <div class="profile-row">
                        <div class="profile-label">电话</div>
                        <div class="profile-value">
                            <input type="text" name="phone" value="${doctor.phone != null ? doctor.phone : ''}">
                        </div>
                    </div>
                    <div class="profile-row">
                        <div class="profile-label">邮箱</div>
                        <div class="profile-value">
                            <input type="email" name="email" value="${doctor.email != null ? doctor.email : ''}">
                        </div>
                    </div>
                    <button type="submit" class="btn-save">保存修改</button>
                </form>

                <hr style="margin: 40px 0; border: none; border-top: 1px solid #eee;">

                <h3 style="color: #2c3e50; margin-bottom: 30px; font-size: 24px; padding-bottom: 15px; border-bottom: 2px solid #e74c3c;">修改密码</h3>
                <form action="${pageContext.request.contextPath}/doctor/changePassword" method="post" onsubmit="return validatePassword()">
                    <div class="profile-row">
                        <div class="profile-label">当前密码</div>
                        <div class="profile-value">
                            <input type="password" id="oldPassword" name="oldPassword" required placeholder="输入当前密码">
                        </div>
                    </div>
                    <div class="profile-row">
                        <div class="profile-label">新密码</div>
                        <div class="profile-value">
                            <input type="password" id="newPassword" name="newPassword" required placeholder="输入新密码（至少6位）" minlength="6">
                        </div>
                    </div>
                    <div class="profile-row">
                        <div class="profile-label">确认新密码</div>
                        <div class="profile-value">
                            <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="再次输入新密码">
                        </div>
                    </div>
                    <button type="submit" class="btn-save" style="background: #e74c3c;">修改密码</button>
                </form>

                <script>
                    function validatePassword() {
                        var newPwd = document.getElementById("newPassword").value;
                        var confirmPwd = document.getElementById("confirmPassword").value;

                        if (newPwd.length < 6) {
                            alert("新密码长度至少6位！");
                            return false;
                        }

                        if (newPwd !== confirmPwd) {
                            alert("两次输入的新密码不一致！");
                            return false;
                        }

                        return true;
                    }
                </script>

                <div class="tip">
                    <strong>提示：</strong><br>
                    1. 医生ID、姓名、科室和职称由管理员设置，无法自行修改。<br>
                    2. 您可以修改联系电话和邮箱地址。<br>
                    3. 修改密码后，下次登录请使用新密码。
                </div>
            </div>
        </div>
    </div>
</body>
</html>
