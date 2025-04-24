-- Active: 1745360102568@@127.0.0.1@5432@postgres
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
    '$2a$12$bmZcgo7jRbjodHj3lP4nCun8QAQnct/B1LP6dFfc7H8JjGNjfjtdO',
    'SUPER_ADMIN',
    'ACTIVE',
    Now()
)
ON CONFLICT (email) DO NOTHING;