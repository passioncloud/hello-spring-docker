-- Spring boot will run automatically run this file during startup
DROP TABLE IF EXISTS sample_member;
CREATE TABLE sample_member (
    member_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    email_address VARCHAR(100)
);