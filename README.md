# **User Data Manager (paams-d-user-manageData)**

[![Java](https://img.shields.io/badge/Java-21-skyblue)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-lightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9.9-gold)](https://maven.apache.org/)


## **Description**
This **paams-d-user-manageData** microservice provides a RESTful API for managing user registrations with CRUD functionality (Create, Read, Update, Delete). It supports connections to multiple databases and is designed to be scalable and easily integrated into a microservices architecture.  


## **Features**
- **CRUD API**: Enables creation, retrieval, updating, and deletion of user records.
- **MVC Architecture**: Follows a layered architecture with separation of concerns (controller, service, repository, model).
- **Multi-DB Support**: Connects to multiple databases based on request type.  


## **Requirements**
Before running this project, ensure the following dependencies are installed:


- **Java 21**: [Download Java](https://www.oracle.com/java/technologies/downloads/#java21l)
- **Maven 3.9.9**: [Download Maven](https://maven.apache.org/download.cgi)
- **Database**: MongoDB  


## **Usage**
The microservice provides the following API endpoints:

- **POST - /api/user/new/data** - Create a new user.
- **GET - /api/user/data/read** - Retrieve user's data.
- **PUT - /api/user/data/update** - Update an existing user.
- **DELETE - /api/user/drop/data** - Delete a user.


## **Installation**

1. Clone this repository:
    ```bash
    git clone https://github.com/your-username/paams-d-user-manageData.git
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
