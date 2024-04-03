CREATE TABLE IF NOT EXISTS work_time
(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    report_id           UUID not null,
    title               TEXT not null,
    description         TEXT not null,
    work_date                timestamp,
    constraint foreign_work_time foreign key(report_id) references reports(id)
);