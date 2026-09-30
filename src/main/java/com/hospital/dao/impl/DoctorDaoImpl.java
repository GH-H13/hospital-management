package com.hospital.dao.impl;

import com.hospital.dao.DoctorDao;
import com.hospital.entity.Doctor;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 医生DAO实现类
 */
public class DoctorDaoImpl implements DoctorDao {

    @Override
    public List<Doctor> findAll() throws SQLException {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_doctor ORDER BY doctor_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(buildDoctor(rs));
            }
        }
        return list;
    }

    @Override
    public List<Doctor> findByName(String doctorName) throws SQLException {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_doctor WHERE doctor_name LIKE ? ORDER BY doctor_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + doctorName + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(buildDoctor(rs));
                }
            }
        }
        return list;
    }

    @Override
    public Doctor findById(Integer doctorId) throws SQLException {
        String sql = "SELECT * FROM tb_doctor WHERE doctor_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildDoctor(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean add(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO tb_doctor (doctor_name, gender, title, duty, department, birth_date, work_date, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setDoctorParams(pstmt, doctor);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Doctor doctor) throws SQLException {
        String sql = "UPDATE tb_doctor SET doctor_name=?, gender=?, title=?, duty=?, department=?, " +
                     "birth_date=?, work_date=?, password=? WHERE doctor_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctor.getDoctorName());
            pstmt.setString(2, doctor.getGender());
            pstmt.setString(3, doctor.getTitle());
            pstmt.setString(4, doctor.getDuty());
            pstmt.setString(5, doctor.getDepartment());
            pstmt.setDate(6, doctor.getBirthDate() != null ? new java.sql.Date(doctor.getBirthDate().getTime()) : null);
            pstmt.setDate(7, doctor.getWorkDate() != null ? new java.sql.Date(doctor.getWorkDate().getTime()) : null);
            pstmt.setString(8, doctor.getPassword());
            pstmt.setInt(9, doctor.getDoctorId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(Integer doctorId) throws SQLException {
        String sql = "DELETE FROM tb_doctor WHERE doctor_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public Doctor findByIdAndPassword(Integer doctorId, String password) throws SQLException {
        String sql = "SELECT * FROM tb_doctor WHERE doctor_id = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildDoctor(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean updatePassword(Integer doctorId, String newPassword) throws SQLException {
        String sql = "UPDATE tb_doctor SET password = ? WHERE doctor_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, doctorId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateProfile(Integer doctorId, String phone, String email) throws SQLException {
        String sql = "UPDATE tb_doctor SET phone = ?, email = ? WHERE doctor_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phone);
            pstmt.setString(2, email);
            pstmt.setInt(3, doctorId);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * 从ResultSet构建Doctor对象
     */
    private Doctor buildDoctor(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(rs.getInt("doctor_id"));
        doctor.setDoctorName(rs.getString("doctor_name"));
        doctor.setGender(rs.getString("gender"));
        doctor.setTitle(rs.getString("title"));
        doctor.setDuty(rs.getString("duty"));
        doctor.setDepartment(rs.getString("department"));
        doctor.setBirthDate(rs.getDate("birth_date"));
        doctor.setWorkDate(rs.getDate("work_date"));
        doctor.setPassword(rs.getString("password"));
        // 新增字段（如果列不存在会抛出异常，需要捕获）
        try {
            doctor.setPhone(rs.getString("phone"));
            doctor.setEmail(rs.getString("email"));
        } catch (SQLException e) {
            // 如果列不存在，忽略
        }
        return doctor;
    }

    /**
     * 设置PreparedStatement参数（用于添加）
     */
    private void setDoctorParams(PreparedStatement pstmt, Doctor doctor) throws SQLException {
        pstmt.setString(1, doctor.getDoctorName());
        pstmt.setString(2, doctor.getGender());
        pstmt.setString(3, doctor.getTitle());
        pstmt.setString(4, doctor.getDuty());
        pstmt.setString(5, doctor.getDepartment());
        pstmt.setDate(6, doctor.getBirthDate() != null ? new java.sql.Date(doctor.getBirthDate().getTime()) : null);
        pstmt.setDate(7, doctor.getWorkDate() != null ? new java.sql.Date(doctor.getWorkDate().getTime()) : null);
        pstmt.setString(8, doctor.getPassword() != null ? doctor.getPassword() : "123456");
    }
}
