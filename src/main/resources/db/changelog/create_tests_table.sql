CREATE TABLE IF NOT EXISTS tests
(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title               TEXT,
    description         TEXT,
    teacher_ids         TEXT,
    send_all            BOOLEAN,
    created_id          UUID,
    addressed           TEXT,
    addressed_num       integer,
    created_time        TIMESTAMP,
    answered            TEXT
);


CREATE INDEX if not exists idx_tests_created ON tests(created_id);
