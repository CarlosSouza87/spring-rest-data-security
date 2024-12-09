-- sample.organization definition
CREATE OR REPLACE SEQUENCE `organizations_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 1 nocache nocycle ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `organizations` (
    `id` bigint(20) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `institution_name` varchar(255) DEFAULT NULL,
    `street` varchar(255) DEFAULT NULL,
    `city` varchar(255) DEFAULT NULL,
    `state` varchar(255) DEFAULT NULL,
    `country` varchar(255) DEFAULT NULL,
    `zipcode` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE `groups` ADD organization_id bigint(20) DEFAULT NULL;
ALTER TABLE `groups` ADD CONSTRAINT `fk_group_organization_id` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)