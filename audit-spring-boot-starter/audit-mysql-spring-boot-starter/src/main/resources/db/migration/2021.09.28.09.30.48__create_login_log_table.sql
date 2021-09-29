--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `login_log` (
    `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `tenant`      varchar(64)   NOT NULL DEFAULT '' COMMENT '租户标识',
    `ip`	        char(19)      NULL     DEFAULT '' COMMENT 'IP',
    `location`	  varchar(128)  NULL     DEFAULT '' COMMENT '位置',
    `browser`	    varchar(64)   NULL     DEFAULT '' COMMENT '浏览器',
    `os`	        varchar(64)   NULL     DEFAULT '' COMMENT '操作系统',
    `catalog`     varchar(128)  NOT NULL DEFAULT '' COMMENT '类别',
    `operator_id` varchar(64)   NOT NULL DEFAULT '' COMMENT '操作人编号',
    `operator_name` varchar(64)   NOT NULL DEFAULT '' COMMENT '操作人名称',
    `remark`   	  longtext      NULL     DEFAULT '' COMMENT '备注',
    `record_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    `create_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    primary key (id),
    key idx_biz_key (biz_key)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='登录日志表';

--rollback DROP TABLE IF EXISTS `login_log`;

--comment: 登录日志表