CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users
(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email               TEXT NOT NULL,
    created             TIMESTAMP,
    last_connection     TIMESTAMP,
    blocked             BOOLEAN DEFAULT FALSE,
    password            VARCHAR(255) NOT NULL,
    user_role           TEXT NOT NULL,
    username            TEXT,
    lastname            TEXT,
    middlename          TEXT,
    category            TEXT,
    "position"          TEXT,
    schoolId            UUID,
    CONSTRAINT unique_email UNIQUE (email),
    CONSTRAINT unique_username UNIQUE (username)
    );

CREATE INDEX idx_user_name ON users(username);
CREATE INDEX idx_email ON users(email);

CREATE TABLE IF NOT EXISTS email_code
(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code                TEXT NOT NULL,
    email               TEXT NOT NULL,
    sending_time        TIMESTAMP,
    used                BOOLEAN DEFAULT FALSE
);
