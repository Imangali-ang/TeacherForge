create table if not exists questions(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    test_id             UUID is not null,
    question_type       TEXT,
    number              integer,
    details       jsonb,
    constraint foreign_testsx foreign key(test_id) references tests(id)
);