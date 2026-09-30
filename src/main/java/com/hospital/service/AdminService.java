package com.hospital.service;

import com.hospital.entity.Admin;

/**
 * 管理员服务接口
 */
public interface AdminService {
    
    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回管理员对象，失败返回null
     */
    Admin login(String username, String password);
    
    /**
     * 修改管理员密码
     * @param adminName 管理员账号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改成功返回true，失败返回false
     */
    boolean changePassword(String adminName, String oldPassword, String newPassword);
}
