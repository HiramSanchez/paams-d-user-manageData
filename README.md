# **User Data Manager (paams-d-user-manageData)**

[![Java](https://img.shields.io/badge/Java-21-skyblue)](https://www.oracle.com/java/)
 &emsp;
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-lightgreen)](https://spring.io/projects/spring-boot)
 &emsp;
[![Maven](https://img.shields.io/badge/Maven-3.9.9-gold)](https://maven.apache.org/)


## **Description**
The **paams-d-user-manageData** microservice is responsible for managing user registration data. It provides a RESTful API for creating, reading, updating, and deleting user records in the database. Designed for scalability and easy integration into a microservices architecture, this service manages multiple databases.


## **Features**
- **Create User Data**: Register new users and create records in the database.
- **Update User Data**: Modify existing user registration data.
- **Retrieve User Data**: Fetch user records from the database.
- **Delete User Data**: Permanently delete user registration records. 


## **API Endpoints**

- **POST - /api/user/new/data**  
  `user-new-data`: Responsible for creating new user records in the database.
  
- **PUT - /api/user/data/update**  
  `user-data-update`: Updates existing user records in the database.
  
- **GET - /api/user/data/read**  
  `user-data-read`: Retrieves user registration data from the database.
  
- **DELETE - /api/user/drop/data**  
  `user-drop-data`: Permanently deletes user records from the database.

## **API Documentation**
This project includes API documentation using swagger. Access the documentation by running it and navigating to:
```bash
http://localhost:8080/swagger-ui/index.html
```

## **Requirements**
Before running this project, ensure the following dependencies are installed:


- **Java 21**: [Download Java](https://www.oracle.com/java/technologies/downloads/#java21l)
- **Maven 3.9.9**: [Download Maven](https://maven.apache.org/download.cgi)
- **MongoDB**: [Download MongoDB](https://www.mongodb.com/try/download/community)


## **Installation**

1. Clone this repository:
    ```bash
    git clone https://github.com/HiramSanchez/paams-d-user-manageData.git
    cd paams-d-user-manageData
    ```

2. Configure the `application.properties` file with your database credentials:
    ```properties
    spring.datasource.url=jdbc:mongodb://localhost:27017/your_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

3. Build and run the application using Maven:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```  

## **Contact**
For any questions or suggestions, feel free to contact me at:  
hiramsanchez.dev@gmail.com  
