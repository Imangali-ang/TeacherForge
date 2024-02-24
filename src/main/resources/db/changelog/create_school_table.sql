create table if not exists region(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name                TEXT NOT NULL
);

INSERT INTO region (name) VALUES
                              ('Abay'),
                              ('Akmola'),
                              ('Aktobe'),
                              ('Alma-Ata''s'),
                              ('Atyrau'),
                              ('East Kazakhstan'),
                              ('Jambyl'),
                              ('Zhetysu'),
                              ('West-Kazakhstan'),
                              ('Karaganda'),
                              ('Kostanay'),
                              ('Kyzylorda'),
                              ('Mangistau'),
                              ('Pavlodar'),
                              ('North-Kazakhstan'),
                              ('Turkestan'),
                              ('Ulytau'),
                              ('Almaty'),
                              ('Astana'),
                              ('Shymkent');

create table if not exists schools(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    domain              TEXT NOT NULL,
    name                TEXT NOT NULL,
    region_id           UUID NOT NULL,
    address             TEXT NOT NULL,
    status              TEXT NOT NULL,
    type                TEXT NOT NULL,
    constraint foreign_reg foreign key(region_id) references region(id)
);