package com.hospital.service;

import com.hospital.dao.ChargeDao;
import com.hospital.dao.impl.ChargeDaoImpl;
import com.hospital.entity.Charge;
import com.hospital.entity.Patient;

import java.util.Date;
import java.util.List;

/**
 * 收费服务
 */
public class ChargeService {
    private final ChargeDao chargeDao = new ChargeDaoImpl();

    public List<Charge> findAll() {
        try {
            return chargeDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Charge> findByPatientName(String patientName) {
        try {
            return chargeDao.findByPatientName(patientName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Charge findById(Integer chargeId) {
        try {
            return chargeDao.findById(chargeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(Charge charge) {
        try {
            return chargeDao.add(charge);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Charge charge) {
        try {
            return chargeDao.update(charge);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer chargeId) {
        try {
            return chargeDao.delete(chargeId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 收费明细查询（统计分析）
     */
    public List<Charge> findByConditions(String patientName, Date startDate, Date endDate) {
        try {
            return chargeDao.findByConditions(patientName, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据医生ID查询费用
     */
    public List<Charge> findByDoctorId(Integer doctorId) {
        try {
            return chargeDao.findByDoctorId(doctorId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据病人ID查询收费记录
     */
    public List<Charge> findByPatientId(Integer patientId) {
        try {
            return chargeDao.findByPatientId(patientId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}