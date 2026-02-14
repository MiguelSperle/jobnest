CREATE TABLE events_outbox (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    event_id VARCHAR(36) UNIQUE NOT NULL,
    payload TEXT NOT NULL,
    aggregate_id VARCHAR(36) NOT NULL,
    aggregate_type VARCHAR(40) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    status VARCHAR(10) NOT NULL,
    exchange VARCHAR(255) NOT NULL,
    routing_key VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);