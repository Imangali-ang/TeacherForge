create table if not exists schools(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    domain              TEXT NOT NULL,
    name                TEXT NOT NULL,
    number              int
);