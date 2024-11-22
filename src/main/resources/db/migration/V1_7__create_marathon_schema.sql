-- sample.marathons definition
CREATE OR REPLACE SEQUENCE `marathons_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 1 nocache nocycle ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `marathons` (
    `id` bigint(20) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `weight` varchar(255) DEFAULT NULL,
    `score` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `member_marathon` (
    `member_id` bigint(20) NOT NULL,
    `marathon_id` bigint(20) NOT NULL,
    PRIMARY KEY (`member_id`, `marathon_id`),
    CONSTRAINT `fk_mm_member_id` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
    CONSTRAINT `fk_mm_marathon_id` FOREIGN KEY (`marathon_id`) REFERENCES `marathons` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;