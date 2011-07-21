CREATE TABLE `gaje_ws_audit_table` (
	`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
	`message` VARCHAR( 200 ) NOT NULL DEFAULT '""',
	`level` VARCHAR( 200 ) NOT NULL ,
	date DATETIME NOT NULL
) ENGINE = InnoDB COMMENT = 'Audit table for the gaje webservices method mailing';

CREATE OR REPLACE 
	ALGORITHM = UNDEFINED
	VIEW `gaje_ws_access_log` AS
		SELECT 
			id, 
			SUBSTRING_INDEX( `message` , ' ', 1 ) AS "method", 
			SUBSTRING_INDEX( SUBSTRING_INDEX( SUBSTRING_INDEX( `message` , ' ', -3 ) , ' ', 2 ) , ' ', 1 ) AS "court", 
			SUBSTRING_INDEX( SUBSTRING_INDEX( `message` , ' ', -2 ) , ' ', 1 ) AS "user",
			LEVEL , 
			date
		FROM 
			`gaje_ws_audit_table`;
			
			
