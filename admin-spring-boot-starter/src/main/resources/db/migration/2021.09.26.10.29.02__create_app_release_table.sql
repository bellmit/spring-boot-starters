--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `app_release`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` varchar(54) NOT NULL COMMENT '版本名称',
  `version_num` integer NOT NULL COMMENT '版本编号',
  `catalog` tinyint NOT NULL COMMENT '类别: 1安卓 2iOS',
  `url` varchar(255) NOT NULL COMMENT '下载地址',
  `force_upgrade` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否强制升级: 0否 1是',
  `description` text NOT NULL COMMENT '升级说明',
  `hash` varchar(255) NULL COMMENT '文件hash',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效：1有效 0无效',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除: 0未删除 1已删除',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_catalog` (`catalog`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='管理员';

--rollback DROP TABLE IF EXISTS `app_release`;

--comment: App发布表