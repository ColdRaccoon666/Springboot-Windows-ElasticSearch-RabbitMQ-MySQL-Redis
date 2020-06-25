/*
 Navicat Premium Data Transfer

 Source Server         : testOne
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 25/06/2020 15:53:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stutwo
-- ----------------------------
DROP TABLE IF EXISTS `stutwo`;
CREATE TABLE `stutwo`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stutwo
-- ----------------------------
INSERT INTO `stutwo` VALUES ('123', ' 123');
INSERT INTO `stutwo` VALUES ('123456', '123456');
INSERT INTO `stutwo` VALUES ('18', '18');
INSERT INTO `stutwo` VALUES ('32133', '3213123');
INSERT INTO `stutwo` VALUES ('5', '123456789');
INSERT INTO `stutwo` VALUES ('7', '123456789');
INSERT INTO `stutwo` VALUES ('789789', '789789789789');
INSERT INTO `stutwo` VALUES ('8', '87');
INSERT INTO `stutwo` VALUES ('888', '库里');

SET FOREIGN_KEY_CHECKS = 1;
