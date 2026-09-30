package com.hospital.entity;

import java.io.Serializable;

/**
 * 管理员实体类
 */
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer adminId;        // 管理员ID（主键）
    private String adminName;       // 管理员账号
    private String password;        // 密码

    public Admin() {
    }

    public Admin(Integer adminId, String adminName, String password) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.password = password;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                '}';
    }
}
