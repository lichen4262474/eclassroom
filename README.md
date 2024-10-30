# E-Classroom

E-Classroom is a digital learning platform designed to facilitate interaction between teachers and students. It provides an integrated environment for sharing learning resources, grading assignments, and conducting grade analysis. This application aims to enhance the educational experience for both educators and learners.

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)

## Features

### For Teachers
- **Profile Management**: Create and modify personal profiles.
- **Class Management**: Create, modify, and delete classes.
- **Announcements**: Share, modify, and delete announcements to keep students informed.
- **Lesson Management**: Share, modify, and delete lesson materials.
- **Assignment Management**: Share, modify, delete, and grade assignments.
- **Student Management**: Unenroll students from classes.
- **Communication**: Contact students and parents for important updates.
- **Grade Analysis**: Perform detailed grade analysis with dynamic graphs showcasing students’ grades and assignment statuses.

### For Students
- **Profile Management**: Create and modify personal profiles.
- **Class Management**: Join and drop classes as needed.
- **Resource Access**: Check and access shared learning resources.
- **Assignment Submission**: Check and submit assignments.
- **Communication**: Contact teachers for support and queries.
- **Grade Analysis**: Perform grade analysis with dynamic graphs reflecting students’ grades and assignment statuses.

## Technology Stack

- **Frontend**: HTML5, CSS3, JavaScript, Bootstrap 5
- **Database**: MySQL
- **Backend**: Java, Spring Boot, Spring JPA, Spring Security
- **Template Engine**: Thymeleaf

## Installation

To set up the E-Classroom application locally, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd e-classroom
## Setup Instructions

### Set Up the Database

## Installation

To set up the E-Classroom application locally, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd e-classroom

2. **Set Up the Database**:

Create a MySQL database and take note of the database name, username, and password.
Configure Application Properties:

Open the src/main/resources/application.properties file.
Update the following properties with your database details:
properties

Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password

3. **Build the Application**:
   - Make sure you have Maven installed. Run:
     ```bash
     mvn clean install
     ```

4. **To Start the Application**:
   - Run:
     ```bash
     mvn spring-boot:run
     ```

5. **Access the Application**:
   - Open your web browser and go to `http://localhost:8080`.


### Usage

- **Teachers**: Log in to create classes, share resources, and communicate with students and parents.
- **Students**: Log in to access learning materials, submit assignments, and communicate with teachers.
