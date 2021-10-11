--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `access_log` (
    `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `tenant`      varchar(64)   NOT NULL DEFAULT '' COMMENT '租户标识',
    `ip`	  char(19)      NULL     DEFAULT '' COMMENT 'IP',
    `browser`	  varchar(64)   NULL     DEFAULT '' COMMENT '浏览器',
    `os`	  varchar(64)   NULL     DEFAULT '' COMMENT '操作系统',
    `url`     	  varchar(256)  NOT NULL DEFAULT '' COMMENT '访问地址',
    `request_method`  char(6) NOT NULL DEFAULT 'GET' COMMENT '请求方式',
    `request_params` text  NULL DEFAULT NULL COMMENT '请求参数',
    `request_id` char(64)  NULL DEFAULT NULL COMMENT '请求编号',
    `operator_id` varchar(64)   NULL DEFAULT NULL COMMENT '操作人编号',
    `operator_name` varchar(64) NULL DEFAULT NULL COMMENT '操作人名称',
    `location`	  varchar(128)  NULL DEFAULT NULL COMMENT '位置',
    `remark`      longtext	    NULL DEFAULT NULL COMMENT '备注',
    `record_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    `create_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    primary key (id),
    key idx_ip (ip),
    key idx_request_id (request_id),
    key idx_operator_id (operator_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='访问日志表';

--rollback DROP TABLE IF EXISTS `access_log`;

--comment: 访问日志表