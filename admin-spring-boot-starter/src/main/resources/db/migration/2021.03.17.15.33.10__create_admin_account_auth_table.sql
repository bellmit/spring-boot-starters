--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `admin_account_auth`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account_id` char(20) NOT NULL COMMENT '管理员编号',
  `source` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '认证方式: 0用户名 10手机号 20微信 30QQ 40Apple',
  `source_id` varchar(255) NOT NULL COMMENT '认证唯一标识',
  `source_token` varchar(255) NULL COMMENT '认证凭证: 密码或令牌',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效：0禁用 1启用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除: 0未删除 1已删除',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_account_id` (`account_id`),
  KEY `idx_source_id` (`source`, `source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='管理员账号授权';

--rollback DROP TABLE IF EXISTS `admin_account_auth`;

--comment: 管理员账号授权表