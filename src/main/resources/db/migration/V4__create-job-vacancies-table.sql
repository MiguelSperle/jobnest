CREATE TABLE job_vacancies (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    seniority_level VARCHAR(12) NOT NULL,
    modality VARCHAR(7) NOT NULL,
    company_name VARCHAR(80) NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL
);