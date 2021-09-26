--liquibase formatted sql

--changeset zacksleo:1.0.0

CREATE TABLE `ad`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `position_id` int NOT NULL COMMENT '广告位编号',
  `name` varchar(64) NULL DEFAULT '' COMMENT '名称',
  `catalog` tinyint NOT NULL DEFAULT 0 COMMENT '类别: 0图片 1文本 2视频',
  `content` text NOT NULL COMMENT '内容',
  `url` varchar(255) NULL DEFAULT '#' COMMENT '链接',
  `sort` tinyint NOT NULL DEFAULT 0 COMMENT '排序',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效：1有效 0无效',
  `start_time` datetime NULL COMMENT '开始时间',
  `end_time` datetime NULL COMMENT '结束时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_position_id` (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='广告表';

--rollback DROP TABLE IF EXISTS `ad`;

--comment: 广告表