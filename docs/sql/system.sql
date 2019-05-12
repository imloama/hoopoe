/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : hoopoe

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-05-12 20:46:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `PARENT_ID` bigint(20) NOT NULL COMMENT '上级部门ID',
  `code` varchar(50) NOT NULL,
  `NAME` varchar(100) NOT NULL COMMENT '部门名称',
  `ORDER_BY` double(20,0) DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `fullname` varchar(125) NOT NULL COMMENT '全称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '001', '戴胜鸟科技', '1', '2019-04-14 21:57:58', null, '戴胜鸟科技');
INSERT INTO `sys_dept` VALUES ('2', '1', '002', '研发部2', '2', '2019-04-14 21:58:20', '2019-04-25 21:39:03', '研发部');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `code` varchar(20) NOT NULL COMMENT '键',
  `name` varchar(100) NOT NULL COMMENT '值',
  `v` varchar(255) NOT NULL COMMENT '字段名称',
  `memo` varchar(100) DEFAULT NULL COMMENT '表名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', 'sex', '性别', '1=男,0=女,2=保密', '');
INSERT INTO `sys_dict` VALUES ('2', 'status', '状态', '0=有效,1=冻结', '');

-- ----------------------------
-- Table structure for sys_dict_ref
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_ref`;
CREATE TABLE `sys_dict_ref` (
  `id` bigint(30) NOT NULL,
  `dict_id` bigint(30) NOT NULL,
  `table_name` varchar(20) NOT NULL COMMENT '表名',
  `field_name` varchar(50) NOT NULL COMMENT '字段名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dict_ref
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
  `PARENT_ID` bigint(20) NOT NULL COMMENT '上级菜单ID',
  `NAME` varchar(50) NOT NULL COMMENT '菜单/按钮名称',
  `PATH` varchar(255) DEFAULT NULL COMMENT '对应路由path',
  `COMPONENT` varchar(255) DEFAULT NULL COMMENT '对应路由组件component',
  `CODE` varchar(50) DEFAULT NULL COMMENT '权限标识',
  `ICON` varchar(50) DEFAULT NULL COMMENT '图标',
  `TYPE` char(2) NOT NULL COMMENT '类型 0菜单 1按钮',
  `ORDER_BY` double(20,0) DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '/system', 'PageView', 'system', 'appstore-o', '0', '1', '2017-12-27 16:39:07', '2019-01-05 11:13:14');
INSERT INTO `sys_menu` VALUES ('2', '0', '系统监控', '/monitor', 'PageView', 'monitor', 'dashboard', '0', '2', '2017-12-27 16:45:51', '2019-01-23 06:27:12');
INSERT INTO `sys_menu` VALUES ('3', '1', '用户管理', '/sys/user', 'sys/user/User', 'user:view', '', '0', '1', '2017-12-27 16:47:13', '2019-01-22 06:45:55');
INSERT INTO `sys_menu` VALUES ('4', '1', '角色管理', '/sys/role', 'sys/role/Role', 'role:view', '', '0', '2', '2017-12-27 16:48:09', '2018-04-25 09:01:12');
INSERT INTO `sys_menu` VALUES ('5', '1', '菜单管理', '/sys/menu', 'sys/menu/Menu', 'menu:view', '', '0', '3', '2017-12-27 16:48:57', '2018-04-25 09:01:30');
INSERT INTO `sys_menu` VALUES ('6', '1', '部门管理', '/sys/dept', 'sys/dept/Dept', 'dept:view', '', '0', '4', '2017-12-27 16:57:33', '2018-04-25 09:01:40');
INSERT INTO `sys_menu` VALUES ('8', '2', '在线用户', '/monitor/online', 'monitor/Online', 'user:online', '', '0', '1', '2017-12-27 16:59:33', '2018-04-25 09:02:04');
INSERT INTO `sys_menu` VALUES ('10', '2', '系统日志', '/monitor/systemlog', 'monitor/SystemLog', 'log:view', '', '0', '2', '2017-12-27 17:00:50', '2018-04-25 09:02:18');
INSERT INTO `sys_menu` VALUES ('11', '3', '新增用户', '', '', 'user:add', null, '1', null, '2017-12-27 17:02:58', null);
INSERT INTO `sys_menu` VALUES ('12', '3', '修改用户', '', '', 'user:update', null, '1', null, '2017-12-27 17:04:07', null);
INSERT INTO `sys_menu` VALUES ('13', '3', '删除用户', '', '', 'user:delete', null, '1', null, '2017-12-27 17:04:58', null);
INSERT INTO `sys_menu` VALUES ('14', '4', '新增角色', '', '', 'role:add', null, '1', null, '2017-12-27 17:06:38', null);
INSERT INTO `sys_menu` VALUES ('15', '4', '修改角色', '', '', 'role:update', null, '1', null, '2017-12-27 17:06:38', null);
INSERT INTO `sys_menu` VALUES ('16', '4', '删除角色', '', '', 'role:delete', null, '1', null, '2017-12-27 17:06:38', null);
INSERT INTO `sys_menu` VALUES ('17', '5', '新增菜单', '', '', 'menu:add', null, '1', null, '2017-12-27 17:08:02', null);
INSERT INTO `sys_menu` VALUES ('18', '5', '修改菜单', '', '', 'menu:update', null, '1', null, '2017-12-27 17:08:02', null);
INSERT INTO `sys_menu` VALUES ('19', '5', '删除菜单', '', '', 'menu:delete', null, '1', null, '2017-12-27 17:08:02', null);
INSERT INTO `sys_menu` VALUES ('20', '6', '新增部门', '', '', 'dept:add', null, '1', null, '2017-12-27 17:09:24', null);
INSERT INTO `sys_menu` VALUES ('21', '6', '修改部门', '', '', 'dept:update', null, '1', null, '2017-12-27 17:09:24', null);
INSERT INTO `sys_menu` VALUES ('22', '6', '删除部门', '', '', 'dept:delete', null, '1', null, '2017-12-27 17:09:24', null);
INSERT INTO `sys_menu` VALUES ('23', '8', '踢出用户', '', '', 'user:kickout', null, '1', null, '2017-12-27 17:11:13', null);
INSERT INTO `sys_menu` VALUES ('24', '10', '删除日志', '', '', 'log:delete', null, '1', null, '2017-12-27 17:11:45', null);
INSERT INTO `sys_menu` VALUES ('64', '1', '字典管理', '/sys/dict', 'sys/dict/Dict', 'dict:view', '', '0', '5', '2018-01-18 10:38:25', '2018-04-25 09:01:50');
INSERT INTO `sys_menu` VALUES ('65', '64', '新增字典', '', '', 'dict:add', null, '1', null, '2018-01-18 19:10:08', null);
INSERT INTO `sys_menu` VALUES ('66', '64', '修改字典', '', '', 'dict:update', null, '1', null, '2018-01-18 19:10:27', null);
INSERT INTO `sys_menu` VALUES ('67', '64', '删除字典', '', '', 'dict:delete', null, '1', null, '2018-01-18 19:10:47', null);
INSERT INTO `sys_menu` VALUES ('101', '0', '任务调度', '/job', 'PageView', 'job', 'clock-circle-o', '0', '3', '2018-01-11 15:52:57', null);
INSERT INTO `sys_menu` VALUES ('102', '101', '定时任务', '/job/job', 'quartz/job/Job', 'job:view', '', '0', '1', '2018-02-24 15:53:53', '2019-01-22 05:42:50');
INSERT INTO `sys_menu` VALUES ('103', '102', '新增任务', '', '', 'job:add', null, '1', null, '2018-02-24 15:55:10', null);
INSERT INTO `sys_menu` VALUES ('104', '102', '修改任务', '', '', 'job:update', null, '1', null, '2018-02-24 15:55:53', null);
INSERT INTO `sys_menu` VALUES ('105', '102', '删除任务', '', '', 'job:delete', null, '1', null, '2018-02-24 15:56:18', null);
INSERT INTO `sys_menu` VALUES ('106', '102', '暂停任务', '', '', 'job:pause', null, '1', null, '2018-02-24 15:57:08', null);
INSERT INTO `sys_menu` VALUES ('107', '102', '恢复任务', '', '', 'job:resume', null, '1', null, '2018-02-24 15:58:21', null);
INSERT INTO `sys_menu` VALUES ('108', '102', '立即执行任务', '', '', 'job:run', null, '1', null, '2018-02-24 15:59:45', null);
INSERT INTO `sys_menu` VALUES ('109', '101', '调度日志', '/job/log', 'quartz/log/JobLog', 'jobLog:view', '', '0', '2', '2018-02-24 16:00:45', '2019-01-22 05:42:59');
INSERT INTO `sys_menu` VALUES ('110', '109', '删除日志', '', '', 'jobLog:delete', null, '1', null, '2018-02-24 16:01:21', null);
INSERT INTO `sys_menu` VALUES ('113', '2', 'Redis监控', '/monitor/redis/info', 'monitor/RedisInfo', 'redis', '', '0', '3', '2018-06-28 14:29:42', null);
INSERT INTO `sys_menu` VALUES ('121', '2', '请求追踪', '/monitor/httptrace', 'monitor/Httptrace', 'httptrace', null, '0', '4', '2019-01-18 02:30:29', null);
INSERT INTO `sys_menu` VALUES ('122', '2', '系统信息', '/monitor/system', 'EmptyPageView', 'system', null, '0', '5', '2019-01-18 02:31:48', '2019-01-18 02:39:46');
INSERT INTO `sys_menu` VALUES ('123', '122', 'Tomcat信息', '/monitor/system/tomcatinfo', 'monitor/TomcatInfo', 'httpserver', null, '0', '2', '2019-01-18 02:32:53', '2019-01-18 02:46:57');
INSERT INTO `sys_menu` VALUES ('124', '122', 'JVM信息', '/monitor/system/jvminfo', 'monitor/JvmInfo', 'jvm', null, '0', '1', '2019-01-18 02:33:30', '2019-01-18 02:46:51');
INSERT INTO `sys_menu` VALUES ('127', '122', '服务器信息', '/monitor/system/info', 'monitor/SystemInfo', 'server', null, '0', '3', '2019-01-21 07:53:43', '2019-01-21 07:57:00');
INSERT INTO `sys_menu` VALUES ('128', '0', '其他模块', '/others', 'PageView', 'other', 'coffee', '0', '5', '2019-01-22 06:49:59', '2019-01-22 06:50:13');
INSERT INTO `sys_menu` VALUES ('129', '128', '导入导出', '/others/excel', 'others/Excel', 'excel', null, '0', '1', '2019-01-22 06:51:36', '2019-01-22 07:06:45');
INSERT INTO `sys_menu` VALUES ('130', '3', '导出Excel', null, null, 'user:export', null, '1', null, '2019-01-23 06:35:16', null);
INSERT INTO `sys_menu` VALUES ('131', '4', '导出Excel', null, null, 'role:export', null, '1', null, '2019-01-23 06:35:36', null);
INSERT INTO `sys_menu` VALUES ('132', '5', '导出Excel', null, null, 'menu:export', null, '1', null, '2019-01-23 06:36:05', null);
INSERT INTO `sys_menu` VALUES ('133', '6', '导出Excel', null, null, 'dept:export', null, '1', null, '2019-01-23 06:36:25', null);
INSERT INTO `sys_menu` VALUES ('134', '64', '导出Excel', null, null, 'dict:export', null, '1', null, '2019-01-23 06:36:43', null);
INSERT INTO `sys_menu` VALUES ('135', '3', '密码重置', null, null, 'user:reset', null, '1', null, '2019-01-23 06:37:00', null);
INSERT INTO `sys_menu` VALUES ('136', '10', '导出Excel', null, null, 'log:export', null, '1', null, '2019-01-23 06:37:27', null);
INSERT INTO `sys_menu` VALUES ('137', '102', '导出Excel', null, null, 'job:export', null, '1', null, '2019-01-23 06:37:59', null);
INSERT INTO `sys_menu` VALUES ('138', '109', '导出Excel', null, null, 'jobLog:export', null, '1', null, '2019-01-23 06:38:32', null);
INSERT INTO `sys_menu` VALUES ('139', '0', '工作台', '/workplace', null, 'workplace', null, '0', null, '2019-03-23 10:23:20', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `REMARK` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name_index` (`name`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '管理员', '管理员', '2017-12-27 16:23:11', '2019-01-23 06:45:29');
INSERT INTO `sys_role` VALUES ('2', 'normal', '普通用户', '可查看，新增，导出', '2019-01-04 14:11:28', '2019-01-23 07:37:08');
INSERT INTO `sys_role` VALUES ('3', 'read', '只读用户1', '只可查看', '2019-01-23 07:33:20', '2019-05-01 21:47:16');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ROLE_ID` bigint(20) NOT NULL,
  `MENU_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1124293938660896770 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('3', '1', '11');
INSERT INTO `sys_role_menu` VALUES ('5', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('6', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('7', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('8', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('9', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('10', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('11', '1', '17');
INSERT INTO `sys_role_menu` VALUES ('12', '1', '18');
INSERT INTO `sys_role_menu` VALUES ('13', '1', '19');
INSERT INTO `sys_role_menu` VALUES ('14', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('15', '1', '20');
INSERT INTO `sys_role_menu` VALUES ('16', '1', '21');
INSERT INTO `sys_role_menu` VALUES ('17', '1', '22');
INSERT INTO `sys_role_menu` VALUES ('18', '1', '64');
INSERT INTO `sys_role_menu` VALUES ('19', '1', '65');
INSERT INTO `sys_role_menu` VALUES ('20', '1', '66');
INSERT INTO `sys_role_menu` VALUES ('21', '1', '67');
INSERT INTO `sys_role_menu` VALUES ('22', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('23', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('24', '1', '23');
INSERT INTO `sys_role_menu` VALUES ('25', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('26', '1', '24');
INSERT INTO `sys_role_menu` VALUES ('27', '1', '113');
INSERT INTO `sys_role_menu` VALUES ('28', '1', '121');
INSERT INTO `sys_role_menu` VALUES ('29', '1', '122');
INSERT INTO `sys_role_menu` VALUES ('30', '1', '124');
INSERT INTO `sys_role_menu` VALUES ('31', '1', '123');
INSERT INTO `sys_role_menu` VALUES ('32', '1', '125');
INSERT INTO `sys_role_menu` VALUES ('33', '1', '101');
INSERT INTO `sys_role_menu` VALUES ('34', '1', '102');
INSERT INTO `sys_role_menu` VALUES ('35', '1', '103');
INSERT INTO `sys_role_menu` VALUES ('36', '1', '104');
INSERT INTO `sys_role_menu` VALUES ('37', '1', '105');
INSERT INTO `sys_role_menu` VALUES ('38', '1', '106');
INSERT INTO `sys_role_menu` VALUES ('39', '1', '107');
INSERT INTO `sys_role_menu` VALUES ('40', '1', '108');
INSERT INTO `sys_role_menu` VALUES ('41', '1', '109');
INSERT INTO `sys_role_menu` VALUES ('42', '1', '110');
INSERT INTO `sys_role_menu` VALUES ('43', '1', '58');
INSERT INTO `sys_role_menu` VALUES ('44', '1', '59');
INSERT INTO `sys_role_menu` VALUES ('45', '1', '61');
INSERT INTO `sys_role_menu` VALUES ('46', '1', '81');
INSERT INTO `sys_role_menu` VALUES ('47', '1', '82');
INSERT INTO `sys_role_menu` VALUES ('48', '1', '83');
INSERT INTO `sys_role_menu` VALUES ('49', '1', '127');
INSERT INTO `sys_role_menu` VALUES ('50', '1', '128');
INSERT INTO `sys_role_menu` VALUES ('51', '1', '129');
INSERT INTO `sys_role_menu` VALUES ('52', '1', '130');
INSERT INTO `sys_role_menu` VALUES ('53', '1', '135');
INSERT INTO `sys_role_menu` VALUES ('54', '1', '131');
INSERT INTO `sys_role_menu` VALUES ('55', '1', '132');
INSERT INTO `sys_role_menu` VALUES ('56', '1', '133');
INSERT INTO `sys_role_menu` VALUES ('57', '1', '134');
INSERT INTO `sys_role_menu` VALUES ('58', '1', '136');
INSERT INTO `sys_role_menu` VALUES ('59', '1', '137');
INSERT INTO `sys_role_menu` VALUES ('60', '1', '138');
INSERT INTO `sys_role_menu` VALUES ('87', '2', '3');
INSERT INTO `sys_role_menu` VALUES ('88', '2', '1');
INSERT INTO `sys_role_menu` VALUES ('89', '2', '4');
INSERT INTO `sys_role_menu` VALUES ('90', '2', '5');
INSERT INTO `sys_role_menu` VALUES ('91', '2', '6');
INSERT INTO `sys_role_menu` VALUES ('92', '2', '64');
INSERT INTO `sys_role_menu` VALUES ('93', '2', '2');
INSERT INTO `sys_role_menu` VALUES ('94', '2', '8');
INSERT INTO `sys_role_menu` VALUES ('95', '2', '10');
INSERT INTO `sys_role_menu` VALUES ('96', '2', '113');
INSERT INTO `sys_role_menu` VALUES ('97', '2', '121');
INSERT INTO `sys_role_menu` VALUES ('98', '2', '122');
INSERT INTO `sys_role_menu` VALUES ('99', '2', '124');
INSERT INTO `sys_role_menu` VALUES ('100', '2', '123');
INSERT INTO `sys_role_menu` VALUES ('101', '2', '125');
INSERT INTO `sys_role_menu` VALUES ('102', '2', '101');
INSERT INTO `sys_role_menu` VALUES ('103', '2', '102');
INSERT INTO `sys_role_menu` VALUES ('104', '2', '109');
INSERT INTO `sys_role_menu` VALUES ('105', '2', '58');
INSERT INTO `sys_role_menu` VALUES ('106', '2', '59');
INSERT INTO `sys_role_menu` VALUES ('107', '2', '61');
INSERT INTO `sys_role_menu` VALUES ('108', '2', '81');
INSERT INTO `sys_role_menu` VALUES ('109', '2', '82');
INSERT INTO `sys_role_menu` VALUES ('110', '2', '83');
INSERT INTO `sys_role_menu` VALUES ('111', '2', '127');
INSERT INTO `sys_role_menu` VALUES ('112', '2', '128');
INSERT INTO `sys_role_menu` VALUES ('113', '2', '129');
INSERT INTO `sys_role_menu` VALUES ('114', '2', '130');
INSERT INTO `sys_role_menu` VALUES ('115', '2', '14');
INSERT INTO `sys_role_menu` VALUES ('116', '2', '17');
INSERT INTO `sys_role_menu` VALUES ('117', '2', '132');
INSERT INTO `sys_role_menu` VALUES ('118', '2', '20');
INSERT INTO `sys_role_menu` VALUES ('119', '2', '133');
INSERT INTO `sys_role_menu` VALUES ('120', '2', '65');
INSERT INTO `sys_role_menu` VALUES ('121', '2', '134');
INSERT INTO `sys_role_menu` VALUES ('122', '2', '136');
INSERT INTO `sys_role_menu` VALUES ('123', '2', '103');
INSERT INTO `sys_role_menu` VALUES ('124', '2', '137');
INSERT INTO `sys_role_menu` VALUES ('125', '2', '138');
INSERT INTO `sys_role_menu` VALUES ('126', '2', '131');
INSERT INTO `sys_role_menu` VALUES ('1123584475859640322', '3', '139');
INSERT INTO `sys_role_menu` VALUES ('1124293938660896769', '4', '139');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '系统前台用户表',
  `name` varchar(20) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '用户登陆账号',
  `pwd` varchar(125) CHARACTER SET utf8 DEFAULT '' COMMENT '登陆密码，32位加密串',
  `nickname` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '用户昵称',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `sex` char(2) CHARACTER SET utf8 DEFAULT '' COMMENT '性别',
  `avatar` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '头像',
  `id_card` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '身份证',
  `province` int(11) DEFAULT '0' COMMENT '省',
  `city` int(11) DEFAULT '0' COMMENT '市',
  `area` int(11) DEFAULT '0' COMMENT '区',
  `address` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '所在地址',
  `memo` text COMMENT '备注',
  `email` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱',
  `code` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '编号',
  `dept_id` bigint(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `username` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$UN.DLdd3bI98TfsVV4NHaOZeZ46jKm8mjAuHf3jIU6M48la02DSv.', 'admin', '13111111111', 'juli', null, '0', '2019-02-16 10:22:45', '1', 'https://static.vilson.xyz/cover.png', '', '0', '0', '0', null, null, '398820605@qq.com', '6v7be19pwman2fird04gqu53', '2', '2019-04-15 21:36:28');
INSERT INTO `sys_user` VALUES ('2', 'Alians', 'e10adc3949ba59abbe56e057f20f883e', 'Alians', '13111111111', 'vilson', null, '0', '2019-01-26 07:29:41', '0', 'https://gw.alipayobjects.com/zos/rmsportal/zOsKZmFRdUtvpqCImOVY.png', null, '0', '0', '0', null, null, '11@qq.com', 'kqdcn2w40p58r31zyo6efjib', '2', '2019-04-15 21:48:13');
INSERT INTO `sys_user` VALUES ('3', 'Chihiro', 'e10adc3949ba59abbe56e057f20f883e', 'Chihiro', '18278881051', 'Chihiro', null, '1', '2019-01-24 11:15:09', '0', 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', null, '0', '0', '0', null, null, '741648282@qq.com', 'y680trgedcavbhnz24u7i5m3', '2', '2019-04-15 21:50:07');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1117787185756516354 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('1117787185756516353', '3', '2');
