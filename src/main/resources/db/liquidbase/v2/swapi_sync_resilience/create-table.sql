CREATE TABLE swapi_sync_resilience (
    swapi_id INTEGER NOT NULL,
    total_tries SMALLINT NOT NULL DEFAULT 0,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NULL,
    deleted_at TIMESTAMPTZ DEFAULT NULL,
    PRIMARY KEY (swapi_id)
);