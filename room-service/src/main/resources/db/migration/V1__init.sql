CREATE TABLE t_rooms (
    id BIGSERIAL NOT NULL,
    availability BOOLEAN DEFAULT true,
    room_name VARCHAR(255),
    features VARCHAR(255),
    price DECIMAL (6,2),
    capacity NUMERIC,


    PRIMARY KEY (id)


);--match model table. will be executed when boot up-will execute in naming order