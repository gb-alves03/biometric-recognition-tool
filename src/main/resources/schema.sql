CREATE TABLE IF NOT EXISTS accounts (
    account_id VARCHAR(255),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    biometric_enabled BOOLEAN NOT NULL,
    PRIMARY KEY(account_id)
);

CREATE TABLE IF NOT EXISTS biometric_urls (
    account_id VARCHAR(255),
    url VARCHAR(255),
    PRIMARY KEY(account_id),
    CONSTRAINT fk_account
        FOREIGN KEY(account_id)
            REFERENCES accounts(account_id)
);