package com.hospital.service;

import com.hospital.dao.DoctorDao;
import com.hospital.dao.impl.DoctorDaoImpl;
import com.hospital.entity.Doctor;

import java.util.List;

/**
 * 医生服务
 */
public class DoctorService {
    private final DoctorDao doctorDao = new DoctorDaoImpl();

    public List<Doctor> findAll() {
        try {
            return doctorDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Doctor> findByName(String doctorName) {
        try {
            return doctorDao.findByName(doctorName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Doctor findById(Integer doctorId) {
        try {
            return doctorDao.findById(doctorId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(Doctor doctor) {
        try {
            return doctorDao.add(doctor);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Doctor doctor) {
        try {
            return doctorDao.update(doctor);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer doctorId) {
        try {
            return doctorDao.delete(doctorId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 医生登录
     */
    public Doctor login(Integer doctorId, String password) {
        try {
            return doctorDao.findByIdAndPassword(doctorId, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新医生个人资料（仅电话和邮箱）
     */
    public boolean updateProfile(Integer doctorId, String phone, String email) {
        try {
            return doctorDao.updateProfile(doctorId, phone, email);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改医生密码
     */
    public boolean updatePassword(Integer doctorId, String newPassword) {
        try {
            return doctorDao.updatePassword(doctorId, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
