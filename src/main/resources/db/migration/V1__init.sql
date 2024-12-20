CREATE TABLE row_setting(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    row_type VARCHAR(30) NOT NULL,
    row_id VARCHAR(30) NOT NULL,
    seq INT NOT NULL,
    enabled TINYINT(1) NOT NULL,
    created_at DATETIME(6),
    updated_at DATETIME(6)
);
