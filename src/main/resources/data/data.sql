CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS account (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    balance NUMERIC(12, 2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS category (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(10) CHECK (type IN ('INCOME', 'EXPENSE')) NOT NULL
    CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS transaction (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL,
    category_id UUID NOT NULL,
    amount NUMERIC(12, 2) NOT NULL,
    date TIMESTAMP NOT NULL,
    note TEXT,
    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Update passwords to hashed versions
UPDATE users 
SET password = '$2a$12$Da20.2k4xCRBoJv8hG.MBu0erGQ8KokQ46zaZg7WLlS9d.WCLe/LO' 
WHERE username = 'eric901209';

UPDATE users 
SET password = '$2a$12$RutGLGdl9Xyl.Uvf1KYA1uHceM6FZweJbgdWm6776aRxyNiKw/2oe' 
WHERE username = 'testuser';
