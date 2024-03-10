CREATE TABLE IF NOT EXISTS reports
(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    student_id          UUID not null,
    report_type_id      UUID ,
    violation_time     TIMESTAMP,
    document_ids        TEXT,
    lesson              TEXT,
    place               TEXT,
    status              TEXT,
    worked_by_id        UUID,
    created_time        TIMESTAMP,
    created_by_id       UUID,
    comments            TEXT
    );

CREATE INDEX if not exists idx_student_id ON reports(student_id);

CREATE TABLE IF NOT EXISTS reports_type
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name            TEXT,
    description     TEXT
);

INSERT INTO reports_type (name, description)
VALUES
    ('Non-compliance with the rules of appearance', 'Non-compliance with the rules of appearance.'),

    ('Using electronic devices in inappropriate places', 'Using the phone during the lesson.'),

    ('Non-compliance with the educational regime', 'Ignoring homework. Plagiarism or copying the work of other students.'),

    ('Skipping lessons or being late', 'Frequent lateness to lessons. Unjustified skipping classes.'),

    ('Violation of safety rules', 'Unsafe handling of equipment.'),

    ('Incorrect communication', 'The use of profanity. Abusive statements directed at school staff or students.'),

    ('Violation of order and discipline', 'Failure to follow the instructions of the teacher. Disturbing behavior in the classroom.'),

    ('Aggressive behavior', 'Physical fights. Threatening or intimidating other students. Initiating conflicts between students or groups of students.'),

    ('Minor hooliganism', 'Theft of personal property of students or teachers, as well as theft of educational materials or school equipment. The use, possession or distribution of illegal items (alcohol, cigarette, pornography) within the school. Intentional damage to school property or equipment. Damage to the property of teachers or students.'),

    ('Other', 'If the violation does not correspond to any of the above points, the teacher will be able to select the “Other“ item in the disciplinary report, when selecting this item, a text field will appear in which the teacher will be able to describe the violation and in the future this should facilitate the work of the psychologist.');
