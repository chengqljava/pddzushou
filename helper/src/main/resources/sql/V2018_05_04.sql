DROP TABLE IF EXISTS `t_rank_record`;
CREATE TABLE `t_rank_record` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `keyword_id` varchar(32) NOT NULL,
  `rank` int(50) NOT NULL COMMENT '排名',
  `create_time` datetime NOT NULL,
  `resource_niche_flag` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '关键词排名';

DROP TABLE IF EXISTS `t_keyword`;
CREATE TABLE `t_keyword` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `good_id` varchar(32) NOT NULL COMMENT 't_good id',
  `name` varchar(50) NOT NULL COMMENT '关键词',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `ranking` varchar(20) DEFAULT NULL COMMENT '最近排名',
  `ranking_time` datetime DEFAULT NULL COMMENT '排名时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '关键词';


DROP TABLE IF EXISTS `t_good`;
CREATE TABLE `t_good` (
  `id` varchar(50) NOT NULL,
  `pin_good_id` varchar(150) NOT NULL,
  `name` varchar(200) NOT NULL,
  `img_url` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `mall_id` varchar(20) DEFAULT NULL COMMENT '店ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT '商品';
