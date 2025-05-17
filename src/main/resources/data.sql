-- Insert test users
INSERT INTO users (username, email, password, first_name, last_name, created_at, updated_at)
VALUES 
('admin', 'admin@liqaatech.com', '$2a$10$rDkPvvAFV6GgJkKq8K6UeOQZQZQZQZQZQZQZQZQZQZQZQZQZQZQ', 'Admin', 'User', NOW(), NOW()),
('john.doe', 'john@example.com', '$2a$10$rDkPvvAFV6GgJkKq8K6UeOQZQZQZQZQZQZQZQZQZQZQZQZQZQZQ', 'John', 'Doe', NOW(), NOW()),
('jane.smith', 'jane@example.com', '$2a$10$rDkPvvAFV6GgJkKq8K6UeOQZQZQZQZQZQZQZQZQZQZQZQZQZQZQ', 'Jane', 'Smith', NOW(), NOW())
ON DUPLICATE KEY UPDATE username = VALUES(username);

-- Insert roles if they don't exist
INSERT INTO roles (name) 
SELECT 'ROLE_USER' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_USER');

INSERT INTO roles (name) 
SELECT 'ROLE_ADMIN' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN');

INSERT INTO roles (name) 
SELECT 'ROLE_ORGANIZER' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ORGANIZER');

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username IN ('john.doe', 'jane.smith') AND r.name = 'ROLE_USER'
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id); 