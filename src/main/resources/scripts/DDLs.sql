--User Registration APP Queries

--05082025
--Script for inserting admin user to users table manually
INSERT INTO users (name, email, password, role)
VALUES ('Admin User', 'admin@example.com', 'admin123', 'ROLE_ADMIN');