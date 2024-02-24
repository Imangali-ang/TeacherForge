create table if not exists public.token
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    expired    BOOLEAN DEFAULT FALSE,
    revoked    BOOLEAN DEFAULT FALSE,
    token      TEXT,
    token_type varchar(255)
        constraint token_token_type_check
            check ((token_type)::text = 'BEARER'::text),
    user_id    UUID not null
);

CREATE INDEX idx_token ON token(token);