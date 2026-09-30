package com.hospital.service.impl;

import com.hospital.dao.AdminDao;
import com.hospital.dao.impl.AdminDaoImpl;
import com.hospital.entity.Admin;
import com.hospital.service.AdminService;

/**
 * 管理员服务实现类
 */
public class AdminServiceImpl implements AdminService {
    
    private final AdminDao adminDao = new AdminDaoImpl();
    
    @Override
    public Admin login(String username, String password) {
        try {
            return adminDao.findByNameAndPassword(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean changePassword(String adminName, String oldPassword, String newPassword) {
        try {
            // 先验证旧密码
            Admin admin = adminDao.findByNameAndPassword(adminName, oldPassword);
            if (admin == null) {
                return false; // 旧密码错误
            }
            
            // 修改密码
            return adminDao.updatePassword(admin.getAdminId(), newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
