CREATE TABLE t_users (
    id BIGSERIAL NOT NULL,
    name VARCHAR(255) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    role VARCHAR(255),
    user_type VARCHAR(255),

    PRIMARY KEY (id)

);--match model table. will be executed when boot up-will execute in naming order