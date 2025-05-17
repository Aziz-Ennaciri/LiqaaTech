# LiqaaTech Event Management System

A comprehensive event management system built with Spring Boot and React, featuring user authentication, event management, ticket sales, and more.

## Features

- User Authentication and Authorization
- Event Management (Create, Read, Update, Delete)
- Ticket Sales and Management
- Event Categories and Tags
- Search and Filtering
- File Upload (Images, Documents)
- Email Notifications
- Responsive Design
- RESTful API
- Swagger Documentation

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- MySQL
- JWT Authentication
- Maven
- Swagger/OpenAPI

### Frontend
- React 18
- TypeScript
- Material-UI
- Redux Toolkit
- React Router
- Axios
- Formik & Yup

## Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- MySQL 8.0 or higher
- Maven
- npm or yarn

## Getting Started

### Backend Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/liqaatech.git
cd liqaatech
```

2. Configure the database:
- Create a MySQL database named `liqaatech`
- Update `application.properties` with your database credentials

3. Build and run the backend:
```bash
mvn clean install
mvn spring-boot:run
```

The backend will be available at `http://localhost:8080/api`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```

The frontend will be available at `http://localhost:3000`

## API Documentation

Swagger documentation is available at:
- Swagger UI: `http://localhost:8080/api/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api/api-docs`

## Project Structure

```
liqaatech/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── LiqaaTech/
│   │   │           ├── Config/
│   │   │           ├── Controllers/
│   │   │           ├── DTOs/
│   │   │           ├── Entities/
│   │   │           ├── Exceptions/
│   │   │           ├── Repositories/
│   │   │           ├── Security/
│   │   │           └── Services/
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
├── frontend/
│   ├── public/
│   └── src/
│       ├── components/
│       ├── pages/
│       ├── services/
│       ├── store/
│       └── utils/
└── pom.xml
```

## Security

- JWT-based authentication
- Role-based access control
- Password encryption
- CORS configuration
- Input validation
- File upload security

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, email support@liqaatech.com or create an issue in the repository. 