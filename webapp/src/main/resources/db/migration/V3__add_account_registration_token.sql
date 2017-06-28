CREATE TABLE account_registration_token (
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	created DATETIME NOT NULL,
	last_updated DATETIME NOT NULL,
	token_string CHAR(36) NOT NULL,
	expiration_date DATETIME NOT NULL,
	a_id BIGINT NOT NULL
);

ALTER TABLE account_registration_token
	ADD FOREIGN KEY (a_id) REFERENCES account(id) ON UPDATE CASCADE ON DELETE CASCADE;