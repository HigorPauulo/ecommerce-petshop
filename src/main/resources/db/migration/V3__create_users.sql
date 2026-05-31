-- Tabela de usuarios para autenticacao JWT

CREATE TABLE users (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(150) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    role       VARCHAR(20)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT now()
);

-- O usuario admin inicial e criado em runtime pelo DataSeeder (senha BCrypt),
-- evitando hash hardcoded neste script.
