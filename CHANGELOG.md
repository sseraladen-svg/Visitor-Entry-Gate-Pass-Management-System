# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2024-08-12

### Added
- Initial project setup with Spring Boot 3.5.0
- User authentication with database credentials
- Gate pass creation and management system
- Real-time gate pass verification
- Database integration with H2 for development
- HTML/CSS frontend with 4 pages (index, login, create-pass, verify-pass)
- Service layer implementation for proper MVC architecture
- Employee entity for managing company employees
- EntryLog entity for tracking visitor entry/exit
- MySQL database configuration support
- Comprehensive documentation (Problem Statement, README)
- System diagrams (Architecture, ER, Class diagrams)
- Project structure following Java/Spring Boot conventions

### Changed
- Updated authentication to use database-backed user validation
- Refactored controllers to use service layer
- Improved project structure with proper separation of concerns

### Fixed
- Database persistence issues resolved
- Security configuration updated for proper authentication flow

## [Unreleased]

### Planned
- JWT token-based authentication
- Enhanced reporting and analytics
- Mobile application development
- QR code generation for gate passes