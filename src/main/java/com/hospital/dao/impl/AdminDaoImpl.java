package com.hospital.dao.impl;

import com.hospital.dao.AdminDao;
import com.hospital.entity.Admin;
import com.hospital.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 管理员DAO实现类
 */
public class AdminDaoImpl implements AdminDao {

    @Override
    public Admin findByNameAndPassword(String adminName, String password) throws SQLException {
        String sql = "SELECT * FROM tb_admin WHERE admin_name = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adminName);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Admin admin = new Admin();
                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setAdminName(rs.getString("admin_name"));
                    admin.setPassword(rs.getString("password"));
                    return admin;
                }
            }
        }
        return null;
    }

    @Override
    public boolean updatePassword(Integer adminId, String newPassword) throws SQLException {
        String sql = "UPDATE tb_admin SET password = ? WHERE admin_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, adminId);
            return pstmt.executeUpdate() > 0;
        }
    }
}
