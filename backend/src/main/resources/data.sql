-- Initial data for Vision Entry Gate Pass Management System
-- This file is executed automatically when the application starts

-- Insert default admin user (password: admin123)
-- Note: The password should be BCrypt encoded in production
INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');

-- Insert sample employee
INSERT INTO employee (employee_id, name, department, email, phone) VALUES ('EMP001', 'John Smith', 'IT', 'john@company.com', '1234567890');