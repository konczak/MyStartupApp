CREATE TABLE questionnaire_template (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	author_id BIGINT NOT NULL,
	created_at TIMESTAMP NOT NULL,
	LANGUAGE VARCHAR(5) NOT NULL,
	NAME VARCHAR(250) NOT NULL UNIQUE,
	updated_at TIMESTAMP NOT NULL,
	updated_by BIGINT NOT NULL,
	version BIGINT NOT NULL
	);


