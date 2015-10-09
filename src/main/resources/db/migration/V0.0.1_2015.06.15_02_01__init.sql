CREATE TABLE employee (
	id BIGINT NOT NULL auto_increment,
	created_at DATETIME NOT NULL,
	firstname VARCHAR(255) NOT NULL,
	guid BINARY (255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	mail VARCHAR(255) NOT NULL,
	title VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	version BIGINT NOT NULL,
   deleted boolean DEFAULT false,
   deleted_at DATETIME,
   updated_at DATETIME,
	PRIMARY KEY (id)
	);

ALTER TABLE employee ADD CONSTRAINT UNIQUE (guid);
