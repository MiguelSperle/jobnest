CREATE TABLE subscriptions (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    job_vacancy_id VARCHAR(36) NOT NULL,
    resume_url VARCHAR(255) NOT NULL,
    is_canceled BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL 
);