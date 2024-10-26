CREATE TABLE withdrawal
(
    id              INT DEFAULT nextval('withdrawal_id_seq') PRIMARY KEY,
    block_id        INT NOT NULL,
    index           TEXT,
    validator_index TEXT,
    address         TEXT,
    amount          TEXT,
    CONSTRAINT fk_withdrawal_block FOREIGN KEY (block_id) REFERENCES block (id)
);
