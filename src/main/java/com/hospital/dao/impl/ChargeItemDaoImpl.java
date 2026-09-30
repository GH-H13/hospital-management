package com.hospital.dao.impl;

import com.hospital.dao.ChargeItemDao;
import com.hospital.entity.ChargeItem;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 收费项目DAO实现类
 */
public class ChargeItemDaoImpl implements ChargeItemDao {

    @Override
    public List<ChargeItem> findAll() throws SQLException {
        List<ChargeItem> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_charge_item ORDER BY item_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(buildChargeItem(rs));
            }
        }
        return list;
    }

    @Override
    public ChargeItem findById(Integer itemId) throws SQLException {
        String sql = "SELECT * FROM tb_charge_item WHERE item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildChargeItem(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean add(ChargeItem item) throws SQLException {
        String sql = "INSERT INTO tb_charge_item (item_name, item_type, default_price) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemType());
            pstmt.setDouble(3, item.getDefaultPrice());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(ChargeItem item) throws SQLException {
        String sql = "UPDATE tb_charge_item SET item_name=?, item_type=?, default_price=? WHERE item_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemType());
            pstmt.setDouble(3, item.getDefaultPrice());
            pstmt.setInt(4, item.getItemId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(Integer itemId) throws SQLException {
        String sql = "DELETE FROM tb_charge_item WHERE item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public ChargeItem findByName(String itemName) throws SQLException {
        String sql = "SELECT * FROM tb_charge_item WHERE item_name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildChargeItem(rs);
                }
            }
        }
        return null;
    }

    /**
     * 从ResultSet构建ChargeItem对象
     */
    private ChargeItem buildChargeItem(ResultSet rs) throws SQLException {
        ChargeItem item = new ChargeItem();
        item.setItemId(rs.getInt("item_id"));
        item.setItemName(rs.getString("item_name"));
        item.setItemType(rs.getString("item_type"));
        item.setDefaultPrice(rs.getDouble("default_price"));
        return item;
    }
}
