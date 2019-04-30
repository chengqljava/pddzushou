/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50560
Source Host           : 127.0.0.1:3306
Source Database       : feihong

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2019-05-01 00:14:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_degrees_record
-- ----------------------------
DROP TABLE IF EXISTS `t_degrees_record`;
CREATE TABLE `t_degrees_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `meter_id` bigint(20) NOT NULL COMMENT '电表ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `meter_number` float NOT NULL,
  `acquisition_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
