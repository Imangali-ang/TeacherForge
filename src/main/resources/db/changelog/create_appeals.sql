create table if not exists appeals(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    topic text not null,
    text  TEXT not null,
    created TIMESTAMP,
    created_by UUID,
    school_id   UUID,
    is_read     BOOLEAN,
    document_ids    text,
    deleted         BOOLEAN,
    constraint foreign_appeals_school_idx foreign key(school_id) references schools(id)
);

CREATE INDEX if not exists idx_appeals_create ON appeals(created_by);
CREATE INDEX if not exists idx_appeals_school ON appeals(school_id);