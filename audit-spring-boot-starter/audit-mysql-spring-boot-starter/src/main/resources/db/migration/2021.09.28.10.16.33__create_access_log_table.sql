--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `access_log` (
    `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `operator_id` varchar(64)   NULL DEFAULT '' COMMENT '操作人编号',
    `operator_name` varchar(64) NULL DEFAULT '' COMMENT '操作人名称',
    `ip`	  char(19)      NULL     DEFAULT '' COMMENT 'IP',
    `location`	  varchar(128)  NULL     DEFAULT '' COMMENT '位置',
    `browser`	  varchar(64)   NULL     DEFAULT '' COMMENT '浏览器',
    `os`	  varchar(64)   NULL     DEFAULT '' COMMENT '操作系统',
    `url`     	  varchar(256)  NOT NULL DEFAULT '' COMMENT '访问地址',
    `request_method`  char(6) NOT NULL DEFAULT 'GET' COMMENT '请求方式',
    `request_params` text  NULL DEFAULT '' COMMENT '请求参数',
    `remark`      longtext	NULL COMMENT '备注',
    `record_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    `create_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    primary key (id),
    key idx_biz_key (biz_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='访问日志表';

--rollback DROP TABLE IF EXISTS `access_log`;

--comment: 访问日志表