CREATE TABLE rates
(
    base_currency_id   INT UNSIGNED   NOT NULL,
    target_currency_id INT UNSIGNED   NOT NULL,
    rate               DECIMAL(15, 6) NOT NULL,
    timestamp          TIMESTAMP      NOT NULL,

    PRIMARY KEY (base_currency_id, target_currency_id, timestamp),

    CONSTRAINT base_currency_fk FOREIGN KEY (base_currency_id) REFERENCES n_currencies (id),
    CONSTRAINT base_target_currency_fk FOREIGN KEY (target_currency_id) REFERENCES n_currencies (id)

);
