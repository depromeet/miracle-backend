CREATE OR REPLACE TABLE `record`
(
    `id` bigint AUTO_INCREMENT,
    `member_id` BIGINT NOT NULL,
    `schedule_id` BIGINT NOT NULL,
    `start_date_time` DATETIME(6) NOT NULL,
    `end_date_time` DATETIME(6) NOT NULL,
    `category` VARCHAR(255) NOT NULL,
    `question` VARCHAR(100) NOT NULL,
    `answer` VARCHAR(100) NOT NULL,
    `created_date_time` datetime(6) DEFAULT NULL,
    `last_modified_date_time` datetime(6) DEFAULT NULL,

    PRIMARY KEY(`id`)
);
