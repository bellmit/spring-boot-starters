--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `banner_position`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `banner_position_id` char(20) NOT NULL COMMENT '广告位编号',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `size` varchar(64) NULL DEFAULT '' COMMENT '尺寸',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效：1有效 0无效',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_banner_position_id` (`banner_position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='广告位表';

--rollback DROP TABLE IF EXISTS `banner_position`;

--comment: 广告位表