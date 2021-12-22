/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : cbs

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 08/11/2019 18:22:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cbs_contract
-- ----------------------------
DROP TABLE IF EXISTS `cbs_contract`;
CREATE TABLE `cbs_contract`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT 'id16进制码，存在子合同时此id相同',
  `fk_trade_object_id` bigint(20) NULL DEFAULT NULL COMMENT '交易对象公司ID，查交易方名录表（cbs_directory_trade_object）',
  `fk_currency_id` int(11) NULL DEFAULT NULL COMMENT '外键币种ID，（cbs_currency）',
  `type` int(8) NULL DEFAULT 1 COMMENT '合同类型 1、来料加工合同 2、国内采购合同',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父合同ID，关联本表查询，树形结构',
  `contract_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同单号，由创建时间、我方ID、对方ID等构成的固定位数16进制长编码（装逼用）',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '合同标题',
  `introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '简介',
  `money` bigint(20) NULL DEFAULT NULL COMMENT '合同额',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  `status` int(2) NULL DEFAULT 0 COMMENT '状态 0、默认 1、已签约 2、履行中 3、已结束 99、中止',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_contract
-- ----------------------------
INSERT INTO `cbs_contract` VALUES (1, '1', 1, 1, 1, NULL, NULL, 'aa', '', NULL, '2019-10-14 13:13:30', '2019-10-15 10:00:25', NULL, 0);
INSERT INTO `cbs_contract` VALUES (2, '0', NULL, NULL, 3, NULL, '2', 'aasdfasf', 'adfasf打发鳌山卫无发送到发送 阿斯顿发生', 12312, '2019-10-15 15:55:26', '2019-10-15 15:55:26', NULL, 0);
INSERT INTO `cbs_contract` VALUES (3, '0', NULL, NULL, 3, NULL, '3', 'asdfasf', 'asdfasdfasf', 1, '2019-10-15 15:57:52', '2019-10-15 15:57:52', NULL, 0);
INSERT INTO `cbs_contract` VALUES (4, '0', 8, 2, 3, NULL, '4', 'asdfasfd', '向第三发顺丰', 1, '2019-10-15 16:02:12', '2019-10-15 16:02:12', 1, 0);
INSERT INTO `cbs_contract` VALUES (5, '0', 8, 2, 3, NULL, '5', 'asdfasfd', '向第三发顺丰', 1, '2019-10-15 16:02:21', '2019-10-15 16:02:21', 1, 0);
INSERT INTO `cbs_contract` VALUES (6, '6', 6, 3, 3, NULL, '12312623423·', '打法是否放', '大丰收阿斯顿发AA', 1231231, '2019-10-15 16:09:10', '2019-10-15 16:09:10', 1, 0);
INSERT INTO `cbs_contract` VALUES (7, '7', 9, NULL, 3, NULL, '12314211dasf', '阿萨德法师', '打法是否', 123123, '2019-10-15 16:36:50', '2019-10-15 16:36:50', 1, 0);
INSERT INTO `cbs_contract` VALUES (8, '8', 6, 2, 3, NULL, '76867868', '金坷垃', '打发大师傅的', 123123, '2019-10-15 16:38:25', '2019-10-15 16:38:25', 1, 0);
INSERT INTO `cbs_contract` VALUES (9, '9', 6, NULL, 3, NULL, '123123是打发斯蒂芬', '阿斯顿发发', '阿达是否', 1231231, '2019-10-15 16:40:32', '2019-10-15 16:40:32', 1, 0);
INSERT INTO `cbs_contract` VALUES (11, 'B', 10, 2, 3, NULL, 'asdfasfds123', 'adfasfs', 'asdf的事发地时', 123123, '2019-10-15 16:54:45', '2019-10-15 16:54:45', 1, 0);
INSERT INTO `cbs_contract` VALUES (12, 'C', 6, NULL, 3, NULL, '123123dfsf', '士大夫阿达是否的', '暗示法大是大非', 122, '2019-10-15 17:29:53', '2019-10-15 17:29:53', 1, 0);
INSERT INTO `cbs_contract` VALUES (13, 'D', 6, 1, 3, NULL, '123123dfsf', '士大夫阿达是否的', '暗示法大是大非', 122, '2019-10-15 17:35:42', '2019-10-15 17:35:42', 1, 0);
INSERT INTO `cbs_contract` VALUES (14, 'E', 6, 1, 3, NULL, '123123dfsf', '士大夫阿达是否的', '暗示法大是大非', 122, '2019-10-15 17:36:54', '2019-10-15 17:36:54', 1, 0);
INSERT INTO `cbs_contract` VALUES (15, 'F', 9, 1, 1, NULL, NULL, 'saf', '123123', 123123, '2019-10-16 10:53:30', '2019-10-16 10:53:30', 1, 0);
INSERT INTO `cbs_contract` VALUES (16, 'F', NULL, 1, 11, NULL, 'asdasfa', 'saf', '', NULL, '2019-10-16 10:53:30', NULL, NULL, 0);
INSERT INTO `cbs_contract` VALUES (17, '11', 6, 2, 1, NULL, NULL, '进料加工合同1', '进料加工合同1进料加工合同1', 12000, '2019-10-16 16:55:48', '2019-10-16 16:55:48', 1, 0);
INSERT INTO `cbs_contract` VALUES (18, '11', NULL, 2, 11, 17, '213123', '进料加工合同1', '', NULL, '2019-10-16 16:55:48', '2019-10-16 17:44:36', 1, 0);
INSERT INTO `cbs_contract` VALUES (19, '11', NULL, 2, 12, 17, 'EXPORT00001', '进料加工合同1', '', NULL, '2019-10-16 16:55:48', '2019-10-16 17:44:36', 1, 0);
INSERT INTO `cbs_contract` VALUES (20, '11', 6, 2, 13, 17, 'CODE00001', '进料加工合同1', '', NULL, '2019-10-16 16:55:48', '2019-10-16 17:44:37', 1, 0);
INSERT INTO `cbs_contract` VALUES (21, '15', 11, 1, 1, NULL, NULL, '进口加工合同2', '进口加工合同2进口加工合同2', 10000, '2019-10-16 18:05:15', '2019-10-17 15:59:47', 1, 0);
INSERT INTO `cbs_contract` VALUES (46, '2E', 2, 2, 3, NULL, 'IP-20191018', '纯进口合同2019.10.18', '纯进口合同IP-20191018', 102300, '2019-10-18 16:16:41', '2019-10-18 16:16:41', 1, 0);
INSERT INTO `cbs_contract` VALUES (50, '0', 11, 1, 11, 21, 'IMPORT2', '进口加工合同2 进口子合同', '', 2000, '2019-10-19 15:50:06', NULL, NULL, 0);
INSERT INTO `cbs_contract` VALUES (51, '0', 11, 1, 12, 21, 'EXPORT0002', '进口加工合同2 出口子合同', '', NULL, '2019-10-19 15:50:06', NULL, NULL, 0);
INSERT INTO `cbs_contract` VALUES (52, '0', 11, 1, 13, 21, 'PURCHASE00002', '进口加工合同2 国内采购子合同', '', 2000, '2019-10-19 15:50:06', NULL, NULL, 0);
INSERT INTO `cbs_contract` VALUES (53, '0', 11, 1, 11, 21, 'IMPORT2', '进口加工合同2 进口子合同', '', 2000, '2019-10-19 16:47:47', NULL, NULL, 0);
INSERT INTO `cbs_contract` VALUES (54, '0', 11, 1, 12, 21, 'EXPORT0002', '进口加工合同2 出口子合同', '', NULL, '2019-10-19 16:47:47', NULL, NULL, 0);
INSERT INTO `cbs_contract` VALUES (55, '0', 11, 1, 13, 21, 'PURCHASE00002', '进口加工合同2 国内采购子合同', '', 2000, '2019-10-19 16:47:47', NULL, NULL, 0);
INSERT INTO `cbs_contract` VALUES (56, '38', 8, 3, 2, NULL, 'LAILIAO000001', '来料1', '来料加工合同20191030', 3000000, '2019-10-30 19:23:07', '2019-10-30 19:23:22', 1, 0);

-- ----------------------------
-- Table structure for cbs_contract_goods
-- ----------------------------
DROP TABLE IF EXISTS `cbs_contract_goods`;
CREATE TABLE `cbs_contract_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT NULL COMMENT '关联合同表',
  `fk_goods_id` bigint(20) NULL DEFAULT NULL COMMENT '关联商品名录表',
  `count` decimal(20, 2) NULL DEFAULT NULL COMMENT '商品数量，可能包含小数',
  `unit_price` decimal(20, 2) NULL DEFAULT NULL COMMENT '商品单价，可能包含小数',
  `type` tinyint(2) NULL DEFAULT NULL COMMENT '1、进 2、出',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '原材料名录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_contract_goods
-- ----------------------------
INSERT INTO `cbs_contract_goods` VALUES (7, 1, 1, 234123.00, 21.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (8, 1, 1, 123.00, 22.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (9, 12, NULL, 123.00, 123.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (10, 13, NULL, 2131.00, 1.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (12, 22, 10, 10000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (13, 23, 7, 100.00, NULL, 2);
INSERT INTO `cbs_contract_goods` VALUES (14, 25, 10, 10000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (15, 26, 7, 100.00, NULL, 2);
INSERT INTO `cbs_contract_goods` VALUES (16, 28, 10, 10000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (17, 29, 7, 100.00, NULL, 2);
INSERT INTO `cbs_contract_goods` VALUES (18, 31, 10, 10000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (19, 32, 7, 100.00, NULL, 2);
INSERT INTO `cbs_contract_goods` VALUES (20, 33, 7, 1000.00, 15.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (21, 34, 8, 100.00, 10.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (22, 35, 10, 10000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (23, 35, 9, 1000.00, 10.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (24, 36, 7, 100.00, NULL, 2);
INSERT INTO `cbs_contract_goods` VALUES (25, 37, 7, 1000.00, 15.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (26, 38, 8, 100.00, 10.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (27, 39, 10, 10000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (28, 40, 7, 100.00, NULL, 2);
INSERT INTO `cbs_contract_goods` VALUES (29, 41, 7, 1000.00, 15.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (30, 42, 8, 100.00, 10.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (31, 43, 10, 10000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (32, 44, 7, 100.00, NULL, 2);
INSERT INTO `cbs_contract_goods` VALUES (33, 45, 7, 1000.00, 15.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (34, 14, 9, 123.00, 213.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (35, 14, NULL, NULL, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (36, 46, 7, 1000.00, 10.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (37, 46, 9, 1023.00, 1.20, 1);
INSERT INTO `cbs_contract_goods` VALUES (38, 47, 10, 10000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (39, 48, 7, 100.00, NULL, 2);
INSERT INTO `cbs_contract_goods` VALUES (40, 49, 7, 1000.00, 15.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (41, 50, 10, 10000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (42, 51, 7, 100.00, NULL, 2);
INSERT INTO `cbs_contract_goods` VALUES (43, 52, 7, 1000.00, 15.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (44, 52, 8, 1000.00, 12.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (45, 53, 10, 10000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (46, 54, 7, 100.00, NULL, 2);
INSERT INTO `cbs_contract_goods` VALUES (47, 55, 7, 1000.00, 15.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (48, 55, 8, 1000.00, 12.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (49, 55, 9, 10000.00, 1.00, 1);
INSERT INTO `cbs_contract_goods` VALUES (53, 56, 10, 1000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (54, 56, 7, 2000.00, NULL, 1);
INSERT INTO `cbs_contract_goods` VALUES (55, 56, 9, 20000.00, NULL, 2);

-- ----------------------------
-- Table structure for cbs_contract_processing_record
-- ----------------------------
DROP TABLE IF EXISTS `cbs_contract_processing_record`;
CREATE TABLE `cbs_contract_processing_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT NULL COMMENT '关联加工贸易合同单表（cbs_contract_parent）ID',
  `customs_record_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '海关备案编号',
  `pass_time` datetime(0) NULL DEFAULT NULL COMMENT '备案通过时间',
  `filing_status` int(4) NULL DEFAULT 0 COMMENT '0：准备中（缺省） 1：审核中 2：已备案 99：备案失败',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '（合同）海关（手册）备案表 这个表只在对应来料加工企业 需要进行海关手册备案情况下使用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_contract_processing_record
-- ----------------------------
INSERT INTO `cbs_contract_processing_record` VALUES (1, 1, '海关备案编号', NULL, 0, '原因', '2019-08-22 17:15:38', NULL);

-- ----------------------------
-- Table structure for cbs_contract_status_stream
-- ----------------------------
DROP TABLE IF EXISTS `cbs_contract_status_stream`;
CREATE TABLE `cbs_contract_status_stream`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT NULL COMMENT '关联合同表',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  `status` int(2) NULL DEFAULT 0 COMMENT '状态 0、默认 1、已签约 2、履行中 3、已结束 99、中止',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_contract_status_stream
-- ----------------------------
INSERT INTO `cbs_contract_status_stream` VALUES (1, NULL, NULL, 0, '', '2019-10-14 13:13:30');
INSERT INTO `cbs_contract_status_stream` VALUES (2, NULL, NULL, 0, 'adfasf打发鳌山卫无发送到发送 阿斯顿发生', '2019-10-15 15:55:26');
INSERT INTO `cbs_contract_status_stream` VALUES (3, NULL, NULL, 0, 'asdfasdfasf', '2019-10-15 15:57:52');
INSERT INTO `cbs_contract_status_stream` VALUES (4, NULL, 1, 0, '向第三发顺丰', '2019-10-15 16:02:12');
INSERT INTO `cbs_contract_status_stream` VALUES (5, NULL, 1, 0, '向第三发顺丰', '2019-10-15 16:02:21');
INSERT INTO `cbs_contract_status_stream` VALUES (6, NULL, 1, 0, '大丰收阿斯顿发AA', '2019-10-15 16:09:10');
INSERT INTO `cbs_contract_status_stream` VALUES (7, NULL, 1, 0, '打法是否', '2019-10-15 16:36:50');
INSERT INTO `cbs_contract_status_stream` VALUES (8, NULL, 1, 0, '打发大师傅的', '2019-10-15 16:38:25');
INSERT INTO `cbs_contract_status_stream` VALUES (9, NULL, 1, 0, '阿达是否', '2019-10-15 16:40:32');
INSERT INTO `cbs_contract_status_stream` VALUES (11, NULL, 1, 0, 'asdf的事发地时', '2019-10-15 16:54:45');
INSERT INTO `cbs_contract_status_stream` VALUES (12, NULL, 1, 0, '暗示法大是大非', '2019-10-15 17:29:53');
INSERT INTO `cbs_contract_status_stream` VALUES (13, NULL, 1, 0, '暗示法大是大非', '2019-10-15 17:35:42');
INSERT INTO `cbs_contract_status_stream` VALUES (14, NULL, 1, 0, '暗示法大是大非', '2019-10-15 17:36:54');
INSERT INTO `cbs_contract_status_stream` VALUES (15, NULL, 1, 0, '123123', '2019-10-16 10:53:30');
INSERT INTO `cbs_contract_status_stream` VALUES (16, NULL, NULL, 0, '', '2019-10-16 10:53:30');

-- ----------------------------
-- Table structure for cbs_currency
-- ----------------------------
DROP TABLE IF EXISTS `cbs_currency`;
CREATE TABLE `cbs_currency`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abbreviation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name_en` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name_cn` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_currency
-- ----------------------------
INSERT INTO `cbs_currency` VALUES (1, 'CNY', 'Chinese Yuan', '人民币');
INSERT INTO `cbs_currency` VALUES (2, 'USD', 'United States Dollar', '美元');
INSERT INTO `cbs_currency` VALUES (3, 'JPY', 'Japanese Yen', '日元');
INSERT INTO `cbs_currency` VALUES (4, 'EUR', 'Euro', '欧元');

-- ----------------------------
-- Table structure for cbs_directory_customs_broker
-- ----------------------------
DROP TABLE IF EXISTS `cbs_directory_customs_broker`;
CREATE TABLE `cbs_directory_customs_broker`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报关行名称',
  `contactor` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报关行地址',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1 COMMENT '0为不可用1为可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报关行名录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_directory_customs_broker
-- ----------------------------
INSERT INTO `cbs_directory_customs_broker` VALUES (1, 'adfasfa', '12314', '123123', 'asfddasfd', '2019-08-26 19:12:49', NULL, NULL);
INSERT INTO `cbs_directory_customs_broker` VALUES (2, '报关行222', '李总', '15984565258', '马栏子2321号2单元1209', '2019-09-20 19:35:44', NULL, 1);

-- ----------------------------
-- Table structure for cbs_directory_goods
-- ----------------------------
DROP TABLE IF EXISTS `cbs_directory_goods`;
CREATE TABLE `cbs_directory_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hscode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '海关商品手册HS编码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品实际名称，包含大类、简写、型号等',
  `unit` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品单位',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1 COMMENT '0为不可用1为可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '原材料名录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_directory_goods
-- ----------------------------
INSERT INTO `cbs_directory_goods` VALUES (1, NULL, '原材料名称', NULL, '原材料单位', '2019-08-14 14:52:23', '2019-08-14 14:51:29', 1);
INSERT INTO `cbs_directory_goods` VALUES (2, NULL, '大米2', NULL, '吨', '2019-08-22 16:21:56', '2019-10-26 14:18:04', 1);
INSERT INTO `cbs_directory_goods` VALUES (5, NULL, '333', NULL, '33', '2019-08-24 09:43:32', NULL, 1);
INSERT INTO `cbs_directory_goods` VALUES (6, NULL, '大米', NULL, '吨', '2019-09-04 18:13:22', NULL, 1);
INSERT INTO `cbs_directory_goods` VALUES (7, '0106202099', '其他食用爬行动物', '蜗牛 110-P', '千克', '2019-10-14 15:15:46', NULL, 1);
INSERT INTO `cbs_directory_goods` VALUES (8, '0101210010', '改良种用濒危野马', '野马 21号', '千克', '2019-10-14 16:34:56', NULL, 1);
INSERT INTO `cbs_directory_goods` VALUES (9, '123141232112', '大米2', '大米 P0001', '吨', '2019-08-22 16:21:56', NULL, 1);
INSERT INTO `cbs_directory_goods` VALUES (10, '6406909900', '其他材料制其他鞋靴零件', '阿玛尼鞋跟 P132', '双', '2019-10-15 17:43:21', NULL, 1);

-- ----------------------------
-- Table structure for cbs_directory_produce_company
-- ----------------------------
DROP TABLE IF EXISTS `cbs_directory_produce_company`;
CREATE TABLE `cbs_directory_produce_company`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产公司名称',
  `contactor` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产公司地址',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1 COMMENT '0为不可用1为可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生产公司名录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_directory_produce_company
-- ----------------------------
INSERT INTO `cbs_directory_produce_company` VALUES (1, '生产公司1', '黄先生', '1234122356', '生产公司地址1', '2019-08-29 18:26:56', NULL, 1);

-- ----------------------------
-- Table structure for cbs_directory_ship_company
-- ----------------------------
DROP TABLE IF EXISTS `cbs_directory_ship_company`;
CREATE TABLE `cbs_directory_ship_company`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '船务公司名称',
  `contactor` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '船务公司地址',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1 COMMENT '0为不可用1为可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '船务公司名录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_directory_ship_company
-- ----------------------------
INSERT INTO `cbs_directory_ship_company` VALUES (1, '船务公司1', '黄先生', '15998565425', '北京街234号', '2019-08-26 16:56:19', NULL, 1);

-- ----------------------------
-- Table structure for cbs_directory_store
-- ----------------------------
DROP TABLE IF EXISTS `cbs_directory_store`;
CREATE TABLE `cbs_directory_store`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `contactor` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库地址',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1 COMMENT '0为不可用1为可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仓库名录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_directory_store
-- ----------------------------
INSERT INTO `cbs_directory_store` VALUES (1, '仓库1', '黄先生', '15998426716', '仓库街1号', '2019-08-28 17:41:22', NULL, 1);
INSERT INTO `cbs_directory_store` VALUES (2, '仓库2', '李先生', '13566552525', '仓库街2号', '2019-11-02 18:55:43', NULL, 1);

-- ----------------------------
-- Table structure for cbs_directory_trade_object
-- ----------------------------
DROP TABLE IF EXISTS `cbs_directory_trade_object`;
CREATE TABLE `cbs_directory_trade_object`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易对象（公司）名称',
  `country` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属国家',
  `contactor` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易对象（公司）地址',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1 COMMENT '0为不可用1为可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '交易对象（公司）名录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_directory_trade_object
-- ----------------------------
INSERT INTO `cbs_directory_trade_object` VALUES (1, '第一个公司', '所属国家', '联系人', '13019436575', '地址', '2019-08-14 14:50:03', '2019-08-14 14:50:04', 1);
INSERT INTO `cbs_directory_trade_object` VALUES (2, '交易对象（公司）名称1', '所属国家1', '联系人1', '联系电话1', '交易对象（公司）地址1', '2019-08-14 14:50:03', '2019-08-16 16:33:24', 1);
INSERT INTO `cbs_directory_trade_object` VALUES (4, 'asdfa', 'adasdf', 'adfasfd', 'afdasdf', 'asf', '2019-08-16 20:45:46', NULL, 1);
INSERT INTO `cbs_directory_trade_object` VALUES (5, '说的', '山东省地方', '撒大声地', '123451231', '撒旦法', '2019-08-16 21:03:58', NULL, 1);
INSERT INTO `cbs_directory_trade_object` VALUES (6, '胜多负少', '所得税', '上升到', '爽肤水', '撒旦法', '2019-08-16 21:05:28', NULL, 1);
INSERT INTO `cbs_directory_trade_object` VALUES (7, '大师傅', '大师傅', '阿斯顿发生', '12314', '撒旦法', '2019-08-16 21:06:03', NULL, 1);
INSERT INTO `cbs_directory_trade_object` VALUES (8, '电饭锅', '阿达', '阿斯顿发', '123123123', '对方三房', '2019-08-17 11:43:21', NULL, 1);
INSERT INTO `cbs_directory_trade_object` VALUES (9, '阿发', '阿斯顿发', '阿斯顿发', '12314', '阿达', '2019-08-17 11:44:17', NULL, 1);
INSERT INTO `cbs_directory_trade_object` VALUES (10, 'asdfa', 'asda', 'sas', 'as', 'as', '2019-08-17 11:49:16', NULL, 1);
INSERT INTO `cbs_directory_trade_object` VALUES (11, '打撒所多', '按时', '阿达', '大师傅', '阿斯顿发', '2019-08-17 11:49:33', NULL, 1);

-- ----------------------------
-- Table structure for cbs_directory_transit_company
-- ----------------------------
DROP TABLE IF EXISTS `cbs_directory_transit_company`;
CREATE TABLE `cbs_directory_transit_company`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国内运输公司名称',
  `contactor` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国内运输公司地址',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1 COMMENT '0为不可用1为可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '国内运输公司名录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_directory_transit_company
-- ----------------------------
INSERT INTO `cbs_directory_transit_company` VALUES (1, '运输公司1', '黄先生', '15998426716', '运输街11号', '2019-08-28 17:36:09', NULL, 1);

-- ----------------------------
-- Table structure for cbs_export
-- ----------------------------
DROP TABLE IF EXISTS `cbs_export`;
CREATE TABLE `cbs_export`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT NULL COMMENT '关联合同单表（cbs_contract）ID',
  `fk_ship_company_id` bigint(20) NULL DEFAULT NULL COMMENT '关联船务公司名录表（cbs_directory_ship_company）ID',
  `fk_directory_customs_broker_id` bigint(20) NULL DEFAULT NULL COMMENT '关联报关行名录表（cbs_directory_customs_broker）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `depart_time` datetime(0) NULL DEFAULT NULL COMMENT '出发时间',
  `expected_arrival_time` datetime(0) NULL DEFAULT NULL COMMENT '预计抵达时间',
  `arrival_time` datetime(0) NULL DEFAULT NULL COMMENT '抵达时间',
  `product_count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '进料数量',
  `product` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '进料金额',
  `invoice_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票编号',
  `invoice_money` decimal(20, 0) NULL DEFAULT 0 COMMENT '发票金额',
  `entry_bill_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报关单号',
  `entry_bill_create_time` datetime(0) NULL DEFAULT NULL COMMENT '报关时间',
  `entry_bill_pass_time` datetime(0) NULL DEFAULT NULL COMMENT '报关通过时间',
  `status` int(4) NULL DEFAULT 0 COMMENT '0：准备中（缺省） 1：已报关 2：已发货 3：（对方）已收货 99：中止',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口单（主）表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_export
-- ----------------------------
INSERT INTO `cbs_export` VALUES (1, 43, 1, 2, '2019-09-20 19:32:41', '2019-09-02 00:00:00', '2019-09-20 19:33:56', '2019-09-21 00:00:00', 10000.00, 0.00, '259895621315', 10000, '564562255', '2019-09-20 19:35:50', '2019-09-28 00:00:00', 4, 1);

-- ----------------------------
-- Table structure for cbs_img_contract
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_contract`;
CREATE TABLE `cbs_img_contract`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT 0 COMMENT '关联合同单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_contract
-- ----------------------------
INSERT INTO `cbs_img_contract` VALUES (2, 2, 'url2', 0, 0, '2019-08-22 17:57:04', NULL);
INSERT INTO `cbs_img_contract` VALUES (3, 2, 'url3', 0, 1, '2019-08-22 17:59:37', NULL);
INSERT INTO `cbs_img_contract` VALUES (4, 2, 'url3', 0, 1, '2019-08-22 18:03:40', NULL);
INSERT INTO `cbs_img_contract` VALUES (49, 0, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/6239936_092702973000_2.jpg', 0, 1, '2019-10-15 16:36:50', NULL);
INSERT INTO `cbs_img_contract` VALUES (50, 0, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/8164280_104301508000_2.jpg', 0, 1, '2019-10-15 16:38:25', NULL);
INSERT INTO `cbs_img_contract` VALUES (51, 0, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/6dc09e0a75ed9763b1351db7.jpg', 0, 1, '2019-10-15 16:38:25', NULL);
INSERT INTO `cbs_img_contract` VALUES (52, 9, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/8164280_104301508000_2.jpg', 0, 1, '2019-10-15 16:40:32', NULL);
INSERT INTO `cbs_img_contract` VALUES (53, 11, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/6dc09e0a75ed9763b1351db7.jpg', 0, 1, '2019-10-15 16:54:45', NULL);
INSERT INTO `cbs_img_contract` VALUES (54, 12, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/6dc09e0a75ed9763b1351db7.jpg', 0, 1, '2019-10-15 17:29:53', NULL);
INSERT INTO `cbs_img_contract` VALUES (55, 22, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/12795880_110914420143_2.jpg', 0, 1, '2019-10-16 18:05:15', NULL);
INSERT INTO `cbs_img_contract` VALUES (56, 23, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-16 18:05:15', NULL);
INSERT INTO `cbs_img_contract` VALUES (57, 24, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-16 18:05:15', NULL);
INSERT INTO `cbs_img_contract` VALUES (58, 25, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/12795880_110914420143_2.jpg', 0, 1, '2019-10-17 15:59:28', NULL);
INSERT INTO `cbs_img_contract` VALUES (59, 26, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 15:59:28', NULL);
INSERT INTO `cbs_img_contract` VALUES (60, 27, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 15:59:28', NULL);
INSERT INTO `cbs_img_contract` VALUES (61, 28, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/12795880_110914420143_2.jpg', 0, 1, '2019-10-17 15:59:47', NULL);
INSERT INTO `cbs_img_contract` VALUES (62, 29, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 15:59:47', NULL);
INSERT INTO `cbs_img_contract` VALUES (63, 30, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 15:59:47', NULL);
INSERT INTO `cbs_img_contract` VALUES (64, 31, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/12795880_110914420143_2.jpg', 0, 1, '2019-10-17 16:00:41', NULL);
INSERT INTO `cbs_img_contract` VALUES (65, 32, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 16:00:41', NULL);
INSERT INTO `cbs_img_contract` VALUES (66, 33, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 16:00:41', NULL);
INSERT INTO `cbs_img_contract` VALUES (67, 34, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/8164280_104301508000_2.jpg', 0, 1, '2019-10-17 16:00:41', NULL);
INSERT INTO `cbs_img_contract` VALUES (68, 35, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/12795880_110914420143_2.jpg', 0, 1, '2019-10-17 16:18:02', NULL);
INSERT INTO `cbs_img_contract` VALUES (69, 36, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 16:18:02', NULL);
INSERT INTO `cbs_img_contract` VALUES (70, 37, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 16:18:02', NULL);
INSERT INTO `cbs_img_contract` VALUES (71, 38, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/8164280_104301508000_2.jpg', 0, 1, '2019-10-17 16:18:02', NULL);
INSERT INTO `cbs_img_contract` VALUES (72, 39, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/12795880_110914420143_2.jpg', 0, 1, '2019-10-17 16:18:25', NULL);
INSERT INTO `cbs_img_contract` VALUES (73, 40, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 16:18:25', NULL);
INSERT INTO `cbs_img_contract` VALUES (74, 41, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 16:18:25', NULL);
INSERT INTO `cbs_img_contract` VALUES (75, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/8164280_104301508000_2.jpg', 0, 1, '2019-10-17 16:18:26', NULL);
INSERT INTO `cbs_img_contract` VALUES (76, 43, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/12795880_110914420143_2.jpg', 0, 1, '2019-10-17 16:23:49', NULL);
INSERT INTO `cbs_img_contract` VALUES (77, 44, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 16:23:49', NULL);
INSERT INTO `cbs_img_contract` VALUES (78, 45, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 16:23:49', NULL);
INSERT INTO `cbs_img_contract` VALUES (79, 14, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-17 17:05:46', NULL);
INSERT INTO `cbs_img_contract` VALUES (80, 46, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-18 16:16:41', NULL);
INSERT INTO `cbs_img_contract` VALUES (81, 47, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/12795880_110914420143_2.jpg', 0, 1, '2019-10-18 17:28:30', NULL);
INSERT INTO `cbs_img_contract` VALUES (82, 48, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-18 17:28:30', NULL);
INSERT INTO `cbs_img_contract` VALUES (83, 49, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-18 17:28:30', NULL);
INSERT INTO `cbs_img_contract` VALUES (84, 50, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/12795880_110914420143_2.jpg', 0, 1, '2019-10-19 15:50:06', NULL);
INSERT INTO `cbs_img_contract` VALUES (85, 51, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-19 15:50:06', NULL);
INSERT INTO `cbs_img_contract` VALUES (86, 52, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-19 15:50:06', NULL);
INSERT INTO `cbs_img_contract` VALUES (87, 53, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/12795880_110914420143_2.jpg', 0, 1, '2019-10-19 16:47:47', NULL);
INSERT INTO `cbs_img_contract` VALUES (88, 54, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-19 16:47:47', NULL);
INSERT INTO `cbs_img_contract` VALUES (89, 55, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/9252150_142515375000_2.jpg', 0, 1, '2019-10-19 16:47:47', NULL);
INSERT INTO `cbs_img_contract` VALUES (91, 56, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/contract/微信图片_20190902163847.jpg', 0, 1, '2019-10-30 19:23:22', NULL);

-- ----------------------------
-- Table structure for cbs_img_contract_export
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_contract_export`;
CREATE TABLE `cbs_img_contract_export`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT 0 COMMENT '关联合同单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口销售合同图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_contract_export
-- ----------------------------
INSERT INTO `cbs_img_contract_export` VALUES (2, 2, 'url2', 0, 0, '2019-08-22 17:57:04', NULL);
INSERT INTO `cbs_img_contract_export` VALUES (3, 2, 'url3', 0, 1, '2019-08-22 17:59:37', NULL);
INSERT INTO `cbs_img_contract_export` VALUES (4, 2, 'url3', 0, 1, '2019-08-22 18:03:40', NULL);
INSERT INTO `cbs_img_contract_export` VALUES (32, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_sell/20190823_jvjz.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_export` VALUES (33, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_sell/20190823_rh8u.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_export` VALUES (48, 43, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/sell_contract/zhutuijilu.png', 0, 1, '2019-09-04 17:42:04', NULL);

-- ----------------------------
-- Table structure for cbs_img_contract_import
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_contract_import`;
CREATE TABLE `cbs_img_contract_import`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT 0 COMMENT '关联合同单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 128 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口采购合同图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_contract_import
-- ----------------------------
INSERT INTO `cbs_img_contract_import` VALUES (18, 41, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/41/contract_purchase/20190823_ybsd.jpg', 0, 1, '2019-08-23 09:40:23', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (19, 41, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/41/contract_purchase/20190823_7frs.jpg', 0, 1, '2019-08-23 09:40:23', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (82, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_purchase/20190822_obj9.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (83, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_purchase/20190822_ujqb.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (84, 42, '', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (85, 42, '', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (86, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_purchase/20190823_bzqg.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (87, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_purchase/20190824_7aak.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (88, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_6ko8.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (89, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_voq2.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (90, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_7b1z.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (91, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_yqmg.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (92, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_fps1.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (93, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_4i23.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (94, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_f2jn.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (95, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/tonghuajilu.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (96, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/zhutuijilu.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (97, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/升级弹窗.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (98, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/zhutuijilu.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (99, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/tonghuajilu.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (100, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/升级弹窗.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (126, 43, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/purchase_contract/升级弹窗.png', 0, 1, '2019-09-04 17:42:04', NULL);
INSERT INTO `cbs_img_contract_import` VALUES (127, 43, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/purchase_contract/9252150_142515375000_2.jpg', 0, 1, '2019-09-04 17:42:04', NULL);

-- ----------------------------
-- Table structure for cbs_img_contract_processing
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_contract_processing`;
CREATE TABLE `cbs_img_contract_processing`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT 0 COMMENT '关联合同单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '来料加工合同图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_contract_processing
-- ----------------------------
INSERT INTO `cbs_img_contract_processing` VALUES (2, 2, 'url2', 0, 0, '2019-08-22 17:57:04', NULL);
INSERT INTO `cbs_img_contract_processing` VALUES (3, 2, 'url3', 0, 1, '2019-08-22 17:59:37', NULL);
INSERT INTO `cbs_img_contract_processing` VALUES (4, 2, 'url3', 0, 1, '2019-08-22 18:03:40', NULL);
INSERT INTO `cbs_img_contract_processing` VALUES (32, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_sell/20190823_jvjz.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_processing` VALUES (33, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_sell/20190823_rh8u.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_processing` VALUES (48, 43, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/sell_contract/zhutuijilu.png', 0, 1, '2019-09-04 17:42:04', NULL);

-- ----------------------------
-- Table structure for cbs_img_contract_processing_record
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_contract_processing_record`;
CREATE TABLE `cbs_img_contract_processing_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT 0 COMMENT '关联合同单主表（cbs_contract_processing）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '来料加工合同海关备案文件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_contract_processing_record
-- ----------------------------
INSERT INTO `cbs_img_contract_processing_record` VALUES (4, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/1/contract_record/9252150_101440518391_2.jpg', 0, 1, '2019-09-04 16:13:53', NULL);
INSERT INTO `cbs_img_contract_processing_record` VALUES (11, 43, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/contract_record/9252150_142515375000_2.jpg', 0, 1, '2019-09-04 17:42:04', NULL);

-- ----------------------------
-- Table structure for cbs_img_contract_purchase
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_contract_purchase`;
CREATE TABLE `cbs_img_contract_purchase`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT 0 COMMENT '关联合同单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 128 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购合同图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_contract_purchase
-- ----------------------------
INSERT INTO `cbs_img_contract_purchase` VALUES (18, 41, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/41/contract_purchase/20190823_ybsd.jpg', 0, 1, '2019-08-23 09:40:23', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (19, 41, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/41/contract_purchase/20190823_7frs.jpg', 0, 1, '2019-08-23 09:40:23', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (82, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_purchase/20190822_obj9.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (83, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_purchase/20190822_ujqb.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (84, 42, '', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (85, 42, '', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (86, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_purchase/20190823_bzqg.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (87, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/contract_purchase/20190824_7aak.jpg', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (88, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_6ko8.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (89, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_voq2.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (90, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_7b1z.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (91, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_yqmg.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (92, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_fps1.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (93, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_4i23.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (94, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/20190826_f2jn.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (95, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/tonghuajilu.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (96, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/zhutuijilu.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (97, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/升级弹窗.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (98, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/zhutuijilu.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (99, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/tonghuajilu.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (100, 42, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/purchase_contract/升级弹窗.png', 0, 1, '2019-08-26 20:24:36', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (126, 43, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/purchase_contract/升级弹窗.png', 0, 1, '2019-09-04 17:42:04', NULL);
INSERT INTO `cbs_img_contract_purchase` VALUES (127, 43, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/purchase_contract/9252150_142515375000_2.jpg', 0, 1, '2019-09-04 17:42:04', NULL);

-- ----------------------------
-- Table structure for cbs_img_contract_record
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_contract_record`;
CREATE TABLE `cbs_img_contract_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT 0 COMMENT '关联合同单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '海关备案文件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_contract_record
-- ----------------------------
INSERT INTO `cbs_img_contract_record` VALUES (4, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/1/contract_record/9252150_101440518391_2.jpg', 0, 1, '2019-09-04 16:13:53', NULL);
INSERT INTO `cbs_img_contract_record` VALUES (11, 43, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/contract_record/9252150_142515375000_2.jpg', 0, 1, '2019-09-04 17:42:04', NULL);

-- ----------------------------
-- Table structure for cbs_img_contract_ship
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_contract_ship`;
CREATE TABLE `cbs_img_contract_ship`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_import）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（船务）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '船务合同图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_contract_ship
-- ----------------------------
INSERT INTO `cbs_img_contract_ship` VALUES (1, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/ship_contract/icon_sort_normal.png', 0, 1, '2019-08-27 10:25:34', NULL);
INSERT INTO `cbs_img_contract_ship` VALUES (7, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/ship_contract/9252150_142515375000_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_contract_ship` VALUES (8, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/ship_contract/6dc09e0a75ed9763b1351db7.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_contract_ship` VALUES (9, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/ship_contract/12795880_110914420143_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_contract_ship` VALUES (10, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/ship_contract/6239936_092702973000_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_contract_ship` VALUES (11, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/ship_contract/8164280_104301508000_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_contract_ship` VALUES (12, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/ship_contract/9252150_101440518391_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_contract_ship` VALUES (13, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/ship_contract/13258436_081747381002_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);

-- ----------------------------
-- Table structure for cbs_img_export_contract_ship
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_export_contract_ship`;
CREATE TABLE `cbs_img_export_contract_ship`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_export_id` bigint(20) NULL DEFAULT 0 COMMENT '关联出口单主表（cbs_export）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（船务）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口船务合同图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_export_contract_ship
-- ----------------------------
INSERT INTO `cbs_img_export_contract_ship` VALUES (6, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/1/ship_contract/zhulinbiandeheliu_2868363.jpg', 0, 1, '2019-09-20 19:36:10', NULL);

-- ----------------------------
-- Table structure for cbs_img_export_delivery_order
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_export_delivery_order`;
CREATE TABLE `cbs_img_export_delivery_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_export_id` bigint(20) NULL DEFAULT 0 COMMENT '关联出口单主表（cbs_export）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口提货单图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_export_delivery_order
-- ----------------------------
INSERT INTO `cbs_img_export_delivery_order` VALUES (3, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/1/delivery_order/9252150_101440518391_2.jpg', 0, 1, '2019-09-20 19:36:10', NULL);

-- ----------------------------
-- Table structure for cbs_img_export_entry_bill
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_export_entry_bill`;
CREATE TABLE `cbs_img_export_entry_bill`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_export_id` bigint(20) NULL DEFAULT 0 COMMENT '关联出口单主表（cbs_export）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口报关单图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_export_entry_bill
-- ----------------------------
INSERT INTO `cbs_img_export_entry_bill` VALUES (2, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/1/entry_bill/9252150_142515375000_2.jpg', 0, 1, '2019-09-20 19:36:10', NULL);

-- ----------------------------
-- Table structure for cbs_img_export_invoice
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_export_invoice`;
CREATE TABLE `cbs_img_export_invoice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_export_id` bigint(20) NULL DEFAULT 0 COMMENT '关联出口单主表（cbs_export）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一发票附件中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口发票附件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_export_invoice
-- ----------------------------
INSERT INTO `cbs_img_export_invoice` VALUES (1, 2, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/2/invoice/bg_pushadd.png', 0, 1, '2019-08-31 10:22:59', NULL);
INSERT INTO `cbs_img_export_invoice` VALUES (12, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/1/invoice/9252150_142515375000_2.jpg', 0, 1, '2019-09-20 19:36:10', NULL);

-- ----------------------------
-- Table structure for cbs_img_export_lading_bill
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_export_lading_bill`;
CREATE TABLE `cbs_img_export_lading_bill`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_export_id` bigint(20) NULL DEFAULT 0 COMMENT '关联出口单主表（cbs_export）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口提单图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_export_lading_bill
-- ----------------------------
INSERT INTO `cbs_img_export_lading_bill` VALUES (4, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/1/lading_bill/9252150_101440518391_2.jpg', 0, 1, '2019-09-20 19:36:10', NULL);

-- ----------------------------
-- Table structure for cbs_img_export_others
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_export_others`;
CREATE TABLE `cbs_img_export_others`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_export_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_export）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口其他附件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_export_others
-- ----------------------------
INSERT INTO `cbs_img_export_others` VALUES (1, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/1/others/zhulinbiandeheliu_2868363.jpg', 0, 1, '2019-09-20 19:36:10', NULL);

-- ----------------------------
-- Table structure for cbs_img_export_packing_list
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_export_packing_list`;
CREATE TABLE `cbs_img_export_packing_list`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_export_id` bigint(20) NULL DEFAULT 0 COMMENT '关联出口单主表（cbs_export）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一发票附件中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口箱单附件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_export_packing_list
-- ----------------------------
INSERT INTO `cbs_img_export_packing_list` VALUES (5, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/1/packing_list/13258436_081747381002_2.jpg', 0, 1, '2019-09-20 19:36:10', NULL);

-- ----------------------------
-- Table structure for cbs_img_import_contract_ship
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_contract_ship`;
CREATE TABLE `cbs_img_import_contract_ship`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_import）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（船务）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '船务合同图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cbs_img_import_delivery_order
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_delivery_order`;
CREATE TABLE `cbs_img_import_delivery_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口提货单图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_import_delivery_order
-- ----------------------------
INSERT INTO `cbs_img_import_delivery_order` VALUES (1, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/delivery_order/paixulan.png', 0, 1, '2019-08-27 10:25:34', NULL);
INSERT INTO `cbs_img_import_delivery_order` VALUES (27, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/delivery_order/9252150_142515375000_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);

-- ----------------------------
-- Table structure for cbs_img_import_entry_bill
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_entry_bill`;
CREATE TABLE `cbs_img_import_entry_bill`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口报关单图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_import_entry_bill
-- ----------------------------
INSERT INTO `cbs_img_import_entry_bill` VALUES (1, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/entry_bill/paixuhui.png', 0, 1, '2019-08-27 10:25:34', NULL);

-- ----------------------------
-- Table structure for cbs_img_import_invoice
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_invoice`;
CREATE TABLE `cbs_img_import_invoice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_import）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一发票附件中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口发票附件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_import_invoice
-- ----------------------------
INSERT INTO `cbs_img_import_invoice` VALUES (16, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/invoice/zhulinbiandeheliu_2868363.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_import_invoice` VALUES (19, 10, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/52/10/invoice/12795880_110914420143_2.jpg', 0, 1, '2019-10-22 11:07:33', NULL);

-- ----------------------------
-- Table structure for cbs_img_import_lading_bill
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_lading_bill`;
CREATE TABLE `cbs_img_import_lading_bill`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口提单图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_import_lading_bill
-- ----------------------------
INSERT INTO `cbs_img_import_lading_bill` VALUES (1, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/lading_bill/bg_call.png', 0, 1, '2019-08-27 10:25:34', NULL);
INSERT INTO `cbs_img_import_lading_bill` VALUES (2, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/lading_bill/icon_sort.png', 0, 1, '2019-08-27 10:25:34', NULL);
INSERT INTO `cbs_img_import_lading_bill` VALUES (3, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/lading_bill/zhutuijilu.png', 0, 1, '2019-08-27 10:25:34', NULL);
INSERT INTO `cbs_img_import_lading_bill` VALUES (4, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/lading_bill/bg_pushadd.png', 0, 1, '2019-08-27 10:25:34', NULL);
INSERT INTO `cbs_img_import_lading_bill` VALUES (5, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/lading_bill/bg_update.png', 0, 1, '2019-08-27 10:25:34', NULL);
INSERT INTO `cbs_img_import_lading_bill` VALUES (6, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/lading_bill/tonghuajilu.png', 0, 1, '2019-08-27 10:25:34', NULL);
INSERT INTO `cbs_img_import_lading_bill` VALUES (7, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/lading_bill/升级弹窗.png', 0, 1, '2019-08-27 10:25:34', NULL);
INSERT INTO `cbs_img_import_lading_bill` VALUES (59, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/lading_bill/13639685_123501617185_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_import_lading_bill` VALUES (60, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/lading_bill/18061085_132409045379_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_import_lading_bill` VALUES (61, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/lading_bill/zhulinbiandeheliu_2868363.jpg', 0, 1, '2019-09-04 19:59:23', NULL);

-- ----------------------------
-- Table structure for cbs_img_import_license_agreement
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_license_agreement`;
CREATE TABLE `cbs_img_import_license_agreement`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_import）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一发票附件中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '授权协议附件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cbs_img_import_others
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_others`;
CREATE TABLE `cbs_img_import_others`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口其他附件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_import_others
-- ----------------------------
INSERT INTO `cbs_img_import_others` VALUES (1, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/others/icon_sort.png', 0, 1, '2019-08-27 10:25:34', NULL);

-- ----------------------------
-- Table structure for cbs_img_import_packing_list
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_packing_list`;
CREATE TABLE `cbs_img_import_packing_list`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_import）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一发票附件中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口箱单附件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cbs_img_import_pay_in_advance
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_pay_in_advance`;
CREATE TABLE `cbs_img_import_pay_in_advance`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_import）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一发票附件中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '首付款附件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cbs_img_import_pay_tail
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_pay_tail`;
CREATE TABLE `cbs_img_import_pay_tail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_import）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一发票附件中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '尾款附件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cbs_img_import_power_of_attorney
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_import_power_of_attorney`;
CREATE TABLE `cbs_img_import_power_of_attorney`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_import）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一发票附件中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '授权书附件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cbs_img_packing_list
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_packing_list`;
CREATE TABLE `cbs_img_packing_list`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_import）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一发票附件中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '箱单附件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_packing_list
-- ----------------------------
INSERT INTO `cbs_img_packing_list` VALUES (1, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/packing_list/paixuhui.png', 0, 1, '2019-08-27 10:25:34', NULL);
INSERT INTO `cbs_img_packing_list` VALUES (9, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/packing_list/zhulinbiandeheliu_2868363.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_packing_list` VALUES (10, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/packing_list/13395269_103525238102_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_packing_list` VALUES (11, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/packing_list/18061085_132409045379_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);
INSERT INTO `cbs_img_packing_list` VALUES (12, 4, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/4/packing_list/13639685_123501617185_2.jpg', 0, 1, '2019-09-04 19:59:23', NULL);

-- ----------------------------
-- Table structure for cbs_img_produce_goods_stream
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_produce_goods_stream`;
CREATE TABLE `cbs_img_produce_goods_stream`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_produce_goods_stream_id` bigint(20) NULL DEFAULT 0 COMMENT 'cbs_store_goods_in  ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生产验收报告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_produce_goods_stream
-- ----------------------------
INSERT INTO `cbs_img_produce_goods_stream` VALUES (1, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/others/icon_sort.png', 0, 1, '2019-08-27 10:25:34', NULL);

-- ----------------------------
-- Table structure for cbs_img_purchase_dispatch_voucher
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_purchase_dispatch_voucher`;
CREATE TABLE `cbs_img_purchase_dispatch_voucher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '国内进货发货凭证附件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_purchase_dispatch_voucher
-- ----------------------------
INSERT INTO `cbs_img_purchase_dispatch_voucher` VALUES (1, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/others/icon_sort.png', 0, 1, '2019-08-27 10:25:34', NULL);

-- ----------------------------
-- Table structure for cbs_img_purchase_invoice
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_purchase_invoice`;
CREATE TABLE `cbs_img_purchase_invoice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_purchase_id` bigint(20) NULL DEFAULT 0 COMMENT '关联国内进货单表（cbs_purchase）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一发票附件中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效 0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口发票附件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_purchase_invoice
-- ----------------------------
INSERT INTO `cbs_img_purchase_invoice` VALUES (1, 2, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/2/invoice/bg_pushadd.png', 0, 1, '2019-08-31 10:22:59', NULL);
INSERT INTO `cbs_img_purchase_invoice` VALUES (12, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/43/1/invoice/9252150_142515375000_2.jpg', 0, 1, '2019-09-20 19:36:10', NULL);

-- ----------------------------
-- Table structure for cbs_img_purchase_receive_voucher
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_purchase_receive_voucher`;
CREATE TABLE `cbs_img_purchase_receive_voucher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT 0 COMMENT '关联进口单主表（cbs_contract）ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口其他附件图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_purchase_receive_voucher
-- ----------------------------
INSERT INTO `cbs_img_purchase_receive_voucher` VALUES (1, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/others/icon_sort.png', 0, 1, '2019-08-27 10:25:34', NULL);

-- ----------------------------
-- Table structure for cbs_img_store_goods_in_delivery_order
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_store_goods_in_delivery_order`;
CREATE TABLE `cbs_img_store_goods_in_delivery_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_in_id` bigint(20) NULL DEFAULT 0 COMMENT 'cbs_store_goods_in  ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '入库提货单图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_store_goods_in_delivery_order
-- ----------------------------
INSERT INTO `cbs_img_store_goods_in_delivery_order` VALUES (2, 0, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/56/2/delivery_order/安卓经纪人30天统计.png', 0, 1, '2019-10-31 16:48:13', NULL);
INSERT INTO `cbs_img_store_goods_in_delivery_order` VALUES (6, 2, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/56/2/delivery_order/1571810984.png', 0, 1, '2019-11-02 17:53:17', NULL);
INSERT INTO `cbs_img_store_goods_in_delivery_order` VALUES (7, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/others/icon_sort.png', 0, 1, '2019-11-02 17:53:23', NULL);

-- ----------------------------
-- Table structure for cbs_img_store_goods_in_receipt
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_store_goods_in_receipt`;
CREATE TABLE `cbs_img_store_goods_in_receipt`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_in_id` bigint(20) NULL DEFAULT 0 COMMENT 'cbs_store_goods_in  ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '入库签收证明图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_store_goods_in_receipt
-- ----------------------------
INSERT INTO `cbs_img_store_goods_in_receipt` VALUES (2, 0, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/56/2/receipt/微信图片_20190902163847.jpg', 0, 1, '2019-10-31 16:48:13', NULL);
INSERT INTO `cbs_img_store_goods_in_receipt` VALUES (6, 2, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/56/2/receipt/1571810984(1).png', 0, 1, '2019-11-02 17:53:17', NULL);
INSERT INTO `cbs_img_store_goods_in_receipt` VALUES (7, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/others/icon_sort.png', 0, 1, '2019-11-02 17:53:23', NULL);

-- ----------------------------
-- Table structure for cbs_img_store_goods_out_delivery_order
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_store_goods_out_delivery_order`;
CREATE TABLE `cbs_img_store_goods_out_delivery_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_out_id` bigint(20) NULL DEFAULT 0 COMMENT 'cbs_store_goods_out  ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '入库提货单图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_store_goods_out_delivery_order
-- ----------------------------
INSERT INTO `cbs_img_store_goods_out_delivery_order` VALUES (2, 0, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/56/2/delivery_order/安卓经纪人30天统计.png', 0, 1, '2019-10-31 16:48:13', NULL);
INSERT INTO `cbs_img_store_goods_out_delivery_order` VALUES (6, 2, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/56/2/delivery_order/1571810984.png', 0, 1, '2019-11-02 17:53:17', NULL);
INSERT INTO `cbs_img_store_goods_out_delivery_order` VALUES (7, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/others/icon_sort.png', 0, 1, '2019-11-02 17:53:23', NULL);

-- ----------------------------
-- Table structure for cbs_img_store_goods_out_receipt
-- ----------------------------
DROP TABLE IF EXISTS `cbs_img_store_goods_out_receipt`;
CREATE TABLE `cbs_img_store_goods_out_receipt`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_out_id` bigint(20) NULL DEFAULT 0 COMMENT 'cbs_store_goods_out  ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图片地址（后缀）',
  `sort` int(8) NULL DEFAULT 0 COMMENT '在同一（采购）合同中的排序顺序',
  `is_valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效:0无效，1有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '入库签收证明图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_img_store_goods_out_receipt
-- ----------------------------
INSERT INTO `cbs_img_store_goods_out_receipt` VALUES (2, 0, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/56/2/receipt/微信图片_20190902163847.jpg', 0, 1, '2019-10-31 16:48:13', NULL);
INSERT INTO `cbs_img_store_goods_out_receipt` VALUES (6, 2, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/56/2/receipt/1571810984(1).png', 0, 1, '2019-11-02 17:53:17', NULL);
INSERT INTO `cbs_img_store_goods_out_receipt` VALUES (7, 1, 'http://huanghe1984.oss-cn-beijing.aliyuncs.com/cbs/42/1/others/icon_sort.png', 0, 1, '2019-11-02 17:53:23', NULL);

-- ----------------------------
-- Table structure for cbs_import
-- ----------------------------
DROP TABLE IF EXISTS `cbs_import`;
CREATE TABLE `cbs_import`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT NULL COMMENT '关联合同单表（cbs_contract）ID',
  `fk_ship_company_id` bigint(20) NULL DEFAULT NULL COMMENT '关联船务公司名录表（cbs_directory_ship_company）ID',
  `fk_directory_customs_broker_id` bigint(20) NULL DEFAULT NULL COMMENT '报关行名录表cbs_directory_customs_broker',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `depart_time` datetime(0) NULL DEFAULT NULL COMMENT '出发时间',
  `expected_arrival_time` datetime(0) NULL DEFAULT NULL COMMENT '预计抵达时间',
  `arrival_time` datetime(0) NULL DEFAULT NULL COMMENT '抵达时间',
  `raw_material_count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '进料数量',
  `raw_material` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '进料金额',
  `status` int(4) NULL DEFAULT 0 COMMENT '0：准备中（缺省） 1：（对方）已发货 2：已抵达 3：已报关 4：待提货 5：已提货 99：中止',
  `invoice_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票编号',
  `entry_bill_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报关单号',
  `invoice_money` decimal(20, 0) NULL DEFAULT NULL COMMENT '进口发票金额',
  `entry_bill_create_time` timestamp(0) NULL DEFAULT NULL COMMENT '报关时间',
  `entry_bill_pass_time` timestamp(0) NULL DEFAULT NULL COMMENT '报关时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口单（主）表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_import
-- ----------------------------
INSERT INTO `cbs_import` VALUES (10, 52, NULL, NULL, '2019-10-22 09:16:03', NULL, NULL, NULL, 0.00, 0.00, 0, 'ABC12000', NULL, 12300, NULL, NULL, NULL);
INSERT INTO `cbs_import` VALUES (11, 52, NULL, NULL, '2019-10-22 09:16:05', NULL, NULL, NULL, 0.00, 0.00, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cbs_import` VALUES (12, 56, NULL, NULL, '2019-10-30 19:23:34', NULL, NULL, NULL, 0.00, 0.00, 0, 'JG0002933', NULL, 100000, NULL, NULL, NULL);
INSERT INTO `cbs_import` VALUES (13, 56, NULL, NULL, '2019-11-02 18:49:24', NULL, NULL, NULL, 0.00, 0.00, 0, 'asdfas1123123', NULL, 1123123, NULL, NULL, NULL);
INSERT INTO `cbs_import` VALUES (14, 56, NULL, NULL, '2019-11-04 16:01:58', NULL, NULL, NULL, 0.00, 0.00, 0, 'A123232', NULL, 2000, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for cbs_import_备份
-- ----------------------------
DROP TABLE IF EXISTS `cbs_import_备份`;
CREATE TABLE `cbs_import_备份`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT NULL COMMENT '关联合同单表（cbs_contract）ID',
  `fk_ship_company_id` bigint(20) NULL DEFAULT NULL COMMENT '关联船务公司名录表（cbs_directory_ship_company）ID',
  `fk_directory_customs_broker_id` bigint(20) NULL DEFAULT NULL COMMENT '报关行名录表cbs_directory_customs_broker',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `depart_time` datetime(0) NULL DEFAULT NULL COMMENT '出发时间',
  `expected_arrival_time` datetime(0) NULL DEFAULT NULL COMMENT '预计抵达时间',
  `arrival_time` datetime(0) NULL DEFAULT NULL COMMENT '抵达时间',
  `raw_material_count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '进料数量',
  `raw_material` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '进料金额',
  `status` int(4) NULL DEFAULT 0 COMMENT '0：准备中（缺省） 1：（对方）已发货 2：已抵达 3：已报关 4：待提货 5：已提货 99：中止',
  `invoice_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票编号',
  `entry_bill_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报关单号',
  `invoice_money` decimal(20, 0) NULL DEFAULT NULL COMMENT '进口发票金额',
  `entry_bill_create_time` timestamp(0) NULL DEFAULT NULL COMMENT '报关时间',
  `entry_bill_pass_time` timestamp(0) NULL DEFAULT NULL COMMENT '报关时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口单（主）表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_import_备份
-- ----------------------------
INSERT INTO `cbs_import_备份` VALUES (4, 43, 1, NULL, '2019-09-04 18:39:39', '2019-09-04 00:00:00', '2019-09-13 00:00:00', '2019-09-28 00:00:00', 10000.00, 0.00, 0, '22222', NULL, 33333, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for cbs_import_goods
-- ----------------------------
DROP TABLE IF EXISTS `cbs_import_goods`;
CREATE TABLE `cbs_import_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_import_id` bigint(20) NULL DEFAULT NULL COMMENT '关联cbs_import',
  `fk_goods_id` bigint(20) NULL DEFAULT NULL COMMENT '关联商品名录表',
  `count` decimal(20, 2) NULL DEFAULT NULL COMMENT '商品数量，可能包含小数',
  `unit_price` decimal(20, 2) NULL DEFAULT NULL COMMENT '商品单价，可能包含小数',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '原材进货记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_import_goods
-- ----------------------------
INSERT INTO `cbs_import_goods` VALUES (7, NULL, 1, 234123.00, 21.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (8, NULL, 1, 123.00, 22.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (9, NULL, NULL, 123.00, 123.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (10, NULL, NULL, 2131.00, 1.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (12, NULL, 10, 10000.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (13, NULL, 7, 100.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (14, NULL, 10, 10000.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (15, NULL, 7, 100.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (16, NULL, 10, 10000.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (17, NULL, 7, 100.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (18, NULL, 10, 10000.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (19, NULL, 7, 100.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (20, NULL, 7, 1000.00, 15.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (21, NULL, 8, 100.00, 10.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (22, NULL, 10, 10000.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (23, NULL, 9, 1000.00, 10.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (24, NULL, 7, 100.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (25, NULL, 7, 1000.00, 15.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (26, NULL, 8, 100.00, 10.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (27, NULL, 10, 10000.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (28, NULL, 7, 100.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (29, NULL, 7, 1000.00, 15.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (30, NULL, 8, 100.00, 10.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (31, NULL, 10, 10000.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (32, NULL, 7, 100.00, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (33, NULL, 7, 1000.00, 15.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (34, NULL, 9, 123.00, 213.00, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (35, NULL, NULL, NULL, NULL, '2019-10-18 15:04:50', NULL);
INSERT INTO `cbs_import_goods` VALUES (36, 10, 7, 100.00, NULL, '2019-10-22 11:07:33', NULL);
INSERT INTO `cbs_import_goods` VALUES (37, 10, 8, 10.00, NULL, '2019-10-22 11:07:33', NULL);
INSERT INTO `cbs_import_goods` VALUES (40, 12, 10, 2000.00, NULL, '2019-11-01 15:59:13', NULL);
INSERT INTO `cbs_import_goods` VALUES (41, 12, 7, 1000.00, NULL, '2019-11-01 15:59:13', NULL);
INSERT INTO `cbs_import_goods` VALUES (42, 13, 10, 20000.00, NULL, '2019-11-02 18:54:31', NULL);
INSERT INTO `cbs_import_goods` VALUES (43, 13, 7, 12200.00, NULL, '2019-11-02 18:54:31', NULL);
INSERT INTO `cbs_import_goods` VALUES (44, 14, 10, 5000.00, NULL, '2019-11-04 16:02:20', NULL);
INSERT INTO `cbs_import_goods` VALUES (45, 14, 7, 20.00, NULL, '2019-11-04 16:02:20', NULL);

-- ----------------------------
-- Table structure for cbs_money_in
-- ----------------------------
DROP TABLE IF EXISTS `cbs_money_in`;
CREATE TABLE `cbs_money_in`  (
  `id` bigint(20) NOT NULL,
  `fk_export_id` bigint(20) NULL DEFAULT NULL COMMENT '关联出口单（cbs_export）',
  `money` decimal(20, 0) NULL DEFAULT NULL COMMENT '发生金额',
  `status` int(8) NULL DEFAULT 1 COMMENT '1、有效 99、作废',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间，即作废时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口单产生的收入流水' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cbs_money_out
-- ----------------------------
DROP TABLE IF EXISTS `cbs_money_out`;
CREATE TABLE `cbs_money_out`  (
  `id` bigint(20) NOT NULL,
  `fk_import_id` bigint(20) NULL DEFAULT NULL COMMENT '关联进口单（cbs_import）',
  `money` decimal(20, 0) NULL DEFAULT NULL COMMENT '发生金额',
  `status` int(8) NULL DEFAULT 1 COMMENT '1、有效 99、作废',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间，即作废时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口单产生的支出流水' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cbs_produce
-- ----------------------------
DROP TABLE IF EXISTS `cbs_produce`;
CREATE TABLE `cbs_produce`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT NULL COMMENT '关联合同单表（cbs_contract）ID',
  `fk_produce_company_id` bigint(20) NULL DEFAULT NULL COMMENT '关联生产公司名录表（cbs_directory_produce_company）ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `status` int(4) NULL DEFAULT 0 COMMENT '0：准备中（缺省） 1：生产中 2：生产结束 99：生产中止',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生产单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_produce
-- ----------------------------
INSERT INTO `cbs_produce` VALUES (0, 54, 1, '2019-08-29 18:16:03', '2019-08-29 18:30:42', '2019-08-30 18:30:43', 0, NULL);
INSERT INTO `cbs_produce` VALUES (3, 55, 1, '2019-09-20 17:42:20', NULL, NULL, 0, NULL);
INSERT INTO `cbs_produce` VALUES (4, 21, NULL, '2019-10-25 17:25:53', NULL, NULL, 0, NULL);
INSERT INTO `cbs_produce` VALUES (5, 56, 1, '2019-10-30 20:06:30', NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for cbs_produce_备份
-- ----------------------------
DROP TABLE IF EXISTS `cbs_produce_备份`;
CREATE TABLE `cbs_produce_备份`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT NULL COMMENT '关联合同单表（cbs_contract）ID',
  `fk_produce_company_id` bigint(20) NULL DEFAULT NULL COMMENT '关联生产公司名录表（cbs_directory_produce_company）ID',
  `fk_raw_material_id` bigint(20) NULL DEFAULT NULL COMMENT '关联商品（原料）名录表（cbs_directory_goods）ID',
  `fk_product_id` bigint(20) NULL DEFAULT NULL COMMENT '关联商品（产品）名录表（cbs_directory_goods）ID',
  `raw_material_count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '原料数量',
  `product_count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '产品数量',
  `wastage_rate` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '原料损耗率，百分比，12.56%记12.56',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `status` int(4) NULL DEFAULT 0 COMMENT '0：准备中（缺省） 1：生产中 2：生产结束 99：生产中止',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生产单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_produce_备份
-- ----------------------------
INSERT INTO `cbs_produce_备份` VALUES (2, 1, 1, 1, 1, 1000.00, 1000.00, 0.50, '2019-08-29 18:16:03', '2019-08-29 18:30:42', '2019-08-30 18:30:43', 0, NULL);
INSERT INTO `cbs_produce_备份` VALUES (3, 43, 1, 4, 6, 10000.00, 2000.00, 0.00, '2019-09-20 17:42:20', NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for cbs_produce_goods
-- ----------------------------
DROP TABLE IF EXISTS `cbs_produce_goods`;
CREATE TABLE `cbs_produce_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_produce_id` bigint(20) NULL DEFAULT NULL COMMENT '关联生产单表（cbs_produce）ID',
  `fk_goods_id` bigint(20) NULL DEFAULT NULL COMMENT '关联商品名录表',
  `goods_type` int(4) NULL DEFAULT 0 COMMENT '商品类型：1、原料 2、产品',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  `total_count` decimal(20, 2) NULL DEFAULT NULL COMMENT '已经调拨的原料或已经生产的产品的总数量',
  `store_count` decimal(20, 2) NULL DEFAULT NULL COMMENT '原料或产品在库（已调拨未使用或已生产未入库）总数量',
  `plan_count` decimal(20, 2) NULL DEFAULT NULL COMMENT '计划数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生产的商品缓存' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_produce_goods
-- ----------------------------
INSERT INTO `cbs_produce_goods` VALUES (4, 5, 10, 1, '2019-10-30 20:06:30', NULL, NULL, NULL, 10000.00);
INSERT INTO `cbs_produce_goods` VALUES (5, 5, 7, 1, '2019-10-30 20:06:30', NULL, NULL, NULL, 2000.00);
INSERT INTO `cbs_produce_goods` VALUES (6, 5, 9, 2, '2019-10-30 20:06:30', NULL, NULL, NULL, 100000.00);

-- ----------------------------
-- Table structure for cbs_produce_goods_stream
-- ----------------------------
DROP TABLE IF EXISTS `cbs_produce_goods_stream`;
CREATE TABLE `cbs_produce_goods_stream`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_produce_id` bigint(20) NULL DEFAULT NULL COMMENT '关联生产表（cbs_produce）ID',
  `produce_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '产出结果时间',
  `produce_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  `status` int(4) NULL DEFAULT 0 COMMENT '0：未审核 1：已审核',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生产流水' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_produce_goods_stream
-- ----------------------------
INSERT INTO `cbs_produce_goods_stream` VALUES (2, 1, '2019-08-29 18:16:03', NULL, NULL, 0);
INSERT INTO `cbs_produce_goods_stream` VALUES (3, 43, '2019-09-20 17:42:20', NULL, NULL, 0);
INSERT INTO `cbs_produce_goods_stream` VALUES (5, NULL, '2019-11-26 00:00:00', 'ABC111', NULL, 0);
INSERT INTO `cbs_produce_goods_stream` VALUES (6, 5, '2019-11-29 00:00:00', '生产111', NULL, 0);

-- ----------------------------
-- Table structure for cbs_produce_goods_stream_item
-- ----------------------------
DROP TABLE IF EXISTS `cbs_produce_goods_stream_item`;
CREATE TABLE `cbs_produce_goods_stream_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_produce_goods_stream_id` bigint(20) NULL DEFAULT NULL COMMENT 'cbs_produce_goods_stream ID',
  `type` int(2) NULL DEFAULT 0 COMMENT '1原料，2产品',
  `fk_goods_id` bigint(20) NULL DEFAULT NULL COMMENT '关联商品名录表（cbs_directory_goods）ID',
  `count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '被使用的原料或被生产的产品数量',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'cbs_produce_goods_stream 的子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_produce_goods_stream_item
-- ----------------------------
INSERT INTO `cbs_produce_goods_stream_item` VALUES (1, NULL, NULL, NULL, 0.00, '2019-09-25 19:51:32');
INSERT INTO `cbs_produce_goods_stream_item` VALUES (2, 5, 1, 10, 102.00, '2019-11-06 21:14:40');
INSERT INTO `cbs_produce_goods_stream_item` VALUES (3, 5, 1, 7, 2323.00, '2019-11-06 21:14:40');
INSERT INTO `cbs_produce_goods_stream_item` VALUES (4, 5, 2, 9, 222.00, '2019-11-06 21:14:40');
INSERT INTO `cbs_produce_goods_stream_item` VALUES (5, 6, 1, 10, 120.00, '2019-11-06 21:16:24');
INSERT INTO `cbs_produce_goods_stream_item` VALUES (6, 6, 1, 7, 320.00, '2019-11-06 21:16:24');
INSERT INTO `cbs_produce_goods_stream_item` VALUES (7, 6, 2, 9, 120.00, '2019-11-06 21:16:24');

-- ----------------------------
-- Table structure for cbs_purchase
-- ----------------------------
DROP TABLE IF EXISTS `cbs_purchase`;
CREATE TABLE `cbs_purchase`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_contract_id` bigint(20) NULL DEFAULT NULL COMMENT '关联合同单表（cbs_contract）ID',
  `fk_ship_company_id` bigint(20) NULL DEFAULT NULL COMMENT '关联国内运输公司名录表（cbs_directory_transit_company）ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `raw_material_count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '进料数量',
  `raw_material` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '进料金额',
  `status` int(4) NULL DEFAULT 0 COMMENT '0：准备中（缺省） 1：（对方）已发货  99：中止',
  `invoice_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票编号',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口单（主）表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_purchase
-- ----------------------------
INSERT INTO `cbs_purchase` VALUES (4, 43, 1, '2019-09-04 18:39:39', 10000.00, 0.00, 0, '22222', NULL);

-- ----------------------------
-- Table structure for cbs_store_goods
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods`;
CREATE TABLE `cbs_store_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_id` bigint(20) NULL DEFAULT NULL COMMENT '关联仓库名录表（cbs_directory_store）ID',
  `fk_goods_id` bigint(20) NULL DEFAULT NULL COMMENT '关联商品名录表（cbs_directory_goods）ID',
  `goods_store_count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '库存商品数量',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品库存表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cbs_store_goods_in
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods_in`;
CREATE TABLE `cbs_store_goods_in`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(8) NULL DEFAULT NULL COMMENT '入库类型 1、进货入库 2、生产入库',
  `fk_store_id` bigint(20) NULL DEFAULT NULL COMMENT '关联仓库名录表（cbs_directory_store）ID',
  `fk_transit_company_id` bigint(20) NULL DEFAULT NULL COMMENT '关联运输公司名录表（cbs_directory_transit_company）ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `depart_time` datetime(0) NULL DEFAULT NULL COMMENT '出发时间',
  `arrival_time` datetime(0) NULL DEFAULT NULL COMMENT '抵达时间',
  `status` int(4) NULL DEFAULT 0 COMMENT '0、默认 1、已审核 98、已锁定 99、已删除',
  `delete_note` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除说明',
  `examine_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '审核时间',
  `delete_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '删除时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  `start_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提货地址',
  `start_contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提货地址-联系人',
  `start_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提货地址-联系人-电话',
  `end_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `end_contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址-联系人',
  `end_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址-联系人-电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品入库表，\r\n继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，\r\n原则上本表中一条数据必在其子表中存在一条数据，反之亦然' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_store_goods_in
-- ----------------------------
INSERT INTO `cbs_store_goods_in` VALUES (1, 1, 1, 1, '2019-09-25 19:51:32', NULL, NULL, 0, NULL, '2019-10-23 17:30:09', '2019-10-23 17:30:09', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cbs_store_goods_in` VALUES (2, 1, 1, 1, '2019-10-30 19:40:00', '2019-10-25 00:00:00', '2019-10-31 00:00:00', 0, NULL, '2019-10-31 16:50:55', '2019-10-31 16:50:55', 1, '大连港1号码头仓储区', '黄先生', '15998426716', '1号仓库', '李先生', '15998425666');
INSERT INTO `cbs_store_goods_in` VALUES (11, 1, 2, NULL, '2019-11-02 18:55:14', NULL, NULL, 0, NULL, '2019-11-04 10:35:06', '2019-11-04 10:35:06', 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cbs_store_goods_in` VALUES (12, 1, 2, NULL, '2019-11-04 16:02:43', NULL, NULL, 0, NULL, '2019-11-04 16:02:59', '2019-11-04 16:02:59', 1, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for cbs_store_goods_in_import
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods_in_import`;
CREATE TABLE `cbs_store_goods_in_import`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_in_id` bigint(20) NULL DEFAULT NULL COMMENT '关联cbs_store_goods_out表，构成继承关系',
  `fk_import_id` bigint(20) NULL DEFAULT NULL COMMENT '关联进口表（cbs_import）ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '进口入库表，与cbs_store_goods_in表为1对1继承关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_store_goods_in_import
-- ----------------------------
INSERT INTO `cbs_store_goods_in_import` VALUES (1, 1, 10);
INSERT INTO `cbs_store_goods_in_import` VALUES (2, 2, 12);
INSERT INTO `cbs_store_goods_in_import` VALUES (4, 11, 13);
INSERT INTO `cbs_store_goods_in_import` VALUES (5, 12, 14);

-- ----------------------------
-- Table structure for cbs_store_goods_in_item
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods_in_item`;
CREATE TABLE `cbs_store_goods_in_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_in_id` bigint(20) NULL DEFAULT NULL COMMENT '关联仓库名录表（cbs_directory_store）ID',
  `fk_goods_id` bigint(20) NULL DEFAULT NULL COMMENT '关联商品名录表（cbs_directory_goods）ID',
  `goods_in_count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '入库产品数量',
  `goods_store_count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '库存产品数量',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品入库表，\r\n继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，\r\n原则上本表中一条数据必在其子表中存在一条数据，反之亦然' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_store_goods_in_item
-- ----------------------------
INSERT INTO `cbs_store_goods_in_item` VALUES (26, 2, 10, 2000.00, 2000.00, '2019-11-02 17:53:17');
INSERT INTO `cbs_store_goods_in_item` VALUES (27, 2, 7, 1000.00, 1000.00, '2019-11-02 17:53:17');
INSERT INTO `cbs_store_goods_in_item` VALUES (28, 1, 7, 100.00, 100.00, '2019-11-02 17:53:23');
INSERT INTO `cbs_store_goods_in_item` VALUES (29, 1, 8, 10.00, 10.00, '2019-11-02 17:53:23');
INSERT INTO `cbs_store_goods_in_item` VALUES (30, 11, 10, 20000.00, 20000.00, '2019-11-04 10:35:06');
INSERT INTO `cbs_store_goods_in_item` VALUES (31, 11, 7, 12200.00, 12200.00, '2019-11-04 10:35:06');
INSERT INTO `cbs_store_goods_in_item` VALUES (32, 12, 10, 5000.00, 5000.00, '2019-11-04 16:02:59');
INSERT INTO `cbs_store_goods_in_item` VALUES (33, 12, 7, 20.00, 20.00, '2019-11-04 16:02:59');

-- ----------------------------
-- Table structure for cbs_store_goods_in_produce
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods_in_produce`;
CREATE TABLE `cbs_store_goods_in_produce`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_in_id` bigint(20) NULL DEFAULT NULL COMMENT '关联cbs_store_goods_out表，构成继承关系',
  `fk_produce_id` bigint(20) NULL DEFAULT NULL COMMENT '关联生产表（cbs_produce）ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生产入库表，与cbs_store_goods_in表为1对1继承关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_store_goods_in_produce
-- ----------------------------
INSERT INTO `cbs_store_goods_in_produce` VALUES (1, NULL, NULL);

-- ----------------------------
-- Table structure for cbs_store_goods_in_purchase
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods_in_purchase`;
CREATE TABLE `cbs_store_goods_in_purchase`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_in_id` bigint(20) NULL DEFAULT NULL COMMENT '关联cbs_store_goods_out表，构成继承关系',
  `fk_purchase_id` bigint(20) NULL DEFAULT NULL COMMENT '关联国内采购表（cbs_purchase）ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '国内采购入库表，与cbs_store_goods_in表为1对1继承关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_store_goods_in_purchase
-- ----------------------------
INSERT INTO `cbs_store_goods_in_purchase` VALUES (1, NULL, NULL);

-- ----------------------------
-- Table structure for cbs_store_goods_in_purchase_废弃
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods_in_purchase_废弃`;
CREATE TABLE `cbs_store_goods_in_purchase_废弃`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_in_id` bigint(20) NULL DEFAULT NULL COMMENT '关联cbs_store_goods_out表，构成继承关系',
  `fk_purchase_id` bigint(20) NULL DEFAULT NULL COMMENT '关联国内采购表（cbs_purchase）ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '国内采购入库表，与cbs_store_goods_in表为1对1继承关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_store_goods_in_purchase_废弃
-- ----------------------------
INSERT INTO `cbs_store_goods_in_purchase_废弃` VALUES (1, NULL, NULL);

-- ----------------------------
-- Table structure for cbs_store_goods_out
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods_out`;
CREATE TABLE `cbs_store_goods_out`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(8) NULL DEFAULT NULL COMMENT '出库类型 1、生产出库 2、出货出库',
  `fk_store_id` bigint(20) NULL DEFAULT NULL COMMENT '关联仓库名录表（cbs_directory_store）ID',
  `fk_transit_company_id` bigint(20) NULL DEFAULT NULL COMMENT '关联运输公司名录表（cbs_directory_transit_company）ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `depart_time` datetime(0) NULL DEFAULT NULL COMMENT '出发时间',
  `arrival_time` datetime(0) NULL DEFAULT NULL COMMENT '抵达时间',
  `status` int(4) NULL DEFAULT 0 COMMENT '0、默认 1、已审核 99、已删除',
  `delete_note` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `examine_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '审核时间',
  `delete_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '删除时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  `start_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提货地址',
  `start_contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提货地址-联系人',
  `start_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提货地址-联系人-电话',
  `end_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `end_contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址-联系人',
  `end_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址-联系人-电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品出库表，\r\n继承表包含cbs_store_goods_out_produce、cbs_store_goods_out_export，\r\n原则上本表中一条数据必在其子表中存在一条数据，反之亦然' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_store_goods_out
-- ----------------------------
INSERT INTO `cbs_store_goods_out` VALUES (1, 1, 1, 1, '2019-09-20 20:50:32', NULL, NULL, 0, NULL, '2019-10-26 11:57:45', '2019-10-26 11:57:45', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cbs_store_goods_out` VALUES (2, 1, 1, 1, '2019-10-26 15:26:31', NULL, NULL, 0, NULL, '2019-10-26 15:26:31', '2019-10-26 15:26:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cbs_store_goods_out` VALUES (3, 1, NULL, NULL, '2019-11-04 17:59:25', NULL, NULL, 0, NULL, '2019-11-06 17:36:57', '2019-11-06 17:36:57', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cbs_store_goods_out` VALUES (4, 1, NULL, NULL, '2019-11-04 18:00:36', NULL, NULL, 0, NULL, '2019-11-06 17:36:57', '2019-11-06 17:36:57', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cbs_store_goods_out` VALUES (5, 1, 2, NULL, '2019-11-06 16:02:44', NULL, NULL, 0, NULL, '2019-11-08 17:27:21', '2019-11-08 17:27:21', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cbs_store_goods_out` VALUES (6, 1, 1, NULL, '2019-11-06 16:03:16', NULL, NULL, 0, NULL, '2019-11-08 17:20:12', '2019-11-08 17:20:12', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cbs_store_goods_out` VALUES (22, 1, 1, NULL, '2019-11-08 18:21:07', NULL, NULL, 0, NULL, '2019-11-08 18:21:27', '2019-11-08 18:21:27', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for cbs_store_goods_out_export
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods_out_export`;
CREATE TABLE `cbs_store_goods_out_export`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_out_id` bigint(20) NULL DEFAULT NULL COMMENT '关联cbs_store_goods_out表，构成继承关系',
  `fk_export_id` bigint(20) NULL DEFAULT NULL COMMENT '关联出口表（cbs_export）ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出口出库表，与cbs_store_goods_out表为1对1继承关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_store_goods_out_export
-- ----------------------------
INSERT INTO `cbs_store_goods_out_export` VALUES (1, NULL, NULL);

-- ----------------------------
-- Table structure for cbs_store_goods_out_item
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods_out_item`;
CREATE TABLE `cbs_store_goods_out_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_out_id` bigint(20) NULL DEFAULT NULL COMMENT '关联产品出库单表（cbs_store_product_out）ID',
  `fk_store_goods_in_id` bigint(20) NULL DEFAULT NULL COMMENT '关联产品入库单表（cbs_store_product_in）ID',
  `goods_out_count` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '出库产品数量',
  `fk_goods_id` bigint(20) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '原料出库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_store_goods_out_item
-- ----------------------------
INSERT INTO `cbs_store_goods_out_item` VALUES (1, 1, 1, 1.00, 1, '2019-10-26 11:52:16');
INSERT INTO `cbs_store_goods_out_item` VALUES (2, 1, 1, 2.00, 2, '2019-10-26 14:17:53');
INSERT INTO `cbs_store_goods_out_item` VALUES (3, 3, 30, 20000.00, 10, '2019-11-04 17:59:25');
INSERT INTO `cbs_store_goods_out_item` VALUES (4, 3, 31, 12000.00, 7, '2019-11-04 17:59:25');
INSERT INTO `cbs_store_goods_out_item` VALUES (5, 4, 30, 20000.00, 10, '2019-11-04 18:00:36');
INSERT INTO `cbs_store_goods_out_item` VALUES (6, 4, 32, 2000.00, 10, '2019-11-04 18:00:36');
INSERT INTO `cbs_store_goods_out_item` VALUES (7, 4, 31, 12200.00, 7, '2019-11-04 18:00:36');
INSERT INTO `cbs_store_goods_out_item` VALUES (8, 4, 33, 10.00, 7, '2019-11-04 18:00:36');
INSERT INTO `cbs_store_goods_out_item` VALUES (17, 5, 30, 2000.00, 10, '2019-11-08 17:27:21');
INSERT INTO `cbs_store_goods_out_item` VALUES (18, 5, 31, 123.00, 7, '2019-11-08 17:27:21');
INSERT INTO `cbs_store_goods_out_item` VALUES (19, 6, 26, 100.00, 10, '2019-11-08 17:27:29');
INSERT INTO `cbs_store_goods_out_item` VALUES (20, 6, 27, 10.00, 7, '2019-11-08 17:27:29');
INSERT INTO `cbs_store_goods_out_item` VALUES (21, 22, 26, 1000.00, 10, '2019-11-08 18:21:27');
INSERT INTO `cbs_store_goods_out_item` VALUES (22, 22, 27, 200.00, 7, '2019-11-08 18:21:27');

-- ----------------------------
-- Table structure for cbs_store_goods_out_produce
-- ----------------------------
DROP TABLE IF EXISTS `cbs_store_goods_out_produce`;
CREATE TABLE `cbs_store_goods_out_produce`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_store_goods_out_id` bigint(20) NULL DEFAULT NULL COMMENT '关联cbs_store_goods_out表，构成继承关系',
  `fk_produce_id` bigint(20) NULL DEFAULT NULL COMMENT '关联生产表（cbs_produce）ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生产出库表，与cbs_store_goods_out表为1对1继承关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cbs_store_goods_out_produce
-- ----------------------------
INSERT INTO `cbs_store_goods_out_produce` VALUES (1, 1, 1);
INSERT INTO `cbs_store_goods_out_produce` VALUES (2, 2, 1);
INSERT INTO `cbs_store_goods_out_produce` VALUES (3, 3, 5);
INSERT INTO `cbs_store_goods_out_produce` VALUES (4, 4, 5);
INSERT INTO `cbs_store_goods_out_produce` VALUES (5, 5, 5);
INSERT INTO `cbs_store_goods_out_produce` VALUES (6, 6, 5);
INSERT INTO `cbs_store_goods_out_produce` VALUES (11, 16, NULL);
INSERT INTO `cbs_store_goods_out_produce` VALUES (12, 17, 5);
INSERT INTO `cbs_store_goods_out_produce` VALUES (13, 18, 5);
INSERT INTO `cbs_store_goods_out_produce` VALUES (14, 19, 5);
INSERT INTO `cbs_store_goods_out_produce` VALUES (15, 20, 5);
INSERT INTO `cbs_store_goods_out_produce` VALUES (16, 21, 5);
INSERT INTO `cbs_store_goods_out_produce` VALUES (17, 22, 5);

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'GMT+08:00');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', NULL, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000016C710DFD707874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RenrenScheduler', 'DESKTOP-NUBBO1N1573206138204', 1573208569040, 15000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', NULL, 1565265600000, -1, 5, 'PAUSED', 'CRON', 1565264929000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000016C710DFD707874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000017800);

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES (1, 'testTask', 'renren', '0 0/30 * * * ?', 1, '参数测试', '2019-08-08 19:47:18');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `job_id`(`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES (1, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-08 20:00:00');
INSERT INTO `schedule_job_log` VALUES (2, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-08 20:30:00');
INSERT INTO `schedule_job_log` VALUES (3, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-08 21:00:00');
INSERT INTO `schedule_job_log` VALUES (4, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-09 12:00:00');
INSERT INTO `schedule_job_log` VALUES (5, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-09 12:30:00');
INSERT INTO `schedule_job_log` VALUES (6, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-09 13:00:00');
INSERT INTO `schedule_job_log` VALUES (7, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-09 13:30:00');
INSERT INTO `schedule_job_log` VALUES (8, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-09 14:00:00');
INSERT INTO `schedule_job_log` VALUES (9, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-09 14:30:00');
INSERT INTO `schedule_job_log` VALUES (10, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-09 15:00:00');
INSERT INTO `schedule_job_log` VALUES (11, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-09 15:30:00');
INSERT INTO `schedule_job_log` VALUES (12, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-09 16:00:00');
INSERT INTO `schedule_job_log` VALUES (13, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-09 16:30:00');
INSERT INTO `schedule_job_log` VALUES (14, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-09 17:00:00');
INSERT INTO `schedule_job_log` VALUES (15, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-09 17:30:00');
INSERT INTO `schedule_job_log` VALUES (16, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-09 18:00:00');
INSERT INTO `schedule_job_log` VALUES (17, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-09 18:30:00');
INSERT INTO `schedule_job_log` VALUES (18, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-09 19:00:00');
INSERT INTO `schedule_job_log` VALUES (19, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-09 19:30:00');
INSERT INTO `schedule_job_log` VALUES (20, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-10 10:30:00');
INSERT INTO `schedule_job_log` VALUES (21, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-10 11:00:00');
INSERT INTO `schedule_job_log` VALUES (22, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-10 12:00:00');
INSERT INTO `schedule_job_log` VALUES (23, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-10 12:30:00');
INSERT INTO `schedule_job_log` VALUES (24, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-10 13:00:00');
INSERT INTO `schedule_job_log` VALUES (25, 1, 'testTask', 'renren', 0, NULL, 0, '2019-08-10 13:30:00');
INSERT INTO `schedule_job_log` VALUES (26, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-12 10:00:00');
INSERT INTO `schedule_job_log` VALUES (27, 1, 'testTask', 'renren', 0, NULL, 1, '2019-08-12 10:30:00');

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
  `code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '验证码',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统验证码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------
INSERT INTO `sys_captcha` VALUES ('3160ed4d-2e1b-41db-824a-efc7abcb5534', 'dmgfy', '2019-08-08 20:41:24');
INSERT INTO `sys_captcha` VALUES ('69b50dbe-df0b-4fdc-8332-c93c66d05156', 'f7bga', '2019-08-09 15:20:06');
INSERT INTO `sys_captcha` VALUES ('98cdd351-9adc-48b4-8a16-c338d5ba6542', 'w5e6n', '2019-08-09 15:19:39');
INSERT INTO `sys_captcha` VALUES ('c470d6be-034d-41e7-8e69-cea662935cc6', 'yxwff', '2019-08-09 15:26:00');
INSERT INTO `sys_captcha` VALUES ('d4a597c1-d473-4b7a-8c4a-acd980015e3e', '4y3df', '2019-08-08 20:40:55');

-- ----------------------------
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国内运输公司名称',
  `contactor` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司地址',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1 COMMENT '0为不可用1为可用',
  `effective_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '有效时间',
  `logo_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户公司表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_company
-- ----------------------------
INSERT INTO `sys_company` VALUES (1, '运输公司1', '黄先生', '15998426716', '运输街11号', '2019-08-28 17:36:09', NULL, 1, '2019-10-17 16:36:18', NULL, NULL);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `param_key`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', 0, '云存储配置信息');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', NULL, NULL, 0, 'system', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3);
INSERT INTO `sys_menu` VALUES (5, 1, 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', 'sys:cbscontract:list,sys:cbscontract:info,sys:cbscontract:save,sys:cbscontract:saveReturnId,sys:cbscontract:update,sys:cbscontract:delete,sys:cbscontract:status,sys:cbscontract:listForType,sys:contractrecord:list,sys:contractrecord:info,sys:contractrecord:save,sys:contractrecord:update,sys:contractrecord:delete,sys:directorycustomsbroker:list,sys:directorycustomsbroker:info,sys:directorycustomsbroker:save,sys:directorycustomsbroker:update,sys:directorycustomsbroker:delete,sys:directoryproducecompany:list,sys:directoryproducecompany:info,sys:directoryproducecompany:save,sys:directoryproducecompany:update,sys:directoryproducecompany:delete,sys:directoryproduct:list,sys:directoryproduct:info,sys:directoryproduct:save,sys:directoryproduct:update,sys:directoryproduct:delete,sys:directoryrawmaterial:list,sys:directoryrawmaterial:info,sys:directoryrawmaterial:save,sys:directoryrawmaterial:update,sys:directoryrawmaterial:delete,sys:directoryshipcompany:list,sys:directoryshipcompany:info,sys:directoryshipcompany:save,sys:directoryshipcompany:update,sys:directoryshipcompany:delete,sys:directorystore:list,sys:directorystore:info,sys:directorystore:save,sys:directorystore:update,sys:directorystore:delete,sys:directorytradeobject:list,sys:directorytradeobject:info,sys:directorytradeobject:save,sys:directorytradeobject:update,sys:directorytradeobject:delete,sys:directorytransitcompany:list,sys:directorytransitcompany:info,sys:directorytransitcompany:save,sys:directorytransitcompany:update,sys:directorytransitcompany:delete,sys:export:list,sys:export:info,sys:export:save,sys:export:update,sys:export:delete,sys:imgcontractpurchase:list,sys:imgcontractpurchase:info,sys:imgcontractpurchase:save,sys:imgcontractpurchase:update,sys:imgcontractpurchase:delete,sys:imgcontractrecord:list,sys:imgcontractrecord:info,sys:imgcontractrecord:save,sys:imgcontractrecord:update,sys:imgcontractrecord:delete,sys:imgcontractsell:list,sys:imgcontractsell:info,sys:imgcontractsell:save,sys:imgcontractsell:update,sys:imgcontractsell:delete,sys:imgcontractship:list,sys:imgcontractship:info,sys:imgcontractship:save,sys:imgcontractship:update,sys:imgcontractship:delete,sys:imgexportinvoice:list,sys:imgexportinvoice:info,sys:imgexportinvoice:save,sys:imgexportinvoice:update,sys:imgexportinvoice:delete,sys:imgimportinvoice:list,sys:imgimportinvoice:info,sys:imgimportinvoice:save,sys:imgimportinvoice:update,sys:imgimportinvoice:delete,sys:imgpackinglist:list,sys:imgpackinglist:info,sys:imgpackinglist:save,sys:imgpackinglist:update,sys:imgpackinglist:delete,sys:import:list,sys:import:info,sys:import:save,sys:import:update,sys:import:delete,sys:produce:list,sys:produce:info,sys:produce:save,sys:produce:update,sys:produce:delete,sys:storeproductin:list,sys:storeproductin:info,sys:storeproductin:save,sys:storeproductin:update,sys:storeproductin:delete,sys:storeproductout:list,sys:storeproductout:info,sys:storeproductout:save,sys:storeproductout:update,sys:storeproductout:delete,sys:storerawmaterialin:list,sys:storerawmaterialin:info,sys:storerawmaterialin:save,sys:storerawmaterialin:update,sys:storerawmaterialin:delete,sys:storerawmaterialout:list,sys:storerawmaterialout:info,sys:storerawmaterialout:save,sys:storerawmaterialout:update,sys:storerawmaterialout:delete,sys:cbsdirectoryproduct:list,sys:cbsdirectoryproduct:info,sys:cbsdirectoryproduct:save,sys:cbsdirectoryproduct:update,sys:cbsdirectoryproduct:delete,sys:cbsdirectorytradeobject:list,sys:cbsdirectorytradeobject:info,sys:cbsdirectorytradeobject:save,sys:cbsdirectorytradeobject:update,sys:cbsdirectorytradeobject:delete,sys:cbsdirectoryrawmaterial:list,sys:cbsdirectoryrawmaterial:info,sys:cbsdirectoryrawmaterial:save,sys:cbsdirectoryrawmaterial:update,sys:cbsdirectoryrawmaterial:delete,sys:cbsdirectorytransitcompany:list,sys:cbsdirectorytransitcompany:info,sys:cbsdirectorytransitcompany:save,sys:cbsdirectorytransitcompany:update,sys:cbsdirectorytransitcompany:delete,sys:cbsimport:saveReturnId,sys:cbsimport:list,sys:cbsimport:info,sys:cbsimport:save,sys:cbsimport:update,sys:cbsimport:delete,sys:cbsdirectoryshipcompany:list,sys:cbsdirectoryshipcompany:info,sys:cbsdirectoryshipcompany:save,sys:cbsdirectoryshipcompany:update,sys:cbsdirectoryshipcompany:delete,sys:cbsdirectorycustomsbroker:list,sys:cbsdirectorycustomsbroker:info,sys:cbsdirectorycustomsbroker:save,sys:cbsdirectorycustomsbroker:update,sys:cbsdirectorycustomsbroker:delete,sys:cbsstoreproduct:list,sys:cbsstoreproduct:info,sys:cbsstoreproduct:save,sys:cbsstoreproduct:update,sys:cbsstoreproduct:delete,sys:cbsstoreproductin:list,sys:cbsstoreproductin:info,sys:cbsstoreproductin:save,sys:cbsstoreproductin:saveReturnId,sys:cbsstoreproductin:update,sys:cbsstoreproductin:confirm,sys:cbsstoreproductin:delete,sys:cbsstoreproductout:list,sys:cbsstoreproductout:info,sys:cbsstoreproductout:save,sys:cbsstoreproductout:saveReturnId,sys:cbsstoreproductout:update,sys:cbsstoreproductout:delete,sys:cbsstorerawmaterial:list,sys:cbsstorerawmaterial:info,sys:cbsstorerawmaterial:save,sys:cbsstorerawmaterial:update,sys:cbsstorerawmaterial:delete,sys:cbsstorerawmaterialin:list,sys:cbsstorerawmaterialin:info,sys:cbsstorerawmaterialin:save,sys:cbsstorerawmaterialin:saveReturnId,sys:cbsstorerawmaterialin:update,sys:cbsstorerawmaterialin:delete,sys:cbsimport:list,sys:cbsimport:info,sys:cbsimport:save,sys:cbsimport:saveReturnId,sys:cbsimport:update,sys:cbsimport:delete,sys:cbsimport:status,sys:cbsstorerawmaterialout:list,sys:cbsstorerawmaterialout:info,sys:cbsstorerawmaterialout:save,sys:cbsstorerawmaterialout:saveReturnId,sys:cbsstorerawmaterialout:update,sys:cbsstorerawmaterialout:delete,sys:cbsproduce:list,sys:cbsproduce:info,sys:cbsproduce:save,sys:cbsproduce:saveReturnId,sys:cbsproduce:update,sys:cbsproduce:delete,sys:cbsproduce:status,sys:cbsimgpackinglist:list,sys:cbsimgpackinglist:info,sys:cbsimgpackinglist:save,sys:cbsimgpackinglist:update,sys:cbsimgpackinglist:delete,sys:cbsimgimportothers:list,sys:cbsimgimportothers:info,sys:cbsimgimportothers:save,sys:cbsimgimportothers:update,sys:cbsimgimportothers:delete,sys:cbsimgimportladingbill:list,sys:cbsimgimportladingbill:info,sys:cbsimgimportladingbill:save,sys:cbsimgimportladingbill:update,sys:cbsimgimportladingbill:delete,sys:cbsimgimportinvoice:list,sys:cbsimgimportinvoice:info,sys:cbsimgimportinvoice:save,sys:cbsimgimportinvoice:update,sys:cbsimgimportinvoice:delete,sys:cbsimgimportentrybill:list,sys:cbsimgimportentrybill:info,sys:cbsimgimportentrybill:save,sys:cbsimgimportentrybill:update,sys:cbsimgimportentrybill:delete,sys:cbsimgimportdeliveryorder:list,sys:cbsimgimportdeliveryorder:info,sys:cbsimgimportdeliveryorder:save,sys:cbsimgimportdeliveryorder:update,sys:cbsimgimportdeliveryorder:delete,sys:cbsimgexportinvoice:list,sys:cbsimgexportinvoice:info,sys:cbsimgexportinvoice:save,sys:cbsimgexportinvoice:update,sys:cbsimgexportinvoice:delete,sys:cbsimgcontractship:list,sys:cbsimgcontractship:info,sys:cbsimgcontractship:save,sys:cbsimgcontractship:update,sys:cbsimgcontractship:delete,sys:cbsimgcontractsell:list,sys:cbsimgcontractsell:info,sys:cbsimgcontractsell:save,sys:cbsimgcontractsell:update,sys:cbsimgcontractsell:delete,sys:cbsimgcontractrecord:list,sys:cbsimgcontractrecord:info,sys:cbsimgcontractrecord:save,sys:cbsimgcontractrecord:update,sys:cbsimgcontractrecord:delete,sys:cbsimgcontractpurchase:list,sys:cbsimgcontractpurchase:info,sys:cbsimgcontractpurchase:save,sys:cbsimgcontractpurchase:update,sys:cbsimgcontractpurchase:delete,sys:cbsexport:list,sys:cbsexport:info,sys:cbsexport:save,sys:cbsexport:saveReturnId,sys:cbsexport:update,sys:cbsexport:delete,sys:cbsexport:status,sys:cbsdirectorystore:list,sys:cbsdirectorystore:info,sys:cbsdirectorystore:save,sys:cbsdirectorystore:update,sys:cbsdirectorystore:delete,sys:cbsdirectoryproducecompany:list,sys:cbsdirectoryproducecompany:info,sys:cbsdirectoryproducecompany:save,sys:cbsdirectoryproducecompany:update,sys:cbsdirectoryproducecompany:delete,sys:cbscontractrecord:list,sys:cbscontractrecord:info,sys:cbscontractrecord:save,sys:cbscontractrecord:update,sys:cbscontractrecord:delete,sys:cbsstorerawmaterialin:listInContract,sys:cbsdirectorygoods:list,sys:cbsdirectorygoods:info,sys:cbsdirectorygoods:save,sys:cbsdirectorygoods:update,sys:cbsdirectorygoods:delete,sys:cbscurrency:listInContract,sys:cbscurrency:list,sys:cbscurrency:info,sys:cbscurrency:save,sys:cbscurrency:update,sys:cbscurrency:delete,sys:syscompany:list,sys:syscompany:info,sys:syscompany:save,sys:syscompany:update,sys:syscompany:delete,sys:cbsstoregoodsin:list,sys:cbsstoregoodsin:info,sys:cbsstoregoodsin:save,sys:cbsstoregoodsin:update,sys:cbsstoregoodsin:delete,sys:cbsstoregoodsin:saveReturnId,sys:cbsstoregoodsout:list,sys:cbsstoregoodsout:info,sys:cbsstoregoodsout:save,sys:cbsstoregoodsout:update,sys:cbsstoregoodsout:delete,sys:cbsstoregoodsin:listImportInByOutToProduceId,sys:cbsstoregoodsout:saveReturnId,sys:cbsproducegoodsstream:save', 1, 'sql', 4);
INSERT INTO `sys_menu` VALUES (6, 1, '定时任务', 'job/schedule', '', 1, 'job', 5);
INSERT INTO `sys_menu` VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO `sys_menu` VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);
INSERT INTO `sys_menu` VALUES (101, 4, '提审', '', 'sys:cbscontract:bringIntoCourt', 2, '', 0);
INSERT INTO `sys_menu` VALUES (102, 4, '删除', '', 'sys:cbscontract:deleteOne', 2, '', 0);
INSERT INTO `sys_menu` VALUES (103, 4, '审核', '', 'sys:cbscontract:check', 2, '', 0);

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件上传' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '老板', NULL, 1, '2019-10-17 09:49:10');
INSERT INTO `sys_role` VALUES (2, '总务', NULL, 1, '2019-10-17 09:49:15');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 3, 5);
INSERT INTO `sys_role_menu` VALUES (3, 2, 103);
INSERT INTO `sys_role_menu` VALUES (4, 3, 101);
INSERT INTO `sys_role_menu` VALUES (5, 3, 102);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', 1, 1, '2016-11-11 11:11:11', NULL);
INSERT INTO `sys_user` VALUES (2, 'laoban', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '17777777777', 1, 1, '2019-10-17 09:50:13', NULL);
INSERT INTO `sys_user` VALUES (3, 'zongwu', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '18888888888', 1, 1, '2019-10-17 09:50:13', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 2, 1);
INSERT INTO `sys_user_role` VALUES (2, 3, 2);

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'token',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户Token' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES (1, '142c120d2ac784cbd9e9f2b6f979c417', '2019-11-08 22:06:41', '2019-11-08 15:09:28');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');

-- ----------------------------
-- Function structure for getChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildList`;
delimiter ;;
CREATE FUNCTION `getChildList`(`rootId` int)
 RETURNS varchar(1000) CHARSET utf8mb4
  DETERMINISTIC
BEGIN
DECLARE sTemp VARCHAR(1000);
DECLARE sTempChd VARCHAR(1000);

SET sTemp = '$';
SET sTempChd =cast(rootId as CHAR);

WHILE sTempChd is not null DO
SET sTemp = concat(sTemp,',',sTempChd);
SELECT group_concat(category_id) INTO sTempChd FROM tb_category where FIND_IN_SET(parent_id,sTempChd)>0;
END WHILE;
RETURN sTemp;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
