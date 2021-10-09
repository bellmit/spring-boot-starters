--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `login_log` (
    `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `tenant`      varchar(64)   NOT NULL DEFAULT '' COMMENT '租户标识',
    `ip`	        char(19)      NULL     DEFAULT '' COMMENT 'IP',
    `location`	  varchar(128)  NULL     DEFAULT '' COMMENT '位置',
    `browser`	    varchar(64)   NULL     DEFAULT '' COMMENT '浏览器',
    `os`	        varchar(64)   NULL     DEFAULT '' COMMENT '操作系统',
    `username` varchar(64)   NOT NULL DEFAULT '' COMMENT '操作人编号',
    `remark`      longtext	    NULL DEFAULT NULL COMMENT '备注',
    `record_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    `create_time` datetime      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    primary key (id),
    key idx_username (username)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='登录日志表';

--rollback DROP TABLE IF EXISTS `login_log`;

--comment: 登录日志表