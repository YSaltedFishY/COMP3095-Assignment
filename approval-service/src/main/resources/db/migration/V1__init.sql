CREATE TABLE t_approvals(
                            id BIGSERIAL PRIMARY KEY,
                            event_id VARCHAR(255) NOT NULL,
                            user_id BIGSERIAL NOT NULL,
                            status VARCHAR(50) NOT NULL,
                            time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);