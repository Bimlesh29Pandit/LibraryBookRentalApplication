Here is a detailed `README.md` for your **Online Book Rental System** built using Spring Boot and MySQL:

---

````markdown
# 📚 Online Book Rental System

This is a Spring Boot-based RESTful web application for managing an online book rental system. It allows users to browse books, rent them (with limits), and return them. It features role-based access, JWT authentication, and MySQL persistence.

## 🔧 Technologies Used

- **Java 17**
- **Spring Boot 3**
- **Spring Security (JWT & Role-Based)**
- **Spring Data JPA**
- **MySQL**
- **ModelMapper**
- **Lombok**
- **Maven**

---

## ✅ Features

### Authentication & Authorization
- JWT-based authentication.
- Role-based access control using Spring Security.
- Two roles supported: `ADMIN`, `USER`.

### Users
- Register and login.
- Fetch profile details.
- Role: `USER` (by default), `ADMIN` (for admin operations).

### Books (Accessible by ADMIN and USER)
- `GET /books` — View all available books (USER/ADMIN).
- `POST /books` — Add a new book (ADMIN only).
- `PUT /books/{id}` — Update book details (ADMIN only).
- `DELETE /books/{id}` — Delete a book (ADMIN only).

### Rentals
- `POST /rent/{bookId}` — Rent a book (USER only).
  - ❗ Users **cannot rent more than 2 books** at the same time.
  - Only books that are not returned yet count towards the limit.
- `PUT /return/{rentalId}` — Return a rented book (USER only).
- `GET /rentals` — View rental history of logged-in user (USER).
- `GET /rentals/all` — View all rentals (ADMIN).

---

## ⚙️ Project Structure

```text
├── config/                 --> Spring Security & JWT Filter config
├── controller/             --> REST controllers
├── dto/                    --> DTOs for request/response mapping
├── enumAll/                --> Role enum
├── exception/              --> Global exception handler & custom exceptions
├── model/                  --> JPA Entity models (User, Book, Rental)
├── repository/             --> JPA repositories
├── service/                --> Business logic for auth, jwtService, books, and rentals.
├── application.properties  --> DB properties 
````

---

## 🛡️ Security Configuration

* Custom JWT token generation and validation.
* Filter chain configured with:

  * `/auth/*` → Open to all (register/login).
  * `/books` (GET) → USER & ADMIN.
  * `/books` (POST/PUT/DELETE) → ADMIN only.
  * `/rent/*` → USER only.

Example endpoint permission:

```java
.requestMatchers(HttpMethod.GET, "/books").hasAnyRole("ADMIN", "USER")
.requestMatchers(HttpMethod.POST, "/books/**").hasRole("ADMIN")
```

---

## 🗃️ Database Schema

### User

| Field    | Type   | Description             |
| -------- | ------ | ----------------------- |
| id       | Long   | Primary Key             |
| name     | String | User full name          |
| email    | String | Used for login/username |
| password | String | Encrypted using BCrypt  |
| role     | Enum   | `USER` / `ADMIN`        |

### Book

| Field     | Type    | Description            |
| --------- | ------- | ---------------------- |
| id        | Long    | Primary Key            |
| title     | String  | Book title             |
| author    | String  | Book author            |
| isbn      | String  | ISBN number            |
| available | Boolean | Book available to rent |

### Rental

| Field      | Type    | Description                        |
| ---------- | ------- | ---------------------------------- |
| id         | Long    | Primary Key                        |
| user       | User    | Foreign key to User                |
| book       | Book    | Foreign key to Book                |
| rentedDate | Date    | When the book was rented           |
| returned   | Boolean | Whether the book has been returned |

---

## 🔑 Authentication

Use the `/auth/login` endpoint to get a JWT token:

```json
POST /auth/login
{
  "email": "user@example.com",
  "password": "yourPassword"
}
```

Then add the token in headers:

```http
Authorization: Bearer <your-token-here>
```

---

## 🚫 Business Logic Constraints

* A user can **hold a maximum of 2 books** at a time.
* User must **return a book** before renting a third.
* Rental logic checks `returned == false` to determine active rentals.

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/book-rental-system.git
cd book-rental-system
```

### 2. Configure MySQL

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookrental
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the App

```bash
./mvnw spring-boot:run
```

---

## 🧪 Sample Test Users

### Admin User

```json
{
  "email": "admin@example.com",
  "password": "admin123",
  "role": "ADMIN"
}
```

### Normal User

```json
{
  "email": "user@example.com",
  "password": "user123",
  "role": "USER"
}
```

---

## 🤝 Contributions

Contributions are welcome! You can:

* Create issues
* Suggest improvements
* Submit PRs

---

## 📄 License

This project is open-source under the [MIT License](LICENSE).

---

## 👨‍💻 Author

**Bimlesh Kumar**

* Full Stack Developer | Accenture
* [LinkedIn](https://www.linkedin.com/in/bimlesh-kumar/)

```

---

Let me know if you want to generate this as a downloadable `README.md` file or include Swagger/OpenAPI documentation in the project.
```
