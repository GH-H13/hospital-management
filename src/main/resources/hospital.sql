

CREATE DATABASE IF NOT EXISTS hospital_db
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE hospital_db;

-- 删除外键约束（按依赖顺序）
DROP TABLE IF EXISTS tb_charge;
DROP TABLE IF EXISTS tb_patient;
DROP TABLE IF EXISTS tb_bed;
DROP TABLE IF EXISTS tb_doctor;
DROP TABLE IF EXISTS tb_admin;

-- =====================================================
-- 1. 管理员表
-- =====================================================
CREATE TABLE tb_admin (
    admin_id       INT PRIMARY KEY AUTO_INCREMENT,
    admin_name     VARCHAR(50)  NOT NULL UNIQUE,
    password       VARCHAR(50)  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- 2. 医生表
-- =====================================================
CREATE TABLE tb_doctor (
    doctor_id      INT PRIMARY KEY AUTO_INCREMENT,
    doctor_name    VARCHAR(50)  NOT NULL,
    gender         VARCHAR(10),
    title          VARCHAR(50),
    duty           VARCHAR(50),
    department     VARCHAR(50),
    birth_date     DATE,
    work_date      DATE,
    password       VARCHAR(50) DEFAULT '123456',
    phone          VARCHAR(20),
    email          VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- 3. 病床表
-- =====================================================
CREATE TABLE tb_bed (
    bed_id         INT PRIMARY KEY AUTO_INCREMENT,
    department     VARCHAR(50),
    bed_no         VARCHAR(20),
    bed_fee        DOUBLE DEFAULT 0,
    use_status     VARCHAR(20) DEFAULT '空闲'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- 4. 病人表
-- bed_no 外键关联 tb_bed.bed_id
-- attending_doctor_id 外键关联 tb_doctor.doctor_id
-- =====================================================
CREATE TABLE tb_patient (
    patient_id            INT PRIMARY KEY AUTO_INCREMENT,
    department           VARCHAR(50),
    bed_no               INT,
    patient_name         VARCHAR(50)  NOT NULL,
    gender               VARCHAR(10),
    age                  INT,
    illness              VARCHAR(200),
    attending_doctor_id  INT,
    admission_date       DATE,
    discharge_date       DATE,
    FOREIGN KEY (bed_no)              REFERENCES tb_bed(bed_id),
    FOREIGN KEY (attending_doctor_id)  REFERENCES tb_doctor(doctor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- 5. 收费表
-- patient_id 外键关联 tb_patient.patient_id
-- =====================================================
CREATE TABLE tb_charge (
    charge_id     INT PRIMARY KEY AUTO_INCREMENT,
    patient_id    INT,
    charge_item   VARCHAR(100),
    unit_price    DOUBLE,
    quantity      INT,
    amount        DOUBLE,
    charge_date   DATE,
    FOREIGN KEY (patient_id) REFERENCES tb_patient(patient_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- 6. 收费项目表
-- =====================================================
CREATE TABLE tb_charge_item (
    item_id       INT PRIMARY KEY AUTO_INCREMENT,
    item_name     VARCHAR(100) NOT NULL,
    item_type     VARCHAR(50),
    default_price DOUBLE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- 初始数据
-- =====================================================

-- 管理员账号（账号：admin，密码：123456）
INSERT INTO tb_admin (admin_name, password) VALUES ('admin', '123456');

-- 医生数据（密码默认 123456）
INSERT INTO tb_doctor (doctor_name, gender, title, duty, department, birth_date, work_date) VALUES
('张伟',   '男', '主任医师', '科主任', '内科', '1975-03-15', '2000-07-01'),
('李娜',   '女', '副主任医师', '科副主任', '内科', '1980-08-22', '2005-07-01'),
('王强',   '男', '主治医师', NULL, '外科', '1985-01-10', '2010-07-01'),
('刘敏',   '女', '主任医师', '科主任', '外科', '1978-11-05', '2002-07-01'),
('陈刚',   '男', '主治医师', NULL, '儿科', '1987-06-18', '2012-07-01'),
('赵丽',   '女', '副主任医师', '科副主任', '儿科', '1982-09-30', '2007-07-01'),
('孙伟',   '男', '主任医师', '科主任', '骨科', '1976-12-25', '2001-07-01'),
('周婷',   '女', '主治医师', NULL, '妇产科', '1988-04-12', '2013-07-01');

-- 床位数据
INSERT INTO tb_bed (department, bed_no, bed_fee, use_status) VALUES
('内科', 'N001', 80,  '空闲'),
('内科', 'N002', 80,  '空闲'),
('内科', 'N003', 100, '空闲'),
('内科', 'N004', 100, '空闲'),
('外科', 'W001', 90,  '空闲'),
('外科', 'W002', 90,  '空闲'),
('外科', 'W003', 120, '空闲'),
('儿科', 'E001', 70,  '空闲'),
('儿科', 'E002', 70,  '空闲'),
('骨科', 'G001', 100, '空闲'),
('骨科', 'G002', 100, '空闲'),
('骨科', 'G003', 130, '空闲'),
('妇产科', 'F001', 90,  '空闲'),
('妇产科', 'F002', 90,  '空闲');

-- 病人数据（attending_doctor_id 对应上面插入的医生）
INSERT INTO tb_patient (department, bed_no, patient_name, gender, age, illness, attending_doctor_id, admission_date) VALUES
('内科', 1, '王建福', '男', 45, '高血压', 1,  '2026-06-25'),
('内科', 2, '冯萱雨', '女', 38, '糖尿病', 2,  '2026-06-26'),
('外科', 5, '温伟伦', '男', 52, '阑尾炎', 3,  '2026-06-27'),
('儿科', 8, '张心怡', '女', 8,  '感冒发烧', 5,  '2026-06-28'),
('骨科', 10, '黄志伟', '男', 60, '骨折', 7,  '2026-06-29');

-- 更新已占用床位的状态
UPDATE tb_bed SET use_status = '占用' WHERE bed_id IN (1, 2, 5, 8, 10);

-- 收费数据
INSERT INTO tb_charge (patient_id, charge_item, unit_price, quantity, amount, charge_date) VALUES
(1, '床位费', 80,  5,  400,  '2026-06-26'),
(1, '检查费', 200, 1,  200,  '2026-06-25'),
(2, '床位费', 80,  4,  320,  '2026-06-27'),
(3, '手术费', 3000, 1,  3000, '2026-06-28'),
(4, '床位费', 70,  2,  140,  '2026-06-29'),
(5, '床位费', 100, 1,  100,  '2026-06-29');

-- 收费项目数据
INSERT INTO tb_charge_item (item_name, item_type, default_price) VALUES
('诊查费', '诊疗', 50),
('护理费', '护理', 30),
('床位费', '住宿', 80),
('检查费', '检查', 200),
('化验费', '化验', 150),
('手术费', '手术', 3000),
('药品费', '药品', 0),
('治疗费', '治疗', 100),
('材料费', '材料', 50),
('输液费', '护理', 20);

-- =====================================================
-- 完成提示
-- =====================================================
SELECT '数据库初始化完成！' AS result;
SELECT '管理员账号：admin  密码：1234' AS admin_login;
SELECT '医生账号使用医生ID登录，默认密码：123456' AS doctor_login;
