/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50560
Source Host           : 127.0.0.1:3306
Source Database       : feihong

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2019-05-01 00:15:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_meter
-- ----------------------------
DROP TABLE IF EXISTS `t_meter`;
CREATE TABLE `t_meter` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `factory_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
