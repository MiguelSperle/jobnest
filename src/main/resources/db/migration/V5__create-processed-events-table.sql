CREATE TABLE processed_events(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    event_id VARCHAR(36) NOT NULL,
    consumed_by VARCHAR(255) NOT NULL,
    processed_at TIMESTAMP NOT NULL
);