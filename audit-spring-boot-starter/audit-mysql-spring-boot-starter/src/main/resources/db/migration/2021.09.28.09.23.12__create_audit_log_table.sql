--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `audit_log` (
    `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `tenant`      varchar(128)  NOT NULL DEFAULT '' COMMENT '租户标识',
    `module`     varchar(128)   NOT NULL DEFAULT '' COMMENT '业务模块',
    `biz_no`      varchar(128)  NOT NULL DEFAULT '' COMMENT '业务编号',
    `operator_id` varchar(64)   NULL DEFAULT NULL COMMENT '操作人编号',
    `operator_name` varchar(64) NULL DEFAULT NULL COMMENT '操作人名称',
    `action`      varchar(128)  NOT NULL DEFAULT '' COMMENT '动作',
    `catalog`     varchar(128)  NOT NULL DEFAULT '' COMMENT '类别',
    `remark`      longtext	    NULL DEFAULT NULL COMMENT '备注',
    `record_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    `create_time` datetime      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    primary key (id),
    key idx_module (module)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='操作日志表';

--rollback DROP TABLE IF EXISTS `audit_log`;

--comment: 操作日志表