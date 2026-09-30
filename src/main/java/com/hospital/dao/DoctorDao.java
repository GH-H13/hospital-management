package com.hospital.dao;

import com.hospital.entity.Doctor;
import java.sql.SQLException;
import java.util.List;

/**
 * 医生DAO接口
 */
public interface DoctorDao {
    /**
     * 查询所有医生
     */
    List<Doctor> findAll() throws SQLException;
    
    /**
     * 根据医生姓名模糊查询
     */
    List<Doctor> findByName(String doctorName) throws SQLException;
    
    /**
     * 根据ID查询医生
     */
    Doctor findById(Integer doctorId) throws SQLException;
    
    /**
     * 添加医生
     */
    boolean add(Doctor doctor) throws SQLException;
    
    /**
     * 修改医生信息
     */
    boolean update(Doctor doctor) throws SQLException;
    
    /**
     * 删除医生
     */
    boolean delete(Integer doctorId) throws SQLException;
    
    /**
     * 根据账号和密码查询医生（用于登录）
     */
    Doctor findByIdAndPassword(Integer doctorId, String password) throws SQLException;

    /**
     * 修改医生密码
     */
    boolean updatePassword(Integer doctorId, String newPassword) throws SQLException;

    /**
     * 更新医生个人资料（仅电话和邮箱）
     */
    boolean updateProfile(Integer doctorId, String phone, String email) throws SQLException;
}
