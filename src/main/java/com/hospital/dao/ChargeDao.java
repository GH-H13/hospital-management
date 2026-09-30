package com.hospital.dao;

import com.hospital.entity.Charge;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 收费DAO接口
 */
public interface ChargeDao {
    /**
     * 查询所有收费记录
     */
    List<Charge> findAll() throws SQLException;
    
    /**
     * 根据病人姓名查询收费记录
     */
    List<Charge> findByPatientName(String patientName) throws SQLException;
    
    /**
     * 根据ID查询收费记录
     */
    Charge findById(Integer chargeId) throws SQLException;
    
    /**
     * 添加收费记录
     */
    boolean add(Charge charge) throws SQLException;
    
    /**
     * 修改收费记录
     */
    boolean update(Charge charge) throws SQLException;
    
    /**
     * 删除收费记录
     */
    boolean delete(Integer chargeId) throws SQLException;
    
    /**
     * 根据条件查询收费明细（统计分析）
     */
    List<Charge> findByConditions(String patientName, Date startDate, Date endDate) throws SQLException;
    
    /**
     * 根据医生ID查询费用（去掉费用表中同一医生的费用）
     */
    List<Charge> findByDoctorId(Integer doctorId) throws SQLException;

    /**
     * 根据病人ID查询收费记录
     */
    List<Charge> findByPatientId(Integer patientId) throws SQLException;
}
