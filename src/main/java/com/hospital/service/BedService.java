package com.hospital.service;

import com.hospital.dao.BedDao;
import com.hospital.dao.impl.BedDaoImpl;
import com.hospital.entity.Bed;
import com.hospital.entity.BedUtilization;

import java.util.List;

/**
 * 病床服务
 */
public class BedService {
    private final BedDao bedDao = new BedDaoImpl();

    public List<Bed> findAll() {
        try {
            return bedDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Bed> findByDepartment(String department) {
        try {
            return bedDao.findByDepartment(department);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bed findById(Integer bedId) {
        try {
            return bedDao.findById(bedId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bed findByBedNoAndDept(String bedNo, String department) {
        try {
            return bedDao.findByBedNoAndDept(bedNo, department);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(Bed bed) {
        try {
            return bedDao.add(bed);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Bed bed) {
        try {
            return bedDao.update(bed);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer bedId) {
        try {
            return bedDao.delete(bedId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Bed> findAvailableBeds() {
        try {
            return bedDao.findAvailableBeds();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新病床使用状态（内部方法）
     */
    public boolean updateBedStatus(Bed bed) {
        try {
            return bedDao.update(bed);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 计算指定科室的病床利用率
     * @param dept 科室名称
     * @return 病床利用率统计数据对象
     */
    public BedUtilization calcUtilization(String dept) {
        try {
            // 统计该科室的总病床数
            Integer totalBeds = bedDao.countTotalBedByDept(dept);
            
            // 统计该科室占用的病床数
            Integer usedBeds = bedDao.countUsedBedByDept(dept);
            
            // 计算利用率：(占用床数/总床数)*100%，保留1位小数
            Double utilizationRate = totalBeds > 0 ? Math.round(((double) usedBeds / totalBeds * 100) * 10.0) / 10.0 : 0.0;
            
            // 封装数据对象
            return new BedUtilization(dept, totalBeds, usedBeds, utilizationRate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
