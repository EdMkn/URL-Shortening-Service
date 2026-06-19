# URL-Shortening-Service

A RESTful API service for creating, managing, and tracking shortened URLs with analytics capabilities.

## 📋 Description

URL-Shortening-Service provides a complete URL shortening solution with features like custom aliases, QR codes, analytics, and link expiration.

## 🛠️ Tech Stack

- **Language**: Java
- **Framework**: Spring Boot
- **Database**: PostgreSQL / MySQL
- **Build Tool**: Maven
- **API Documentation**: Swagger/OpenAPI
- **Caching**: Redis
- **Testing**: JUnit 5, Mockito

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- PostgreSQL/MySQL
- Redis (optional, for caching)
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/EdMkn/URL-Shortening-Service.git
   cd URL-Shortening-Service
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Configure database**
   ```bash
   # Create application.properties
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   
   # Edit database configuration
   # spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortener
   # spring.datasource.username=postgres
   # spring.datasource.password=password
   ```

### Running the Application

```bash
# Using Maven
mvn spring-boot:run

# Or run JAR
java -jar target/url-shortening-service-1.0.0.jar
```

API available at `http://localhost:8080`
Swagger UI: `http://localhost:8080/swagger-ui.html`

## 📁 Project Structure

```
URL-Shortening-Service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/urlshortener/
│   │   │       ├── controller/        # REST controllers
│   │   │       ├── service/           # Business logic
│   │   │       ├── repository/        # Data access
│   │   │       ├── model/             # Entity models
│   │   │       ├── dto/               # Data transfer objects
│   │   │       ├── exception/         # Custom exceptions
│   │   │       └── Application.java   # Main entry
│   │   └── resources/
│   │       └── application.properties
│   └── test/                          # Unit tests
├── pom.xml                            # Maven config
└── README.md
```

## 🔑 Key Features

- **URL Shortening** - Convert long URLs to short codes
- **Custom Aliases** - Create custom short URLs
- **QR Codes** - Generate QR codes for short URLs
- **Analytics** - Track clicks and visitor information
- **Link Expiration** - Set expiration dates for links
- **Bulk Operations** - Shorten multiple URLs at once
- **API Rate Limiting** - Prevent abuse
- **User Management** - User accounts and API keys
- **Admin Dashboard** - Manage links and users

## 📚 API Endpoints

### Shorten URL
```
POST /api/shorten
Content-Type: application/json

{
  "longUrl": "https://www.example.com/very/long/url",
  "customAlias": "mylink",
  "expiresAt": "2024-12-31T23:59:59Z"
}

Response (201):
{
  "id": "abc123",
  "shortUrl": "http://short.url/mylink",
  "longUrl": "https://www.example.com/very/long/url",
  "shortCode": "mylink",
  "createdAt": "2024-01-01T00:00:00Z",
  "expiresAt": "2024-12-31T23:59:59Z",
  "clicks": 0
}
```

### Redirect to Long URL
```
GET /:shortCode

Response (302): Redirect to original URL
```

### Get Link Details
```
GET /api/links/:shortCode

Response:
{
  "id": "abc123",
  "shortUrl": "http://short.url/mylink",
  "longUrl": "https://www.example.com/very/long/url",
  "shortCode": "mylink",
  "createdAt": "2024-01-01T00:00:00Z",
  "clicks": 42,
  "lastClickedAt": "2024-01-15T10:30:00Z"
}
```

### Get Analytics
```
GET /api/links/:shortCode/analytics

Response:
{
  "shortCode": "mylink",
  "totalClicks": 42,
  "uniqueClicks": 28,
  "clicksByDate": [...],
  "referrers": [...],
  "devices": {...},
  "countries": [...]
}
```

### List All Links
```
GET /api/links?page=0&size=10&sort=createdAt,desc

Response:
{
  "content": [...],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 150,
  "totalPages": 15
}
```

### Update Link
```
PUT /api/links/:shortCode

{
  "expiresAt": "2024-12-31T23:59:59Z",
  "active": true
}
```

### Delete Link
```
DELETE /api/links/:shortCode

Response (204): No Content
```

### Generate QR Code
```
GET /api/links/:shortCode/qr

Response: QR code image (PNG)
```

## ⚙️ Configuration

### Application Properties

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortener
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Caching
spring.redis.host=localhost
spring.redis.port=6379
spring.cache.type=redis

# App Configuration
app.base-url=http://localhost:8080
app.short-code-length=6
app.max-redirect-attempts=3
app.rate-limit=1000

# Logging
logging.level.root=INFO
logging.level.com.urlshortener=DEBUG
```

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UrlServiceTest

# Coverage report
mvn jacoco:report
```

## 📊 Database Schema

```sql
CREATE TABLE urls (
    id BIGINT PRIMARY KEY,
    short_code VARCHAR(100) UNIQUE NOT NULL,
    long_url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    active BOOLEAN DEFAULT true,
    user_id BIGINT,
    clicks BIGINT DEFAULT 0
);

CREATE TABLE clicks (
    id BIGINT PRIMARY KEY,
    url_id BIGINT NOT NULL,
    clicked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    referrer VARCHAR(255),
    user_agent TEXT,
    country VARCHAR(2),
    device_type VARCHAR(50),
    FOREIGN KEY (url_id) REFERENCES urls(id)
);
```

## 🔐 Security

- JWT-based authentication
- API key management
- Rate limiting per user
- Input validation
- SQL injection prevention
- HTTPS requirement in production
- Admin-only endpoints protected

## 🎯 URL Algorithm

- Base62 encoding for short codes
- 6-character default short codes (56 billion+ combinations)
- Custom aliases supported
- Collision detection
- Uniqueness guaranteed

## 📈 Performance

- Redis caching for frequently accessed URLs
- Database indexing on short_code
- Connection pooling
- Async click logging
- Query optimization

## 🐛 Troubleshooting

### Database Connection Failed
```bash
# Verify PostgreSQL is running
# Check connection properties
# Verify user permissions
```

### Redis Not Working
```bash
# Disable caching in application.properties
spring.cache.type=simple
```

### Performance Issues
```bash
# Increase connection pool
spring.datasource.hikari.maximum-pool-size=20
# Enable caching
spring.cache.type=redis
```

## 🤝 Contributing

Contributions welcome! Please:

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Open Pull Request

## 📄 License

Open source project. See LICENSE for details.

## 💬 Support

For issues and questions, open an issue on GitHub.
