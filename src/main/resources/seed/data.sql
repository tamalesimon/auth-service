-- Active: 1744738446853@@127.0.0.1@5001@db
CREATE TABLE IF NOT EXISTS "users" (    id UUID PRIMARY KEY,
                                        email VARCHAR(255) NOT NULL UNIQUE,
                                        password VARCHAR(255) NOT NULL,
                                        role VARCHAR(50) NOT NULL,
                                        status VARCHAR(50) NOT NULL,
                                        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (id, email, password, role, status, created_at)
VALUES (
    'c8cc86c7-26a6-44d0-8825-56f8b9a44b55',
    'john.doe@example.com',
    '$2a$10$WqDdPUKh5xLQIZcMKZJnAeRqkUp6Gagnt63aStXPxR.cPzQ96J6Ui',
    'SUPER_ADMIN',
    'ACTIVE',
    Now()
)
ON CONFLICT (email) DO NOTHING;