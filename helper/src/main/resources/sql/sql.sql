-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` varchar(64) NOT NULL,
  `goods_id` varchar(20) NOT NULL,
  `amount` varchar(22) NOT NULL COMMENT '数量',
  `create_time` datetime NOT NULL,
  `shop_id` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_shop`
-- ----------------------------
DROP TABLE IF EXISTS `t_shop`;
CREATE TABLE `t_shop` (
  `id` varchar(64) NOT NULL,
  `name` varchar(50) NOT NULL,
  `key` varchar(20) NOT NULL,
  `secret` varchar(60) NOT NULL,
  `create_time` datetime NOT NULL,
  `user_id` varchar(64) NOT NULL,
  `type` tinyint(3) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(64) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `valid_end_time` datetime DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `salt` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t_user` VALUES ('59f07eab064bf754d84fa70d', '18500197557', '3D39867BD35FFEABC9CD6D6D7521BAF2D6F63043', '18500197557', '0', '2017-10-25 20:08:11', null, 'admin', 'C4632909EA48B079488B40BDB0766BBB4ABBEA50');

