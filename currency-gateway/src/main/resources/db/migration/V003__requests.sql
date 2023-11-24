CREATE TABLE requests
(
    id           VARCHAR(32) NOT NULL PRIMARY KEY,
    currency     VARCHAR(3)  NOT NULL,
    client_id    BIGINT(20)  NOT NULL,
    timestamp    TIMESTAMP   NOT NULL,
    query_period INT,
    service_name VARCHAR(32) NOT NULL,
    completed    TINYINT(1)  NOT NULL DEFAULT 0,

    UNIQUE id_service_unq (id, service_name)
);