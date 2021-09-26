--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `lookup`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `catalog` varchar(64) NOT NULL COMMENT '类型',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `code` integer NOT NULL COMMENT '编码',
  `remark` text NOT NULL COMMENT '备注',
  `sort` tinyint NOT NULL DEFAULT 0 COMMENT '排序',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效：1有效 0无效',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_catalog_name` (`catalog`, `name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典表';

--rollback DROP TABLE IF EXISTS `lookup`;

--comment: 字典表