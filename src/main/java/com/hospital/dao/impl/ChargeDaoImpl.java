package com.hospital.dao.impl;

import com.hospital.dao.ChargeDao;
import com.hospital.entity.Charge;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 收费DAO实现类
 */
public class ChargeDaoImpl implements ChargeDao {

    @Override
    public List<Charge> findAll() throws SQLException {
        List<Charge> list = new ArrayList<>();
        String sql = "SELECT c.*, p.patient_name, p.department " +
                     "FROM tb_charge c " +
                     "LEFT JOIN tb_patient p ON c.patient_id = p.patient_id " +
                     "ORDER BY c.charge_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(buildCharge(rs));
            }
        }
        return list;
    }

    @Override
    public List<Charge> findByPatientName(String patientName) throws SQLException {
        List<Charge> list = new ArrayList<>();
        String sql = "SELECT c.*, p.patient_name, p.department " +
                     "FROM tb_charge c " +
                     "LEFT JOIN tb_patient p ON c.patient_id = p.patient_id " +
                     "WHERE p.patient_name LIKE ? " +
                     "ORDER BY c.charge_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + patientName + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(buildCharge(rs));
                }
            }
        }
        return list;
    }

    @Override
    public Charge findById(Integer chargeId) throws SQLException {
        String sql = "SELECT c.*, p.patient_name, p.department " +
                     "FROM tb_charge c " +
                     "LEFT JOIN tb_patient p ON c.patient_id = p.patient_id " +
                     "WHERE c.charge_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, chargeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildCharge(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean add(Charge charge) throws SQLException {
        String sql = "INSERT INTO tb_charge (patient_id, charge_item, unit_price, quantity, amount, charge_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setChargeParams(pstmt, charge);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Charge charge) throws SQLException {
        String sql = "UPDATE tb_charge SET patient_id=?, charge_item=?, unit_price=?, quantity=?, " +
                     "amount=?, charge_date=? WHERE charge_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, charge.getPatientId());
            pstmt.setString(2, charge.getChargeItem());
            pstmt.setDouble(3, charge.getUnitPrice());
            pstmt.setInt(4, charge.getQuantity());
            pstmt.setDouble(5, charge.getAmount());
            pstmt.setDate(6, charge.getChargeDate() != null ? new java.sql.Date(charge.getChargeDate().getTime()) : null);
            pstmt.setInt(7, charge.getChargeId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(Integer chargeId) throws SQLException {
        String sql = "DELETE FROM tb_charge WHERE charge_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, chargeId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Charge> findByConditions(String patientName, Date startDate, Date endDate) throws SQLException {
        List<Charge> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT c.*, p.patient_name, p.department " +
            "FROM tb_charge c " +
            "LEFT JOIN tb_patient p ON c.patient_id = p.patient_id WHERE 1=1"
        );
        
        if (patientName != null && !patientName.trim().isEmpty()) {
            sql.append(" AND p.patient_name LIKE ?");
        }
        if (startDate != null) {
            sql.append(" AND c.charge_date >= ?");
        }
        if (endDate != null) {
            sql.append(" AND c.charge_date <= ?");
        }
        sql.append(" ORDER BY c.charge_id");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (patientName != null && !patientName.trim().isEmpty()) {
                pstmt.setString(paramIndex++, "%" + patientName + "%");
            }
            if (startDate != null) {
                pstmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
            }
            if (endDate != null) {
                pstmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(buildCharge(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<Charge> findByDoctorId(Integer doctorId) throws SQLException {
        List<Charge> list = new ArrayList<>();
        String sql = "SELECT c.*, p.patient_name, p.department " +
                     "FROM tb_charge c " +
                     "LEFT JOIN tb_patient p ON c.patient_id = p.patient_id " +
                     "WHERE p.attending_doctor_id = ? " +
                     "ORDER BY c.charge_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(buildCharge(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<Charge> findByPatientId(Integer patientId) throws SQLException {
        List<Charge> list = new ArrayList<>();
        String sql = "SELECT c.*, p.patient_name, p.department " +
                     "FROM tb_charge c " +
                     "LEFT JOIN tb_patient p ON c.patient_id = p.patient_id " +
                     "WHERE c.patient_id = ? " +
                     "ORDER BY c.charge_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(buildCharge(rs));
                }
            }
        }
        return list;
    }

    /**
     * 从ResultSet构建Charge对象
     */
    private Charge buildCharge(ResultSet rs) throws SQLException {
        Charge charge = new Charge();
        charge.setChargeId(rs.getInt("charge_id"));
        charge.setPatientId(rs.getInt("patient_id"));
        charge.setChargeItem(rs.getString("charge_item"));
        charge.setUnitPrice(rs.getDouble("unit_price"));
        charge.setQuantity(rs.getInt("quantity"));
        charge.setAmount(rs.getDouble("amount"));
        charge.setChargeDate(rs.getDate("charge_date"));
        // 关联属性
        charge.setPatientName(rs.getString("patient_name"));
        charge.setDepartment(rs.getString("department"));
        return charge;
    }

    /**
     * 设置PreparedStatement参数
     */
    private void setChargeParams(PreparedStatement pstmt, Charge charge) throws SQLException {
        pstmt.setInt(1, charge.getPatientId());
        pstmt.setString(2, charge.getChargeItem());
        pstmt.setDouble(3, charge.getUnitPrice());
        pstmt.setInt(4, charge.getQuantity());
        pstmt.setDouble(5, charge.getAmount());
        pstmt.setDate(6, charge.getChargeDate() != null ? new java.sql.Date(charge.getChargeDate().getTime()) : null);
    }
}
