package com.hospital.service;

import com.hospital.dao.ChargeItemDao;
import com.hospital.dao.impl.ChargeItemDaoImpl;
import com.hospital.entity.ChargeItem;

import java.util.List;

/**
 * 收费项目服务
 */
public class ChargeItemService {

    private final ChargeItemDao chargeItemDao = new ChargeItemDaoImpl();

    /**
     * 查询所有收费项目
     */
    public List<ChargeItem> findAll() {
        try {
            return chargeItemDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据ID查询收费项目
     */
    public ChargeItem findById(Integer itemId) {
        try {
            return chargeItemDao.findById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加收费项目
     */
    public boolean add(ChargeItem item) {
        try {
            // 检查是否已存在同名项目
            if (chargeItemDao.findByName(item.getItemName()) != null) {
                return false; // 已存在
            }
            return chargeItemDao.add(item);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改收费项目
     */
    public boolean update(ChargeItem item) {
        try {
            return chargeItemDao.update(item);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除收费项目
     */
    public boolean delete(Integer itemId) {
        try {
            return chargeItemDao.delete(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
