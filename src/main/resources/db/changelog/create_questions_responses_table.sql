create table if not exists questions_responses(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    test_id             UUID not null,
    question_id         UUID not null,
    number              integer,
    question_type       TEXT,
    responses TEXT[],
    constraint foreign_questionsx foreign key(question_id) references questions(id)
);

CREATE INDEX if not exists idx_questions_responses_test ON tests(id);
CREATE INDEX if not exists idx_questions_responses_quesion ON questions(id);

alter table tests add column if not exists status TEXT;
alter table tests add column if not exists question_count int;