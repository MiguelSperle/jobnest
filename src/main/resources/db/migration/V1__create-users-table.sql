CREATE TABLE users (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(155) UNIQUE NOT NULL,
    description VARCHAR(1000),
    password VARCHAR(100) NOT NULL,
    status VARCHAR(10) NOT NULL,
    authorization_role VARCHAR(10) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL
);