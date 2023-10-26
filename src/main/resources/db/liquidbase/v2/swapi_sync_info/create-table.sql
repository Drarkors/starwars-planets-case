CREATE TABLE swapi_sync_info (
    entity VARCHAR(120) DEFAULT NOT NULL,
    total_records SMALLINT NOT NULL DEFAULT 0,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (entity)
);