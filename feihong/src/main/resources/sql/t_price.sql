/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50560
Source Host           : 127.0.0.1:3306
Source Database       : feihong

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2019-05-01 00:15:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_price
-- ----------------------------
DROP TABLE IF EXISTS `t_price`;
CREATE TABLE `t_price` (
  `id` int(11) NOT NULL COMMENT '时间段',
  `price` float(10,2) NOT NULL COMMENT '价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '电费价格';
