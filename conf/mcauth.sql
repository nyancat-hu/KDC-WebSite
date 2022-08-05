SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for w_profile
-- ----------------------------
DROP TABLE IF EXISTS `w_profile`;
CREATE TABLE `w_profile`  (
  `w_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'UUID',
  `w_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `w_uid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户所属用户',
  `w_skin_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '皮肤ID',
  `w_cape_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '披风ID',
  PRIMARY KEY (`w_id`) USING BTREE,
  UNIQUE INDEX `w_name`(`w_name`) USING BTREE,
  INDEX `w_uid`(`w_uid`) USING BTREE,
  INDEX `w_skin_id`(`w_skin_id`) USING BTREE,
  INDEX `w_cape_id`(`w_cape_id`) USING BTREE,
  CONSTRAINT `w_profile_ibfk_1` FOREIGN KEY (`w_uid`) REFERENCES `w_user` (`w_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `w_profile_ibfk_2` FOREIGN KEY (`w_skin_id`) REFERENCES `w_texture` (`w_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `w_profile_ibfk_3` FOREIGN KEY (`w_cape_id`) REFERENCES `w_texture` (`w_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for w_texture
-- ----------------------------
DROP TABLE IF EXISTS `w_texture`;
CREATE TABLE `w_texture`  (
  `w_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'UUID',
  `w_hash` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'hash',
  `w_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材质名称',
  `w_type` enum('STEVE','ALEX','CAPE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'STEVE' COMMENT '材质类型（0: Steve; 1: Alex; 3: Cape）',
  `w_size` int(11) NULL DEFAULT NULL COMMENT '占用空间（字节）',
  `w_upid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传者',
  `w_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`w_id`) USING BTREE,
  INDEX `w_uid`(`w_upid`) USING BTREE,
  CONSTRAINT `w_texture_ibfk_1` FOREIGN KEY (`w_upid`) REFERENCES `w_user` (`w_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for w_token
-- ----------------------------
DROP TABLE IF EXISTS `w_token`;
CREATE TABLE `w_token`  (
  `w_client_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端token',
  `w_access_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作token',
  `w_uid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `w_pid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  `w_create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `w_access_time` datetime(0) NOT NULL COMMENT '上次刷新时间',
  PRIMARY KEY (`w_client_token`, `w_access_token`) USING BTREE,
  UNIQUE INDEX `w_access_id`(`w_access_token`) USING BTREE,
  INDEX `w_uid`(`w_uid`) USING BTREE,
  INDEX `w_pid`(`w_pid`) USING BTREE,
  CONSTRAINT `w_token_ibfk_1` FOREIGN KEY (`w_uid`) REFERENCES `w_user` (`w_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `w_token_ibfk_2` FOREIGN KEY (`w_pid`) REFERENCES `w_profile` (`w_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for w_user
-- ----------------------------
DROP TABLE IF EXISTS `w_user`;
CREATE TABLE `w_user`  (
  `w_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'UUID',
  `w_nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `w_username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `w_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `w_password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `w_last_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上次登录IP',
  PRIMARY KEY (`w_id`) USING BTREE,
  UNIQUE INDEX `w_username`(`w_username`) USING BTREE,
  UNIQUE INDEX `w_email`(`w_email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
