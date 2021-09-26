--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `config`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `catalog` varchar(64) NOT NULL COMMENT '类别',
  `name` varchar(64) NULL DEFAULT '' COMMENT '名称',
  `content` longtext NOT NULL COMMENT '内容',
  `remark` text NOT NULL COMMENT '备注',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效：1有效 0无效',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_catalog_name` (`catalog`, `name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='配置表';

--rollback DROP TABLE IF EXISTS `config`;

--comment: 配置表