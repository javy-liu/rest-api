
CREATE TABLE IF NOT EXISTS `user` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(20) NOT NULL,
	`nickname` VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
);

insert into `user` (`username`, `nickname`) values ('oyach', '欧阳澄泓');