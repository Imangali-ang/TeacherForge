create table if not exists students(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name                TEXT NOT NULL,
    surname             TEXT,
    middlename          TEXT,
    photo_id            UUID,
    phone_number        TEXT,
    birth_date          TIMESTAMP,
    gender              TEXT,
    nationality         TEXT,
    email               TEXT,
    class_room          TEXT,
    orphan              BOOLEAN DEFAULT FALSE,
    school_id           UUID,
    additional_properties       jsonb,
    constraint foreign_schoolx foreign key(school_id) references schools(id)
);