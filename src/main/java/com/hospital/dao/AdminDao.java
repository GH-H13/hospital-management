package com.hospital.dao;

import com.hospital.entity.Admin;
import java.sql.SQLException;

/**
 * 管理员DAO接口
 */
public interface AdminDao {
    /**
     * 根据账号和密码查询管理员
     */
    Admin findByNameAndPassword(String adminName, String password) throws SQLException;
    
    /**
     * 修改管理员密码
     */
    boolean updatePassword(Integer adminId, String newPassword) throws SQLException;
}
