package com.hospital.dao;

import com.hospital.entity.Bed;
import java.sql.SQLException;
import java.util.List;

/**
 * 病床DAO接口
 */
public interface BedDao {
    /**
     * 查询所有病床
     */
    List<Bed> findAll() throws SQLException;
    
    /**
     * 根据科别查询病床
     */
    List<Bed> findByDepartment(String department) throws SQLException;
    
    /**
     * 根据病床号查询
     */
    Bed findByBedNo(String bedNo) throws SQLException;
    
    /**
     * 根据病床号和科室查询（用于重复校验）
     */
    Bed findByBedNoAndDept(String bedNo, String department) throws SQLException;
    
    /**
     * 根据ID查询病床
     */
    Bed findById(Integer bedId) throws SQLException;
    
    /**
     * 添加病床
     */
    boolean add(Bed bed) throws SQLException;
    
    /**
     * 修改病床信息
     */
    boolean update(Bed bed) throws SQLException;
    
    /**
     * 删除病床
     */
    boolean delete(Integer bedId) throws SQLException;
    
    /**
     * 查询空闲病床
     */
    List<Bed> findAvailableBeds() throws SQLException;
    
    /**
     * 统计指定科室的总病床数
     */
    Integer countTotalBedByDept(String department) throws SQLException;
    
    /**
     * 统计指定科室的占用病床数
     */
    Integer countUsedBedByDept(String department) throws SQLException;
}
