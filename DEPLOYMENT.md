# Deployment Guide - Visitor Gate Pass Management System

## 📋 Prerequisites

### Required Software
- **Java 17 or higher** - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.9+** - Download from [Apache Maven](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - Download from [MySQL](https://dev.mysql.com/downloads/mysql/)

### Database Options

#### Option 1: Local MySQL (For Testing/Small Scale)
1. Download and install MySQL 8.0+
2. Start MySQL service
3. Create database

#### Option 2: Aiven Cloud MySQL (Current Production Database)
The project is configured with Aiven Cloud MySQL. Connection details:
- **Host:** mysql-3831cb6b-sseraladen-55f4.c.aivencloud.com
- **Port:** 10392
- **Database:** defaultdb
- **User:** avnadmin
- **Password:** d1b7db3a
- **SSL Mode:** REQUIRED

To use Aiven MySQL, uncomment the MySQL configuration in `application.properties` and comment out H2.

#### Option 3: Other Cloud MySQL Providers
- **AWS RDS** - https://aws.amazon.com/rds/mysql/
- **Google Cloud SQL** - https://cloud.google.com/sql/docs/mysql
- **Azure Database for MySQL** - https://azure.microsoft.com/en-us/products/mysql
- **PlanetScale** - https://planetscale.com/ (Recommended for modern apps)
- **DigitalOcean Managed Database** - https://www.digitalocean.com/products/managed-databases/

## 🗄️ Database Setup

### Step 1: Create Database
```sql
CREATE DATABASE visitor_gate_pass_system;
```

### Step 2: Create Database User (Recommended)
```sql
CREATE USER 'gatepass_user'@'localhost' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON visitor_gate_pass_system.* TO 'gatepass_user'@'localhost';
FLUSH PRIVILEGES;
```

### Step 3: Configure Application

#### For Local Development
Edit `backend/src/main/resources/application.properties`:

**Option A: H2 Database (Current - Development)**
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

**Option B: Local MySQL**
```properties
# Comment out H2 configuration
# spring.datasource.url=jdbc:h2:mem:testdb
# spring.datasource.driverClassName=org.h2.Driver
# spring.datasource.username=sa
# spring.datasource.password=

# Uncomment MySQL configuration
spring.datasource.url=jdbc:mysql://localhost:3306/visitor_gate_pass_system
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

**Option C: Aiven Cloud MySQL (Production)**
```properties
# Comment out H2 configuration
# spring.datasource.url=jdbc:h2:mem:testdb
# spring.datasource.driverClassName=org.h2.Driver
# spring.datasource.username=sa
# spring.datasource.password=

# Uncomment Aiven MySQL configuration
spring.datasource.url=jdbc:mysql://mysql-3831cb6b-sseraladen-55f4.c.aivencloud.com:10392/defaultdb?ssl-mode=REQUIRED&useSSL=true&trustServerCertificate=true
spring.datasource.username=avnadmin
spring.datasource.password=d1b7db3a
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

#### For Production
Use `application-production.properties`:
```bash
java -jar -Dspring.profiles.active=production target/vision-entry-gate-pass-1.0.0.jar
```

## 🚀 Deployment Platforms

### Platform 1: Traditional VPS (DigitalOcean, Linode, AWS EC2)

#### Steps:
1. **Server Setup**
   - Install Java 17
   - Install Maven
   - Install MySQL 8.0
   - Configure firewall (allow port 8080)

2. **Build Application**
   ```bash
   cd backend
   mvn clean package -DskipTests
   ```

3. **Deploy**
   ```bash
   java -jar target/vision-entry-gate-pass-1.0.0.jar
   ```

4. **Use Systemd Service** (Recommended)
   Create `/etc/systemd/system/visitor-gate.service`:
   ```ini
   [Unit]
   Description=Visitor Gate Pass Management System
   After=syslog.target network.target

   [Service]
   Type=simple
   User=www-data
   WorkingDirectory=/opt/visitor-gate
   ExecStart=/usr/bin/java -jar /opt/visitor-gate/target/vision-entry-gate-pass-1.0.0.jar
   Restart=always

   [Install]
   WantedBy=multi-user.target
   ```

### Platform 2: Docker Containerization

#### Create Dockerfile
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/vision-entry-gate-pass-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Build and Run
```bash
# Build
docker build -t visitor-gate-pass .

# Run
docker run -p 8080:8080 --name visitor-gate visitor-gate-pass
```

### Platform 3: Cloud Platforms

#### A. Heroku
1. Create `Procfile`:
   ```
   web: java -jar target/vision-entry-gate-pass-1.0.0.jar
   ```

2. Deploy:
   ```bash
   heroku create visitor-gate-pass
   heroku addons:create heroku-postgresql
   git push heroku main
   ```

#### B. AWS Elastic Beanstalk
1. Create WAR file:
   ```bash
   mvn package -DskipTests
   ```

2. Upload to Elastic Beanstalk
3. Configure environment variables for database

#### C. Google Cloud Platform (App Engine)
1. Create `app.yaml`:
   ```yaml
   runtime: java17
   entrypoint: java -jar target/vision-entry-gate-pass-1.0.0.jar
   ```

2. Deploy:
   ```bash
   gcloud app deploy
   ```

## 🔒 Security Considerations

### 1. Environment Variables
Never commit sensitive data. Use environment variables:
```bash
export DB_URL=jdbc:mysql://localhost:3306/visitor_gate_pass_system
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

### 2. SSL/HTTPS
- Use Nginx or Apache as reverse proxy
- Configure SSL certificates (Let's Encrypt is free)
- Redirect HTTP to HTTPS

### 3. Database Security
- Use strong passwords
- Restrict database access to specific IPs
- Enable SSL for database connections
- Regular backups

## 📊 Database Schema

### Tables Created Automatically:
1. **visitor** - Visitor information
2. **gate_pass** - Gate pass details
3. **appointment** - Appointment requests
4. **vehicle** - Vehicle tracking
5. **entry_log** - Entry/exit logs
6. **employee** - Employee/host information
7. **users** - User authentication

## 🔧 Configuration Files

### For Development (H2 Database)
- `application.properties` - Uses H2 in-memory database

### For Production (MySQL)
- `application-production.properties` - Uses MySQL
- Activate with: `-Dspring.profiles.active=production`

## 📝 Environment Variables (Recommended)

Create `.env` file (add to .gitignore):
```properties
DB_URL=jdbc:mysql://localhost:3306/visitor_gate_pass_system
DB_USERNAME=root
DB_PASSWORD=your_password
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=production
```

## 🚦 Quick Start Commands

### Build
```bash
cd backend
mvn clean package -DskipTests
```

### Run (Development)
```bash
mvn spring-boot:run
```

### Run (Production)
```bash
java -jar target/vision-entry-gate-pass-1.0.0.jar
```

### Run with Production Profile
```bash
java -jar -Dspring.profiles.active=production target/vision-entry-gate-pass-1.0.0.jar
```

## 📱 Access URLs

### Local Development
- Application: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console

### Production
- Application: http://your-server-ip:8080
- With SSL: https://your-domain.com

## 🔍 Troubleshooting

### Common Issues

1. **Port 8080 already in use**
   - Kill process using port 8080
   - Or change port in application.properties

2. **Database connection failed**
   - Check MySQL service is running
   - Verify database credentials
   - Check firewall settings

3. **Out of memory**
   - Increase JVM heap size: `-Xmx2g`
   - Optimize database queries

## 📞 Support

For issues or questions:
- Check logs in `logs/application.log`
- Review Spring Boot documentation
- Check MySQL error logs

## 🎯 Recommended Deployment Stack

### For Small/Medium Applications:
- **Server**: DigitalOcean Droplet (2GB RAM, 1 CPU)
- **Database**: Managed MySQL Database
- **Reverse Proxy**: Nginx
- **SSL**: Let's Encrypt
- **Cost**: ~$20-30/month

### For Large/Enterprise Applications:
- **Server**: AWS EC2 or Google Compute Engine
- **Database**: AWS RDS or Google Cloud SQL
- **Load Balancer**: AWS ALB or Google Cloud Load Balancing
- **CDN**: CloudFlare
- **Monitoring**: AWS CloudWatch or Google Cloud Monitoring
- **Cost**: $100-500+/month depending on scale
