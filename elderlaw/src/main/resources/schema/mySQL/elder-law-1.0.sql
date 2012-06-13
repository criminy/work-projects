CREATE TABLE `Lawyer` (
`id` CHAR( 33 ) NOT NULL ,
`name` VARCHAR( 256 ) NOT NULL ,
`email` VARCHAR( 256 ) NOT NULL ,
`phone` VARCHAR( 256 ) NOT NULL ,
`address` VARCHAR( 512 ) NOT NULL ,
`standing` VARCHAR( 256 ) NOT NULL ,
`insurance` BOOL NOT NULL DEFAULT '1',
`barnumber` VARCHAR( 256 ) NOT NULL ,
`presentation` BOOL NOT NULL DEFAULT '1',
PRIMARY KEY ( `id` )
) ENGINE = InnoDB COMMENT = 'this is a lawyer person';

CREATE TABLE `County` (
`id` CHAR( 33 ) NOT NULL ,
`name` VARCHAR( 256 ) NOT NULL ,
PRIMARY KEY ( `id` )
) ENGINE = InnoDB COMMENT = 'table of counties';

CREATE TABLE `LegalArea` (
`id` CHAR( 33 ) NOT NULL ,
`name` VARCHAR( 256 ) NOT NULL ,
PRIMARY KEY ( `id` )
) ENGINE = InnoDB COMMENT = 'table of legal areas';

CREATE TABLE `CaseStatus` (
`id` CHAR( 33 ) NOT NULL ,
`startDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`endDate` TIMESTAMP NULL ,
`name` VARCHAR( 256 ) NOT NULL ,
`county` VARCHAR( 256 ) NOT NULL ,
`area` VARCHAR( 256 ) NOT NULL ,
`status` BOOL NOT NULL DEFAULT '1' COMMENT 'open case is true, closed case is false',
PRIMARY KEY ( `id` )
) ENGINE = InnoDB COMMENT = 'table of cases';

CREATE TABLE `Comment` (
`id` CHAR( 33 ) NOT NULL ,
`date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`user` VARCHAR( 256 ) NOT NULL ,
`comment` VARCHAR( 1024 ) NOT NULL ,
PRIMARY KEY ( `id` )
) ENGINE = InnoDB COMMENT = 'table of comments';

CREATE TABLE `joinedLawyerCounty` (
`lawyer_id` CHAR( 33 ) NOT NULL ,
`county_id` CHAR( 33 ) NOT NULL
) ENGINE = InnoDB COMMENT = 'table of counties tied to lawyers';

ALTER TABLE `joinedLawyerCounty` ADD INDEX `lawyer_index` ( `lawyer_id` );
ALTER TABLE `joinedLawyerCounty` ADD INDEX `county_index` ( `county_id` );

ALTER TABLE `joinedLawyerCounty` ADD FOREIGN KEY ( `lawyer_id` ) REFERENCES `Lawyer` (
`id`
) ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE `joinedLawyerCounty` ADD FOREIGN KEY ( `county_id` ) REFERENCES `County` (
`id`
) ON DELETE CASCADE ON UPDATE CASCADE ;

CREATE TABLE `joinedLawyerArea` (
`lawyer_id` CHAR( 33 ) NOT NULL ,
`area_id` CHAR( 33 ) NOT NULL
) ENGINE = InnoDB COMMENT = 'table of areas tied to legal areas';

ALTER TABLE `joinedLawyerArea` ADD INDEX `lawyer_index` ( `lawyer_id` );
ALTER TABLE `joinedLawyerArea` ADD INDEX `area_index` ( `area_id` );

ALTER TABLE `joinedLawyerArea` ADD FOREIGN KEY ( `lawyer_id` ) REFERENCES `Lawyer` (
`id`
) ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE `joinedLawyerArea` ADD FOREIGN KEY ( `area_id` ) REFERENCES `LegalArea` (
`id`
) ON DELETE CASCADE ON UPDATE CASCADE ;

CREATE TABLE `joinedLawyerCaseStatus` (
`lawyer_id` CHAR( 33 ) NOT NULL ,
`casestatus_id` CHAR( 33 ) NOT NULL
) ENGINE = InnoDB COMMENT = 'table of cases tied to lawyers';

ALTER TABLE `joinedLawyerCaseStatus` ADD INDEX `lawyer_index` ( `lawyer_id` );
ALTER TABLE `joinedLawyerCaseStatus` ADD INDEX `casestatus_index` ( `casestatus_id` );

ALTER TABLE `joinedLawyerCaseStatus` ADD FOREIGN KEY ( `lawyer_id` ) REFERENCES `Lawyer` (
`id`
) ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE `joinedLawyerCaseStatus` ADD FOREIGN KEY ( `casestatus_id` ) REFERENCES `CaseStatus` (
`id`
) ON DELETE CASCADE ON UPDATE CASCADE ;

CREATE TABLE `joinedLawyerComment` (
`lawyer_id` CHAR( 33 ) NOT NULL ,
`comment_id` CHAR( 33 ) NOT NULL
) ENGINE = InnoDB COMMENT = 'table of comments tied to lawyers';

ALTER TABLE `DEV_Elder-Law`.`joinedLawyerComment` ADD INDEX `lawyer_index` ( `lawyer_id` );
ALTER TABLE `DEV_Elder-Law`.`joinedLawyerComment` ADD INDEX `comment_index` ( `comment_id` );

ALTER TABLE `joinedLawyerComment` ADD FOREIGN KEY ( `lawyer_id` ) REFERENCES `DEV_Elder-Law`.`Lawyer` (
`id`
) ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE `joinedLawyerComment` ADD FOREIGN KEY ( `comment_id` ) REFERENCES `DEV_Elder-Law`.`Comment` (
`id`
) ON DELETE CASCADE ON UPDATE CASCADE ;
