# 💼 Job Portal API

A robust RESTful API for a job portal application, facilitating seamless interactions between employers and job seekers. Built with Java and Spring Boot, this API manages job postings, applications, and user authentication efficiently.

## 🚀 Features

- *User Roles*: Supports multiple user roles such as Admin, Employer, and Job Seeker.
- *Job Management*: Employers can create, update, and delete job postings.
- *Application Handling*: Job seekers can apply to jobs, and employers can manage applications.
- *Authentication*: Secure user authentication and authorization mechanisms.
- *Search & Filter*: Advanced job search and filtering capabilities.
- *Pagination*: Efficient data retrieval with pagination support.
- *Error Handling*: Comprehensive error responses for better client-side handling.

## 🛠 Tech Stack

- *Backend*: Java, Spring Boot
- *Database*: MySQL (Dockerized MySQL Database)
- *Security*: Spring Security, JWT (JSON Web Tokens)
- *Build Tool*: Maven
- *API Documentation*: Swagger/OpenAPI

## 📦 Installation

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker (MySQL) installed and running

### Steps

1. *Clone the repository*:

   bash
   ```sh
   git clone https://github.com/ShrunalNimje/Job_Portal_API.git
   cd Job_Portal_API
   ```
   

3. *Configure the database*:

   Update the application.properties file with your database credentials:

   properties
   ```sh
   spring.datasource.url=jdbc:mysql://localhost:3306/job_portal
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

   
5. *Build the project*:

   bash
   ```sh
   mvn clean install
   ```


7. *Run the application*:

   bash
   ```sh
   mvn spring-boot:run
   ```
   

   The API will be accessible at
   
   ```sh
   http://localhost:8080.
   ```


## 📖 API Documentation

Access the Swagger UI for interactive API documentation:


```sh
http://localhost:8080/swagger-ui.html
```


## 📨 API Endpoints

### 🔐 Authentication
| Method | Endpoint              | Description                 |
|--------|-----------------------|-----------------------------|
| POST   | `/register/user/`     | Register new user           |
| POST   | `/login/`             | Authenticate user and return JWT |

---

### 👤 User
| Method | Endpoint               | Description              |
|--------|------------------------|--------------------------|
| GET    | `/user/profile/`   | Get current user profile |
| PUT    | `/user/profile/update/`   | Update user profile      |
| PUT    | `/user/update/{id}/`   | Update user by unique id      |
| GET    | `/users/`   | Get all users      |
| GET    | `/user/{id}/`   | Get an user by unique id      |
| DELETE    | `/users/delete/`   | Delete all users      |
| DELETE    | `/user/delete/{id}`   | Delete an user by unique id      |

---

### 💼 Jobs (Employer Role)
| Method | Endpoint                 | Description                    |
|--------|--------------------------|--------------------------------|
| POST   | `/job/`              | Create a new job post          |
| PUT    | `/job/update/{id}/`         | Update an existing job post    |
| DELETE | `/job/delete/{id}/`         | Delete a job post              |
| DELETE    | `/jobs/delete/`     | Delete all jobs |
| GET    | `/jobs/`     | Get all jobs posted by employer |
| GET    | `/jobs/{id}`     | Get an job by unique id posted by employer |

---

### 📋 Job Listings (Public/Job Seeker)
| Method | Endpoint                 | Description                             |
|--------|--------------------------|-----------------------------------------|
| GET    | `/jobs/`     | Get all jobs posted by employer |
| GET    | `/jobs/{id}`     | Get an job by unique id posted by employer |

---

### 📄 Applications (Job Seeker Role)
| Method | Endpoint                       | Description                      |
|--------|--------------------------------|----------------------------------|
| POST   | `/application/apply/`    | Apply to a job                   |
| GET    | `/application/{id}`       | Get an application     |
| GET    | `/applications/user/`       | Get an applications for specific user     |
| GET    | `/applications/`       | Get all application     |

---

### 📥 Applications (Employer Role)
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| PUT    | `/api/application/status/{id}`   | Update application status for a job posted by employer |
| GET    | `/application/{id}`       | Get an application     |
| GET    | `/applications/job/{jobId}`       | Get all applications for job posted by employer     |

---

### 📥 Applications (Admin Role)
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| DELETE    | `/application/delete/{id}/`       | Delete an application by unique id    |
| DELETE    | `/applications/delete/`       | Delete all applications      |

---

## 🔐 Authentication & Authorization

- *JWT Authentication*: Secure endpoints using JSON Web Tokens.
- *Role-Based Access Control*: Different access levels for Admins, Employers, and Job Seekers.

## 🧪 Testing

- *Unit Tests*:  
  Unit tests are written using *JUnit 5* and *Mockito* to test service layer logic in isolation.  
  You can run all tests using the following Maven command:

## 💡 Author
Developed by *[Shrunal Nimje](https://github.com/ShrunalNimje)*

---

🔥 *Feel free to fork and contribute!* 🚀
