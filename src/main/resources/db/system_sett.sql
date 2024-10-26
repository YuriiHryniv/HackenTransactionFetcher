CREATE TABLE system_sett
(
    id          INT       DEFAULT nextval('system_sett_id_seq') PRIMARY KEY,
    name        TEXT NOT NULL,
    value       TEXT NOT NULL,
    last_edit   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_user   TEXT NOT NULL,
    description TEXT
);

INSERT INTO system_sett (name, value, last_user, description)
VALUES ('ethereum_job_enabled', 'true', 'system_admin', 'Flag to control execution of ethereum job');
