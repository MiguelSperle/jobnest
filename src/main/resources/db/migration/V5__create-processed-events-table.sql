CREATE TABLE processed_events(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    event_id VARCHAR(36) NOT NULL,
    listener VARCHAR(100) NOT NULL,
    processed_at TIMESTAMP NOT NULL
);