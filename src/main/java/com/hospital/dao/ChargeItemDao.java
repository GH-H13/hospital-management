package com.hospital.dao;

import com.hospital.entity.ChargeItem;

import java.sql.SQLException;
import java.util.List;

/**
 * 收费项目DAO接口
 */
public interface ChargeItemDao {
    /**
     * 查询所有收费项目
     */
    List<ChargeItem> findAll() throws SQLException;

    /**
     * 根据ID查询收费项目
     */
    ChargeItem findById(Integer itemId) throws SQLException;

    /**
     * 添加收费项目
     */
    boolean add(ChargeItem item) throws SQLException;

    /**
     * 修改收费项目
     */
    boolean update(ChargeItem item) throws SQLException;

    /**
     * 删除收费项目
     */
    boolean delete(Integer itemId) throws SQLException;

    /**
     * 根据项目名称查询
     */
    ChargeItem findByName(String itemName) throws SQLException;
}
