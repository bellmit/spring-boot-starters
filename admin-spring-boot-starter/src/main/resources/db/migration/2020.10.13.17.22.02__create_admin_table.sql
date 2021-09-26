--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `admin`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_id` char(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效：1有效 0无效',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除: 0未删除 1已删除',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX uk_admin_id(admin_id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='管理员';

--rollback DROP TABLE IF EXISTS `admin`;

--comment: 管理员表