

create table gaje_ws_auth_vendor_court_access (
	id INT NOT NULL AUTO_INCREMENT,
	username varchar(50) NOT NULL,
	court_uuid varchar(32) NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(court_uuid) references court(uuid)) 
ENGINE=innoDB,
COMMENT = 'Association between LDAP GAJE-WS Vendors and Court entities.';
	

create table gaje_ws_court_stamp_metadata (
	id INT NOT NULL AUTO_INCREMENT,
	court_uuid varchar(32) NOT NULL,
	do_stamp boolean NOT NULL default TRUE,
	text varchar(200),
	x_position float NOT NULL,
	y_position float NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(court_uuid) references court(uuid))
ENGINE=innoDB,
COMMENT = 'Table which stores the court stamping information for the Court. Use \n for newline in text field.';

