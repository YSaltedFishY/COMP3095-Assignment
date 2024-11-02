CREATE TABLE t_users (
    id BIGSERIAL NOT NULL,
    email VARCHAR(255) DEFAULT NULL,
    userType VARCHAR(255),
    role VARCHAR(255),
    PRIMARY KEY (id)

);--match model table. will be executed when boot up-will execute in naming order