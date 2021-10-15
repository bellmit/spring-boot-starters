--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `terms`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `terms_id` char(20) NOT NULL COMMENT '条款编号',
  `name` varchar(128) NOT NULL COMMENT '条款名称',
  `content` longtext NOT NULL COMMENT '条款内容',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效：1有效 0无效',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除: 0未删除 1已删除',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX uk_terms_id(terms_id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='条款';

--rollback DROP TABLE IF EXISTS `terms`;

--comment: 条款表