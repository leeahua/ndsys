/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : nongda

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-11-02 08:26:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `pig_level`
-- ----------------------------
DROP TABLE IF EXISTS `pig_level`;
CREATE TABLE `pig_level` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `level1` tinyint(10) unsigned NOT NULL COMMENT '等级一',
  `level2` double(5,2) unsigned NOT NULL COMMENT '等级2',
  `level3` double(5,2) unsigned NOT NULL COMMENT '等级三',
  `level4` double(5,2) NOT NULL COMMENT '等级四',
  `level5` double(5,2) NOT NULL COMMENT '等级五',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pig_level
-- ----------------------------
INSERT INTO `pig_level` VALUES ('3', '1', '2.00', '3.00', '4.00', '5.00', 'admain', '2018-10-27', 'admin', '2018-10-27');

-- ----------------------------
-- Table structure for `pig_pound`
-- ----------------------------
DROP TABLE IF EXISTS `pig_pound`;
CREATE TABLE `pig_pound` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `botpounds` double NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `batch_num` varchar(32) DEFAULT NULL COMMENT '批次号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pig_pound
-- ----------------------------
INSERT INTO `pig_pound` VALUES ('2', '14', '2018-10-28 13:24:22', '100014');

-- ----------------------------
-- Table structure for `pig_weight`
-- ----------------------------
DROP TABLE IF EXISTS `pig_weight`;
CREATE TABLE `pig_weight` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pig_batch_no` varchar(32) DEFAULT NULL COMMENT '批次号',
  `pig_num` varchar(20) NOT NULL COMMENT '序号',
  `pig_width` decimal(10,2) DEFAULT NULL COMMENT '宽度',
  `pig_weight` decimal(10,2) DEFAULT NULL COMMENT '重量',
  `pig_level` varchar(255) DEFAULT NULL COMMENT '猪肉等级',
  `pig_color` varchar(32) DEFAULT NULL COMMENT '颜色',
  `charge_man` varchar(32) DEFAULT NULL COMMENT '负责人',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_batchno_num` (`pig_batch_no`,`pig_num`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=864 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pig_weight
-- ----------------------------
INSERT INTO `pig_weight` VALUES ('847', '100013', '00001', null, '79.61', null, null, 'admin', '2018-10-28 13:23:40', '2018-10-28 13:23:40');
INSERT INTO `pig_weight` VALUES ('848', '100013', '00002', null, '72.38', null, null, 'admin', '2018-10-28 13:23:40', '2018-10-28 13:23:40');
INSERT INTO `pig_weight` VALUES ('849', '100013', '00003', null, '67.94', null, null, 'admin', '2018-10-28 13:23:40', '2018-10-28 13:23:40');
INSERT INTO `pig_weight` VALUES ('850', '100013', '00004', null, '75.39', null, null, 'admin', '2018-10-28 13:23:41', '2018-10-28 13:23:41');
INSERT INTO `pig_weight` VALUES ('851', '100013', '00005', null, '72.04', null, null, 'admin', '2018-10-28 13:23:41', '2018-10-28 13:23:41');
INSERT INTO `pig_weight` VALUES ('852', '100013', '00006', null, '75.11', null, null, 'admin', '2018-10-28 13:23:41', '2018-10-28 13:23:41');
INSERT INTO `pig_weight` VALUES ('853', '100013', '00007', null, '75.64', null, null, 'admin', '2018-10-28 13:23:41', '2018-10-28 13:23:41');
INSERT INTO `pig_weight` VALUES ('854', '100014', '00001', null, '79.61', null, null, 'admin', '2018-10-28 13:24:38', '2018-10-28 13:24:38');
INSERT INTO `pig_weight` VALUES ('855', '100014', '00002', null, '72.38', null, null, 'admin', '2018-10-28 13:24:38', '2018-10-28 13:24:38');
INSERT INTO `pig_weight` VALUES ('856', '100014', '00003', null, '71.32', null, null, 'admin', '2018-10-28 13:24:38', '2018-10-28 13:24:38');
INSERT INTO `pig_weight` VALUES ('857', '100014', '00004', null, '57.00', null, null, 'admin', '2018-10-28 13:24:38', '2018-10-28 13:24:38');
INSERT INTO `pig_weight` VALUES ('858', '100014', '00005', null, '64.90', null, null, 'admin', '2018-10-28 13:24:38', '2018-10-28 13:24:38');
INSERT INTO `pig_weight` VALUES ('859', '100014', '00006', null, '66.03', null, null, 'admin', '2018-10-28 13:24:38', '2018-10-28 13:24:38');
INSERT INTO `pig_weight` VALUES ('860', '100014', '00007', null, '73.73', null, null, 'admin', '2018-10-28 13:24:39', '2018-10-28 13:24:39');
INSERT INTO `pig_weight` VALUES ('861', '100014', '00008', null, '60.99', null, null, 'admin', '2018-10-28 13:24:39', '2018-10-28 13:24:39');
INSERT INTO `pig_weight` VALUES ('862', '100014', '00009', null, '80.56', null, null, 'admin', '2018-10-28 13:24:39', '2018-10-28 13:24:39');
INSERT INTO `pig_weight` VALUES ('863', '100014', '00010', null, '61.83', null, null, 'admin', '2018-10-28 13:24:39', '2018-10-28 13:24:39');

-- ----------------------------
-- Table structure for `pig_width`
-- ----------------------------
DROP TABLE IF EXISTS `pig_width`;
CREATE TABLE `pig_width` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pig_batch_no` varchar(32) NOT NULL COMMENT '批次号',
  `pig_num` varchar(20) NOT NULL COMMENT '序号',
  `pig_width` decimal(10,2) NOT NULL COMMENT '宽度',
  `pig_level` varchar(255) DEFAULT NULL COMMENT '猪肉等级',
  `pig_color` varchar(32) DEFAULT NULL COMMENT '颜色',
  `charge_man` varchar(32) DEFAULT NULL COMMENT '负责人',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `pig_weight` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_batchno_num` (`pig_batch_no`,`pig_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pig_width
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `realname` varchar(8) DEFAULT NULL COMMENT '角色',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_user_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'hoIPI+1VeV3GFzJijYH9cQQzJwLy4WG+', '系统管理员', '1');
INSERT INTO `sys_user` VALUES ('4', 'test', 'oqGiG3w2C/s4l945xI++My4Wpv2cCyLi', '测试', '1');
