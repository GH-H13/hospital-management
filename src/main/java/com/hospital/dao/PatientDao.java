package com.hospital.dao;

import com.hospital.entity.Patient;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 病人DAO接口
 */
public interface PatientDao {
    /**
     * 查询所有病人
     */
    List<Patient> findAll() throws SQLException;
    
    /**
     * 根据病人姓名模糊查询
     */
    List<Patient> findByName(String patientName) throws SQLException;
    
    /**
     * 根据ID查询病人
     */
    Patient findById(Integer patientId) throws SQLException;
    
    /**
     * 添加病人
     */
    boolean add(Patient patient) throws SQLException;
    
    /**
     * 修改病人信息
     */
    boolean update(Patient patient) throws SQLException;
    
    /**
     * 删除病人
     */
    boolean delete(Integer patientId) throws SQLException;
    
    /**
     * 根据条件查询病床利用情况（统计分析）
     */
    List<Patient> findByConditions(String department, Integer doctorId, Date startDate, Date endDate) throws SQLException;
    
    /**
     * 查询指定医生负责的所有病人
     */
    List<Patient> findByAttendingDoctor(Integer doctorId) throws SQLException;
}
