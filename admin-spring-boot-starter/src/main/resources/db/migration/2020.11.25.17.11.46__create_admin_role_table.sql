--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `admin_role`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_role_id` char(20) NOT NULL COMMENT '编号',
  `admin_id` char(20) NOT NULL COMMENT '管理员编号',
  `role_id` char(20) NOT NULL COMMENT '角色编号',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除: 0未删除 1已删除',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_admin_role_id` (`admin_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='管理员角色关联表';

--rollback DROP TABLE IF EXISTS `admin_role`;

--comment: 管理员角色关联表