package com.hospital.dao.impl;

import com.hospital.dao.BedDao;
import com.hospital.entity.Bed;
import com.hospital.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 病床DAO实现类
 */
public class BedDaoImpl implements BedDao {

    @Override
    public List<Bed> findAll() throws SQLException {
        List<Bed> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_bed ORDER BY bed_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(buildBed(rs));
            }
        }
        return list;
    }

    @Override
    public List<Bed> findByDepartment(String department) throws SQLException {
        List<Bed> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_bed WHERE department = ? ORDER BY bed_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(buildBed(rs));
                }
            }
        }
        return list;
    }

    @Override
    public Bed findByBedNo(String bedNo) throws SQLException {
        String sql = "SELECT * FROM tb_bed WHERE bed_no = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bedNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildBed(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Bed findByBedNoAndDept(String bedNo, String department) throws SQLException {
        String sql = "SELECT * FROM tb_bed WHERE bed_no = ? AND department = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bedNo);
            pstmt.setString(2, department);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildBed(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Bed findById(Integer bedId) throws SQLException {
        String sql = "SELECT * FROM tb_bed WHERE bed_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bedId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildBed(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean add(Bed bed) throws SQLException {
        String sql = "INSERT INTO tb_bed (department, bed_no, bed_fee, use_status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setBedParams(pstmt, bed);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Bed bed) throws SQLException {
        String sql = "UPDATE tb_bed SET department=?, bed_no=?, bed_fee=?, use_status=? WHERE bed_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bed.getDepartment());
            pstmt.setString(2, bed.getBedNo());
            pstmt.setDouble(3, bed.getBedFee());
            pstmt.setString(4, bed.getUseStatus());
            pstmt.setInt(5, bed.getBedId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(Integer bedId) throws SQLException {
        String sql = "DELETE FROM tb_bed WHERE bed_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bedId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Bed> findAvailableBeds() throws SQLException {
        List<Bed> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_bed WHERE use_status = '空闲' ORDER BY bed_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(buildBed(rs));
            }
        }
        return list;
    }

    /**
     * 从ResultSet构建Bed对象
     */
    private Bed buildBed(ResultSet rs) throws SQLException {
        Bed bed = new Bed();
        bed.setBedId(rs.getInt("bed_id"));
        bed.setDepartment(rs.getString("department"));
        bed.setBedNo(rs.getString("bed_no"));
        bed.setBedFee(rs.getDouble("bed_fee"));
        bed.setUseStatus(rs.getString("use_status"));
        return bed;
    }

    /**
     * 设置PreparedStatement参数
     */
    private void setBedParams(PreparedStatement pstmt, Bed bed) throws SQLException {
        pstmt.setString(1, bed.getDepartment());
        pstmt.setString(2, bed.getBedNo());
        pstmt.setDouble(3, bed.getBedFee());
        pstmt.setString(4, bed.getUseStatus());
    }

    @Override
    public Integer countTotalBedByDept(String department) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tb_bed WHERE department = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    @Override
    public Integer countUsedBedByDept(String department) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tb_bed WHERE department = ? AND use_status = '占用'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}
