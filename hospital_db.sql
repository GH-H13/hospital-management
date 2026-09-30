/*
 Navicat Premium Data Transfer

 Source Server         : book_db
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : hospital_db

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 03/07/2026 07:42:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin`  (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `admin_name`(`admin_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
INSERT INTO `tb_admin` VALUES (1, 'admin', '1234');

-- ----------------------------
-- Table structure for tb_bed
-- ----------------------------
DROP TABLE IF EXISTS `tb_bed`;
CREATE TABLE `tb_bed`  (
  `bed_id` int NOT NULL AUTO_INCREMENT,
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `bed_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `bed_fee` double NULL DEFAULT 0,
  `use_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '空闲',
  PRIMARY KEY (`bed_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_bed
-- ----------------------------
INSERT INTO `tb_bed` VALUES (1, '内科', 'N001', 80, '占用');
INSERT INTO `tb_bed` VALUES (2, '内科', 'N002', 80, '占用');
INSERT INTO `tb_bed` VALUES (3, '内科', 'N003', 100, '空闲');
INSERT INTO `tb_bed` VALUES (4, '内科', 'N004', 100, '空闲');
INSERT INTO `tb_bed` VALUES (5, '外科', 'W001', 90, '占用');
INSERT INTO `tb_bed` VALUES (6, '外科', 'W002', 90, '空闲');
INSERT INTO `tb_bed` VALUES (7, '外科', 'W003', 120, '空闲');
INSERT INTO `tb_bed` VALUES (8, '儿科', 'E001', 70, '占用');
INSERT INTO `tb_bed` VALUES (9, '儿科', 'E002', 70, '空闲');
INSERT INTO `tb_bed` VALUES (10, '骨科', 'G001', 100, '占用');
INSERT INTO `tb_bed` VALUES (11, '骨科', 'G002', 100, '空闲');
INSERT INTO `tb_bed` VALUES (12, '骨科', 'G003', 130, '空闲');
INSERT INTO `tb_bed` VALUES (13, '妇产科', 'F001', 90, '空闲');
INSERT INTO `tb_bed` VALUES (14, '妇产科', 'F002', 90, '空闲');
INSERT INTO `tb_bed` VALUES (16, '内科', 'N100', 50, '空闲');

-- ----------------------------
-- Table structure for tb_charge
-- ----------------------------
DROP TABLE IF EXISTS `tb_charge`;
CREATE TABLE `tb_charge`  (
  `charge_id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NULL DEFAULT NULL,
  `charge_item` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `unit_price` double NULL DEFAULT NULL,
  `quantity` int NULL DEFAULT NULL,
  `amount` double NULL DEFAULT NULL,
  `charge_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`charge_id`) USING BTREE,
  INDEX `patient_id`(`patient_id` ASC) USING BTREE,
  CONSTRAINT `tb_charge_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `tb_patient` (`patient_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_charge
-- ----------------------------
INSERT INTO `tb_charge` VALUES (1, 1, '床位费', 80, 5, 400, '2026-06-26');
INSERT INTO `tb_charge` VALUES (2, 1, '检查费', 200, 1, 200, '2026-06-25');
INSERT INTO `tb_charge` VALUES (3, 2, '床位费', 80, 4, 320, '2026-06-27');
INSERT INTO `tb_charge` VALUES (4, 3, '手术费', 3000, 1, 3000, '2026-06-28');
INSERT INTO `tb_charge` VALUES (5, 4, '床位费', 70, 2, 140, '2026-06-29');
INSERT INTO `tb_charge` VALUES (6, 5, '床位费', 100, 1, 100, '2026-06-29');
INSERT INTO `tb_charge` VALUES (7, 6, '布洛芬胶囊', 15, 1, 15, '2026-06-30');
INSERT INTO `tb_charge` VALUES (8, 6, '诊查费', 50, 1, 50, '2026-06-30');
INSERT INTO `tb_charge` VALUES (9, 1, '化验费', 150, 1, 150, '2026-06-30');
INSERT INTO `tb_charge` VALUES (10, 6, '诊查费', 50, 1, 50, '2026-07-02');

-- ----------------------------
-- Table structure for tb_charge_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_charge_item`;
CREATE TABLE `tb_charge_item`  (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `item_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `default_price` double NOT NULL,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_charge_item
-- ----------------------------
INSERT INTO `tb_charge_item` VALUES (1, '诊查费', '诊疗', 50);
INSERT INTO `tb_charge_item` VALUES (2, '护理费', '护理', 30);
INSERT INTO `tb_charge_item` VALUES (3, '床位费', '住宿', 80);
INSERT INTO `tb_charge_item` VALUES (4, '检查费', '检查', 200);
INSERT INTO `tb_charge_item` VALUES (5, '化验费', '化验', 150);
INSERT INTO `tb_charge_item` VALUES (6, '手术费', '手术', 3000);
INSERT INTO `tb_charge_item` VALUES (7, '布洛芬胶囊', '药品', 15);
INSERT INTO `tb_charge_item` VALUES (8, '治疗费', '治疗', 100);
INSERT INTO `tb_charge_item` VALUES (9, '材料费', '材料', 50);
INSERT INTO `tb_charge_item` VALUES (10, '输液费', '护理', 20);
INSERT INTO `tb_charge_item` VALUES (11, '阿莫西林胶囊', '药品', 20);

-- ----------------------------
-- Table structure for tb_doctor
-- ----------------------------
DROP TABLE IF EXISTS `tb_doctor`;
CREATE TABLE `tb_doctor`  (
  `doctor_id` int NOT NULL AUTO_INCREMENT,
  `doctor_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `duty` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `birth_date` date NULL DEFAULT NULL,
  `work_date` date NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '123456',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`doctor_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_doctor
-- ----------------------------
INSERT INTO `tb_doctor` VALUES (1, '张伟', '男', '主任医师', '科主任', '内科', '1975-03-15', '2000-07-01', '1234', '15838446188', '317591774@qq.com');
INSERT INTO `tb_doctor` VALUES (2, '李娜', '女', '副主任医师', '科副主任', '内科', '1980-08-22', '2005-07-01', '123456', NULL, NULL);
INSERT INTO `tb_doctor` VALUES (3, '王强', '男', '主治医师', NULL, '外科', '1985-01-10', '2010-07-01', '123456', NULL, NULL);
INSERT INTO `tb_doctor` VALUES (4, '刘敏', '女', '主任医师', '科主任', '外科', '1978-11-05', '2002-07-01', '123456', NULL, NULL);
INSERT INTO `tb_doctor` VALUES (5, '陈刚', '男', '主治医师', NULL, '儿科', '1987-06-18', '2012-07-01', '123456', NULL, NULL);
INSERT INTO `tb_doctor` VALUES (6, '赵丽', '女', '副主任医师', '科副主任', '儿科', '1982-09-30', '2007-07-01', '123456', NULL, NULL);
INSERT INTO `tb_doctor` VALUES (7, '孙伟', '男', '主任医师', '科主任', '骨科', '1976-12-25', '2001-07-01', '123456', NULL, NULL);
INSERT INTO `tb_doctor` VALUES (8, '周婷', '女', '主治医师', NULL, '妇产科', '1988-04-12', '2013-07-01', '123456', NULL, NULL);

-- ----------------------------
-- Table structure for tb_patient
-- ----------------------------
DROP TABLE IF EXISTS `tb_patient`;
CREATE TABLE `tb_patient`  (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `bed_no` int NULL DEFAULT NULL,
  `patient_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `illness` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `attending_doctor_id` int NULL DEFAULT NULL,
  `admission_date` date NULL DEFAULT NULL,
  `discharge_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`patient_id`) USING BTREE,
  INDEX `bed_no`(`bed_no` ASC) USING BTREE,
  INDEX `attending_doctor_id`(`attending_doctor_id` ASC) USING BTREE,
  CONSTRAINT `tb_patient_ibfk_1` FOREIGN KEY (`bed_no`) REFERENCES `tb_bed` (`bed_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tb_patient_ibfk_2` FOREIGN KEY (`attending_doctor_id`) REFERENCES `tb_doctor` (`doctor_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_patient
-- ----------------------------
INSERT INTO `tb_patient` VALUES (1, '内科', 1, '王建福', '男', 45, '高血压', 1, '2026-06-25', NULL);
INSERT INTO `tb_patient` VALUES (2, '内科', 2, '冯萱雨', '女', 38, '糖尿病', 2, '2026-06-26', NULL);
INSERT INTO `tb_patient` VALUES (3, '外科', 5, '温伟伦', '男', 52, '阑尾炎', 3, '2026-06-27', NULL);
INSERT INTO `tb_patient` VALUES (4, '儿科', 8, '张心怡', '女', 8, '感冒发烧', 5, '2026-06-28', NULL);
INSERT INTO `tb_patient` VALUES (5, '骨科', 10, '黄志伟', '男', 60, '骨折', 7, '2026-06-29', NULL);
INSERT INTO `tb_patient` VALUES (6, '内科', NULL, '康乐行', '男', 22, '水痘', 1, '2026-06-30', NULL);

SET FOREIGN_KEY_CHECKS = 1;
