package com.hospital.service;

import com.hospital.dao.PatientDao;
import com.hospital.dao.impl.PatientDaoImpl;
import com.hospital.entity.Patient;
import com.hospital.entity.Bed;

import java.util.Date;
import java.util.List;

/**
 * 病人服务
 */
public class PatientService {
    private final PatientDao patientDao = new PatientDaoImpl();
    private final BedService bedService = new BedService();

    public List<Patient> findAll() {
        try {
            return patientDao.findAll();
        } catch (Exception e) {
            System.err.println("❌ PatientService.findAll() 发生异常：");
            e.printStackTrace();
            // 返回空列表而不是null，避免JSP中<c:forEach>报错
            return new java.util.ArrayList<>();
        }
    }

    public List<Patient> findByName(String patientName) {
        try {
            return patientDao.findByName(patientName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Patient findById(Integer patientId) {
        try {
            return patientDao.findById(patientId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(Patient patient) {
        try {
            if (patientDao.add(patient)) {
                // 新增成功后，更新病床状态为"占用"
                if (patient.getBedNo() != null) {
                    // 先获取完整的病床信息
                    List<Bed> beds = bedService.findAll();
                    if (beds != null) {
                        for (Bed bed : beds) {
                            if (bed.getBedId().equals(patient.getBedNo())) {
                                bed.setUseStatus("占用");
                                bedService.updateBedStatus(bed);
                                break;
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Patient patient) {
        try {
            return patientDao.update(patient);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer patientId) {
        try {
            // 先查找病人，获取其床位ID
            Patient patient = this.findById(patientId);
            Integer bedId = (patient != null) ? patient.getBedNo() : null;

            // 删除病人
            boolean deleted = patientDao.delete(patientId);

            // 如果删除成功且病人有床位，释放床位
            if (deleted && bedId != null) {
                Bed bed = bedService.findById(bedId);
                if (bed != null) {
                    bed.setUseStatus("空闲");
                    bedService.updateBedStatus(bed);
                }
            }

            return deleted;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询指定医生负责的所有病人
     */
    public List<Patient> findByAttendingDoctor(Integer doctorId) {
        try {
            return patientDao.findByAttendingDoctor(doctorId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 病床利用率查询（统计分析）
     */
    public List<Patient> findByConditions(String department, Integer doctorId, Date startDate, Date endDate) {
        try {
            return patientDao.findByConditions(department, doctorId, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
