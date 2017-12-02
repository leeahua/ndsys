/*
Navicat MySQL Data Transfer

Source Server         : 172.20.10.32
Source Server Version : 50711
Source Host           : 172.20.10.32:3306
Source Database       : hexin_user

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2017-10-20 16:36:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for demo
-- ----------------------------
DROP TABLE IF EXISTS `demo`;
CREATE TABLE `demo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of demo
-- ----------------------------
INSERT INTO `demo` VALUES ('1', 'sdsad');

-- ----------------------------
-- Table structure for system_platform
-- ----------------------------
DROP TABLE IF EXISTS `system_platform`;
CREATE TABLE `system_platform` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `platform_name` varchar(64) NOT NULL COMMENT '平台名称',
  `platform_code` varchar(32) NOT NULL COMMENT '平台编码',
  `platform_status` int(11) NOT NULL DEFAULT '0' COMMENT '平台状态  0: 未激活  1: 激活',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_platform_code` (`platform_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_platform
-- ----------------------------
INSERT INTO `system_platform` VALUES ('1', '线上系统', '0002', '1', '2017-10-17 16:39:59', '2017-10-20 16:16:53');
INSERT INTO `system_platform` VALUES ('3', '线上系统2', '0001', '1', '2017-10-20 16:07:13', '2017-10-20 16:07:13');
INSERT INTO `system_platform` VALUES ('4', '线上系统3', '0003', '1', '2017-10-20 16:09:53', '2017-10-20 16:14:59');
INSERT INTO `system_platform` VALUES ('5', '线上系统4', '0004', '0', '2017-10-20 16:11:48', '2017-10-20 16:19:26');
