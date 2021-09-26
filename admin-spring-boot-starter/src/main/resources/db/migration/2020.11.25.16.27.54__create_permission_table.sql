--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `permission`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `permission_id` char(20) NOT NULL COMMENT '编号',
  `http_method` char(6) NOT NULL DEFAULT 'GET' COMMENT '请求方式',
  `name` varchar(32) NOT NULL DEFAULT '查询' COMMENT '权限名称',
  `uri_regex` varchar(128) NOT NULL COMMENT '路由表达式',
  `parent_id` char(20) NOT NULL DEFAULT 'ROOT' COMMENT '父级',
  `sort` smallint NOT NULL DEFAULT 0 COMMENT '序号',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除: 0未删除 1已删除',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='权限表';

--rollback DROP TABLE IF EXISTS `permission`;

--comment: 权限表