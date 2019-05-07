/*
SQLyog Ultimate v11.3 (64 bit)
MySQL - 5.6.21-log : Database - btc-db1
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`btc-db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `btc-db`;

/*Table structure for table `btc_account` */

DROP TABLE IF EXISTS `btc_account`;

CREATE TABLE `btc_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `coins` decimal(21,8) DEFAULT '0.00000000' COMMENT '货币数量',
  `can_use_coins` decimal(21,8) DEFAULT '0.00000000' COMMENT '可用货币数量(主要是用来计算收益货币的)',
  `coin_code` varchar(8) NOT NULL COMMENT '货币代码',
  `today_coins` decimal(21,8) DEFAULT '0.00000000' COMMENT '今日计息资产',
  `today_income` decimal(21,8) DEFAULT '0.00000000' COMMENT '收益(不是复利)',
  `total_income` decimal(21,8) DEFAULT '0.00000000' COMMENT '历史总收益(复利)',
  `today_real_income` decimal(21,8) DEFAULT '0.00000000' COMMENT '今日应发利息',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态[1.启用 0.禁用]',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `user_id_2` (`user_id`,`coin_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_account` */

/*Table structure for table `btc_account_back` */

DROP TABLE IF EXISTS `btc_account_back`;

CREATE TABLE `btc_account_back` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `coins` decimal(21,8) DEFAULT '0.00000000' COMMENT '货币数量',
  `can_use_coins` decimal(21,8) DEFAULT '0.00000000' COMMENT '可用货币数量(主要是用来计算收益货币的)',
  `coin_code` varchar(8) NOT NULL COMMENT '货币代码',
  `today_coins` decimal(21,8) DEFAULT '0.00000000' COMMENT '今日计息资产',
  `today_income` decimal(21,8) DEFAULT '0.00000000' COMMENT '收益(不是复利)',
  `total_income` decimal(21,8) DEFAULT '0.00000000' COMMENT '历史总收益(复利)',
  `today_real_income` decimal(21,8) DEFAULT '0.00000000' COMMENT '今日应发利息',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态[1.启用 0.禁用]',
  `count_date` date NOT NULL COMMENT '日期',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `user_id_2` (`user_id`,`coin_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_account_back` */

/*Table structure for table `btc_asset_add_record` */

DROP TABLE IF EXISTS `btc_asset_add_record`;

CREATE TABLE `btc_asset_add_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(24) NOT NULL COMMENT 'openId',
  `coin_code` varchar(8) NOT NULL COMMENT '资产名称',
  `coins` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '资产数量',
  `org_total_coins` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '分发之前资产',
  `bind_no` varchar(128) NOT NULL COMMENT '绑定流水号',
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `bind_no` (`bind_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_asset_add_record` */

/*Table structure for table `btc_asset_add_record_temp` */

DROP TABLE IF EXISTS `btc_asset_add_record_temp`;

CREATE TABLE `btc_asset_add_record_temp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(24) NOT NULL COMMENT 'openId',
  `is_exist` int(1) NOT NULL DEFAULT '0' COMMENT '是否存在',
  `coin_code` varchar(8) NOT NULL COMMENT '资产名称',
  `coins` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '资产数量',
  `org_total_coins` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '分发之前资产',
  `bind_no` varchar(128) NOT NULL COMMENT '绑定流水号',
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_asset_add_record_temp` */

/*Table structure for table `btc_asset_income_record` */

DROP TABLE IF EXISTS `btc_asset_income_record`;

CREATE TABLE `btc_asset_income_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coin_code` varchar(8) NOT NULL COMMENT '资产代码',
  `coin_income` decimal(21,8) NOT NULL COMMENT '收益',
  `usdt_init` decimal(21,8) NOT NULL COMMENT 'usdt单位',
  `usdt_income` decimal(21,8) NOT NULL COMMENT 'usdt收益',
  `count_date` date NOT NULL COMMENT '时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `coin_code` (`coin_code`,`count_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_asset_income_record` */

/*Table structure for table `btc_asset_total` */

DROP TABLE IF EXISTS `btc_asset_total`;

CREATE TABLE `btc_asset_total` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `coin_code` varchar(8) NOT NULL COMMENT '资产代码',
  `coins` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '资产数量',
  `today_income` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '今日收益',
  `total_income` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '总收益',
  `yesterday_coins` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '昨日数量',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_asset_total` */

/*Table structure for table `btc_coin_rate_record` */

DROP TABLE IF EXISTS `btc_coin_rate_record`;

CREATE TABLE `btc_coin_rate_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `coin_code` varchar(8) NOT NULL COMMENT '货币代码',
  `coin_day_rate` decimal(8,6) NOT NULL DEFAULT '0.000000' COMMENT '日收益收益率',
  `coin_year_rate` decimal(8,6) DEFAULT '0.000000' COMMENT '年收益率',
  `usdt_unit` decimal(21,8) DEFAULT '0.00000000' COMMENT 'usdt单位',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态[1.启用 0.禁用]',
  `apply_date` date DEFAULT NULL COMMENT '应用日期',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `coin_code` (`coin_code`,`gmt_create`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_coin_rate_record` */

/*Table structure for table `btc_coin_record` */

DROP TABLE IF EXISTS `btc_coin_record`;

CREATE TABLE `btc_coin_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `coins` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '货币数量',
  `coin_code` varchar(8) NOT NULL COMMENT '货币代码',
  `total_coins` decimal(21,8) DEFAULT NULL COMMENT '交易前总数',
  `trade_type` int(1) NOT NULL COMMENT '交易类型[1.进 2.出]',
  `status` int(1) NOT NULL DEFAULT '3' COMMENT '状态[1.成功 2.失败 3.正在处理]',
  `is_system_operate` int(1) NOT NULL DEFAULT '1' COMMENT '是否是系统操作[1.是 0.否]',
  `operate_user_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '操作人员',
  `order_no` varchar(128) NOT NULL COMMENT '流水号',
  `bind_no` varchar(128) DEFAULT NULL COMMENT '绑定业务流水号',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `account_id` (`account_id`),
  KEY `user_id_2` (`user_id`,`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_coin_record` */

/*Table structure for table `btc_user_day_total_coin_record` */

DROP TABLE IF EXISTS `btc_user_day_total_coin_record`;

CREATE TABLE `btc_user_day_total_coin_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `coin_code` varchar(8) NOT NULL COMMENT '币种',
  `coins` decimal(21,8) NOT NULL COMMENT '货币数量',
  `cost_amount` decimal(21,8) NOT NULL COMMENT '扣除金额[用户提现的时候会扣除]',
  `cost_amount_desc` varchar(1024) NOT NULL COMMENT '扣除金额描述',
  `count_date` date NOT NULL COMMENT '日期',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `user_id_2` (`user_id`,`account_id`,`coin_code`,`count_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_user_day_total_coin_record` */

/*Table structure for table `btc_user_income_record` */

DROP TABLE IF EXISTS `btc_user_income_record`;

CREATE TABLE `btc_user_income_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `coins` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '计息的数量',
  `coin_income` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '收益数量',
  `coin_day_rate` decimal(8,6) NOT NULL DEFAULT '0.000000' COMMENT '日收益率',
  `coin_year_rate` decimal(8,6) NOT NULL DEFAULT '0.000000' COMMENT '年收益率',
  `coin_code` varchar(8) NOT NULL COMMENT '货币代码',
  `usdt_unit` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT 'usdt单位',
  `usdt_income` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT 'usdt收益',
  `is_add_income` int(1) NOT NULL DEFAULT '0' COMMENT '是否增加收益',
  `count_date` date DEFAULT NULL COMMENT '计算日期',
  `bind_no` varchar(128) NOT NULL COMMENT '绑定流水号(主要是和btc_user_income_verify流水号对应)',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_user_income_record` */

/*Table structure for table `btc_user_income_verify` */

DROP TABLE IF EXISTS `btc_user_income_verify`;

CREATE TABLE `btc_user_income_verify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `coins` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '计息的数量',
  `coin_income` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '收益数量',
  `coin_day_rate` decimal(8,6) NOT NULL DEFAULT '0.000000' COMMENT '日收益率',
  `coin_year_rate` decimal(8,6) NOT NULL DEFAULT '0.000000' COMMENT '年收益率',
  `coin_code` varchar(8) NOT NULL COMMENT '货币代码',
  `count_date` date NOT NULL COMMENT '计息时间',
  `usdt_unit` decimal(21,8) DEFAULT '0.00000000' COMMENT 'udst单位',
  `usdt_income` decimal(21,8) DEFAULT '0.00000000' COMMENT 'usdt收益',
  `is_system_operate` int(1) DEFAULT '0' COMMENT '是否系统操作',
  `operate_id` bigint(1) DEFAULT '-1' COMMENT '操作id[-1是系统]',
  `order_no` varchar(128) DEFAULT NULL COMMENT '流水号',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_user_income_verify` */

/*Table structure for table `btc_user_income_verify_temp` */

DROP TABLE IF EXISTS `btc_user_income_verify_temp`;

CREATE TABLE `btc_user_income_verify_temp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `coins` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '计息的数量',
  `coin_income` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT '收益数量',
  `coin_day_rate` decimal(8,6) NOT NULL DEFAULT '0.000000' COMMENT '日收益率',
  `coin_year_rate` decimal(8,6) NOT NULL DEFAULT '0.000000' COMMENT '年收益率',
  `coin_code` varchar(8) NOT NULL COMMENT '货币代码',
  `count_date` date NOT NULL COMMENT '计息时间',
  `usdt_unit` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT 'udst单位',
  `usdt_income` decimal(21,8) NOT NULL DEFAULT '0.00000000' COMMENT 'usdt收益',
  `is_system_operate` int(1) NOT NULL DEFAULT '0' COMMENT '是否系统操作',
  `operate_id` bigint(1) NOT NULL DEFAULT '-1' COMMENT '操作id[-1是系统]',
  `order_no` varchar(128) NOT NULL COMMENT '流水号',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `btc_user_income_verify_temp` */

/*Table structure for table `btc_user_info` */

DROP TABLE IF EXISTS `btc_user_info`;

CREATE TABLE `btc_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `open_id` varchar(128) NOT NULL COMMENT 'openid',
  `token` varchar(512) DEFAULT NULL COMMENT 'token',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登录时间',
  `head_img` varchar(512) DEFAULT NULL COMMENT '用户头像',
  `gmt_create` datetime NOT NULL COMMENT '修改是时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

/*Data for the table `btc_user_info` */

/*Table structure for table `sys_coins_dic` */

DROP TABLE IF EXISTS `sys_coins_dic`;

CREATE TABLE `sys_coins_dic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `coin_code` varchar(8) NOT NULL COMMENT '货币类型',
  `coin_icon` varchar(256) NOT NULL COMMENT '图标',
  `coin_color` varchar(36) DEFAULT NULL COMMENT '颜色',
  `min_income_count` decimal(21,8) DEFAULT NULL COMMENT '最小计息数量',
  `min_turn_in_count` decimal(21,8) DEFAULT NULL COMMENT '最小转入数量',
  `min_turn_out_count` decimal(21,8) DEFAULT NULL COMMENT '最小转出数量',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除[1.是 0.否]',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用[1.启用 0.禁用]',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_coins_dic` */

/*Table structure for table `sys_task_job` */

DROP TABLE IF EXISTS `sys_task_job`;

CREATE TABLE `sys_task_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `start_date` datetime NOT NULL COMMENT '开始时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `job_type` int(2) NOT NULL COMMENT '任务类型',
  `count_date` date DEFAULT NULL COMMENT '计算日期',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `is_success` int(1) NOT NULL DEFAULT '1' COMMENT '是否成功',
  `error_log` text COMMENT '错误日志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_task_job` */

/*Table structure for table `system_first_item` */

DROP TABLE IF EXISTS `system_first_item`;

CREATE TABLE `system_first_item` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(100) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '一级菜单',
  `item_name` varchar(100) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '菜单名称',
  `status` int(1) DEFAULT '1' COMMENT '0未启用 1启用',
  `order_index` int(11) DEFAULT NULL COMMENT '顺序',
  `level` int(1) DEFAULT NULL COMMENT '1普通 2高级 3超级',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

/*Data for the table `system_first_item` */

insert  into `system_first_item`(`id`,`url`,`item_name`,`status`,`order_index`,`level`,`create_date`) values (1,NULL,'其他管理',1,7,1,'2019-03-12 12:44:21'),(2,NULL,'资产管理',1,6,NULL,'2019-03-26 22:15:49'),(3,NULL,'流水管理',1,5,NULL,'2019-03-28 09:58:15'),(4,NULL,'资产分发',1,4,NULL,'2019-03-29 17:51:55'),(5,NULL,'用户管理',1,2,NULL,'2019-04-02 19:51:11'),(6,NULL,'首页',1,1,NULL,'2019-04-02 23:05:20'),(7,NULL,'资产管理',1,3,NULL,'2019-04-19 11:18:41');

/*Table structure for table `system_second_item` */

DROP TABLE IF EXISTS `system_second_item`;

CREATE TABLE `system_second_item` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `first_id` bigint(11) DEFAULT NULL COMMENT '一级菜单Id',
  `url` varchar(100) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '二级菜单',
  `item_name` varchar(100) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '菜单名称',
  `status` int(1) DEFAULT '1' COMMENT '0未启用 1启用',
  `order_index` int(11) DEFAULT NULL COMMENT '顺序',
  `level` int(1) DEFAULT NULL COMMENT '1普通 2高级 3超级',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10018 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

/*Data for the table `system_second_item` */

insert  into `system_second_item`(`id`,`first_id`,`url`,`item_name`,`status`,`order_index`,`level`,`create_date`) values (10000,1,'/admin/adminPage','用户管理',1,1,NULL,'2019-03-12 12:45:05'),(10001,1,'/admin/systemFirstItem/systemFirstItemPage','菜单管理',1,2,NULL,'2019-03-12 12:46:46'),(10004,2,'/admin/system/toSysCoinsDicHtml','资产配置',1,1,NULL,'2019-03-26 22:18:10'),(10005,2,'/admin/system/coinRate','利率配置',1,2,NULL,'2019-03-27 20:58:09'),(10006,3,'/admin/coinRecord/index','资金流水',1,1,NULL,'2019-03-28 09:59:44'),(10008,3,'/admin/userIncomeRecord/index','收益明细',1,2,NULL,'2019-03-28 12:27:04'),(10009,3,'/admin/userIncomeVerify/index','收益审核流水',1,3,NULL,'2019-03-29 17:51:03'),(10010,4,'/admin/userIncomeVerifyTemp/index','资产分发',1,1,NULL,'2019-03-29 17:52:56'),(10011,5,'/admin/user/index','用户查看',1,1,NULL,'2019-04-02 19:52:13'),(10012,6,'/admin/home','首页',1,1,NULL,'2019-04-02 23:05:36'),(10013,5,'/admin/user/account/index','资产管理',1,2,NULL,'2019-04-12 17:04:28'),(10014,5,'/admin/user/accountHis/index','历史账户',1,3,NULL,'2019-04-15 12:40:46'),(10015,4,'/admin/userIncomeVerifyTemp/indexOld','历史收益计算',1,3,NULL,'2019-04-19 11:16:56'),(10016,7,'/admin/asset/shareAsset','资产分发流水',1,1,NULL,'2019-04-19 11:20:04'),(10017,7,'/admin/asset/shareAssetTemp','资产分发',1,2,NULL,'2019-04-19 11:20:29');

/*Table structure for table `u_user` */

DROP TABLE IF EXISTS `u_user`;

CREATE TABLE `u_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `token` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'token',
  `is_expired` int(1) DEFAULT '1' COMMENT '密码是否过期(1-正常,0-已过期)',
  `is_locked` int(1) DEFAULT '0' COMMENT '是否锁定(0未锁定,1-锁定)',
  `is_enable` int(1) DEFAULT '0' COMMENT '是否禁用(0-否,1-是)',
  `mobile` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `fullname` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '用户全称',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_error_count` int(11) DEFAULT NULL COMMENT '登录错误次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `channel` char(2) COLLATE utf8_bin DEFAULT '0' COMMENT '渠道【默认是0，代表所有渠道】',
  `level` int(2) DEFAULT NULL COMMENT '级别[3.超级管理员 2.普通管理员 1.一般管理员]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

/*Data for the table `u_user` */

insert  into `u_user`(`id`,`user_name`,`password`,`token`,`is_expired`,`is_locked`,`is_enable`,`mobile`,`fullname`,`last_login_time`,`login_error_count`,`create_time`,`channel`,`level`) values (1,'admin','$2a$10$dVgPero/xYP.lazvFAVYh.ziUAx1aPLVJNKTAfLdbOn4UtBKbvUla','544426e22ce64a28b265d4c40ddaf13b',1,0,0,'15800379655','2','2019-05-06 21:16:19',NULL,'2019-03-13 12:30:10','0',3);

/*Table structure for table `u_user_role` */

DROP TABLE IF EXISTS `u_user_role`;

CREATE TABLE `u_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `first_item_id` bigint(20) NOT NULL COMMENT '以及菜单id',
  `second_item_id` bigint(20) NOT NULL COMMENT '二级菜单id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `u_user_role` */

insert  into `u_user_role`(`user_id`,`first_item_id`,`second_item_id`) values (1,6,10012),(1,5,10011),(1,5,10013),(1,5,10014),(1,4,10010),(1,3,10006),(1,3,10008),(1,3,10009),(1,2,10004),(1,2,10005),(1,1,10000),(1,1,10001);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
