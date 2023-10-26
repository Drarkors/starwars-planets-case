CREATE TABLE planets (
    id uuid DEFAULT uuid_generate_v4(),
    name VARCHAR(120) NOT NULL,
    climate VARCHAR(120) NOT NULL,
    terrain VARCHAR(120) NOT NULL,
    swapi_id INTEGER NULL DEFAULT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NULL,
    deleted_at TIMESTAMPTZ DEFAULT NULL,
    PRIMARY KEY (id)
);