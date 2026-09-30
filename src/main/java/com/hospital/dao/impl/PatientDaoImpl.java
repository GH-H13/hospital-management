package com.hospital.dao.impl;

import com.hospital.dao.PatientDao;
import com.hospital.entity.Patient;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 病人DAO实现类
 */
public class PatientDaoImpl implements PatientDao {

    @Override
    public List<Patient> findAll() throws SQLException {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT p.*, d.doctor_name as attending_doctor_name, b.bed_no as bed_no_str " +
                     "FROM tb_patient p " +
                     "LEFT JOIN tb_doctor d ON p.attending_doctor_id = d.doctor_id " +
                     "LEFT JOIN tb_bed b ON p.bed_no = b.bed_id " +
                     "ORDER BY p.patient_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(buildPatient(rs));
            }
        }
        return list;
    }

    @Override
    public List<Patient> findByName(String patientName) throws SQLException {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT p.*, d.doctor_name as attending_doctor_name, b.bed_no as bed_no_str " +
                     "FROM tb_patient p " +
                     "LEFT JOIN tb_doctor d ON p.attending_doctor_id = d.doctor_id " +
                     "LEFT JOIN tb_bed b ON p.bed_no = b.bed_id " +
                     "WHERE p.patient_name LIKE ? " +
                     "ORDER BY p.patient_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + patientName + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(buildPatient(rs));
                }
            }
        }
        return list;
    }

    @Override
    public Patient findById(Integer patientId) throws SQLException {
        String sql = "SELECT p.*, d.doctor_name as attending_doctor_name, b.bed_no as bed_no_str " +
                     "FROM tb_patient p " +
                     "LEFT JOIN tb_doctor d ON p.attending_doctor_id = d.doctor_id " +
                     "LEFT JOIN tb_bed b ON p.bed_no = b.bed_id " +
                     "WHERE p.patient_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildPatient(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean add(Patient patient) throws SQLException {
        String sql = "INSERT INTO tb_patient (department, bed_no, patient_name, gender, age, illness, " +
                     "attending_doctor_id, admission_date, discharge_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setPatientParams(pstmt, patient);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Patient patient) throws SQLException {
        String sql = "UPDATE tb_patient SET department=?, bed_no=?, patient_name=?, gender=?, age=?, " +
                     "illness=?, attending_doctor_id=?, admission_date=?, discharge_date=? WHERE patient_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getDepartment());
            pstmt.setObject(2, patient.getBedNo());
            pstmt.setString(3, patient.getPatientName());
            pstmt.setString(4, patient.getGender());
            pstmt.setInt(5, patient.getAge());
            pstmt.setString(6, patient.getIllness());
            pstmt.setObject(7, patient.getAttendingDoctorId());
            pstmt.setDate(8, patient.getAdmissionDate() != null ? new java.sql.Date(patient.getAdmissionDate().getTime()) : null);
            pstmt.setDate(9, patient.getDischargeDate() != null ? new java.sql.Date(patient.getDischargeDate().getTime()) : null);
            pstmt.setInt(10, patient.getPatientId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(Integer patientId) throws SQLException {
        String sql = "DELETE FROM tb_patient WHERE patient_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Patient> findByConditions(String department, Integer doctorId, Date startDate, Date endDate) throws SQLException {
        List<Patient> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT p.*, d.doctor_name as attending_doctor_name, b.bed_no as bed_no_str " +
            "FROM tb_patient p " +
            "LEFT JOIN tb_doctor d ON p.attending_doctor_id = d.doctor_id " +
            "LEFT JOIN tb_bed b ON p.bed_no = b.bed_id WHERE 1=1"
        );
        
        if (department != null && !department.trim().isEmpty()) {
            sql.append(" AND p.department = ?");
        }
        if (doctorId != null) {
            sql.append(" AND p.attending_doctor_id = ?");
        }
        if (startDate != null) {
            sql.append(" AND p.admission_date >= ?");
        }
        if (endDate != null) {
            sql.append(" AND p.admission_date <= ?");
        }
        sql.append(" ORDER BY p.patient_id");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (department != null && !department.trim().isEmpty()) {
                pstmt.setString(paramIndex++, department);
            }
            if (doctorId != null) {
                pstmt.setInt(paramIndex++, doctorId);
            }
            if (startDate != null) {
                pstmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
            }
            if (endDate != null) {
                pstmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(buildPatient(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<Patient> findByAttendingDoctor(Integer doctorId) throws SQLException {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT p.*, d.doctor_name as attending_doctor_name, b.bed_no as bed_no_str " +
                     "FROM tb_patient p " +
                     "LEFT JOIN tb_doctor d ON p.attending_doctor_id = d.doctor_id " +
                     "LEFT JOIN tb_bed b ON p.bed_no = b.bed_id " +
                     "WHERE p.attending_doctor_id = ? " +
                     "ORDER BY p.patient_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(buildPatient(rs));
                }
            }
        }
        return list;
    }

    /**
     * 从ResultSet构建Patient对象
     */
    private Patient buildPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("patient_id"));
        patient.setDepartment(rs.getString("department"));
        patient.setBedNo(rs.getObject("bed_no") != null ? rs.getInt("bed_no") : null);
        patient.setPatientName(rs.getString("patient_name"));
        patient.setGender(rs.getString("gender"));
        patient.setAge(rs.getInt("age"));
        patient.setIllness(rs.getString("illness"));
        patient.setAttendingDoctorId(rs.getObject("attending_doctor_id") != null ? rs.getInt("attending_doctor_id") : null);
        patient.setAdmissionDate(rs.getDate("admission_date"));
        patient.setDischargeDate(rs.getDate("discharge_date"));
        // 关联属性
        patient.setAttendingDoctorName(rs.getString("attending_doctor_name"));
        patient.setBedNoStr(rs.getString("bed_no_str"));
        return patient;
    }

    /**
     * 设置PreparedStatement参数
     */
    private void setPatientParams(PreparedStatement pstmt, Patient patient) throws SQLException {
        pstmt.setString(1, patient.getDepartment());
        pstmt.setObject(2, patient.getBedNo());
        pstmt.setString(3, patient.getPatientName());
        pstmt.setString(4, patient.getGender());
        pstmt.setInt(5, patient.getAge());
        pstmt.setString(6, patient.getIllness());
        pstmt.setObject(7, patient.getAttendingDoctorId());
        pstmt.setDate(8, patient.getAdmissionDate() != null ? new java.sql.Date(patient.getAdmissionDate().getTime()) : null);
        pstmt.setDate(9, patient.getDischargeDate() != null ? new java.sql.Date(patient.getDischargeDate().getTime()) : null);
    }
}
