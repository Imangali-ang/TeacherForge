create table if not exists file(
                     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                     name text,
                     purpose TEXT,
                     uploaded_by_id UUID,
                     received_by_id UUID,
                     time TIMESTAMP
);