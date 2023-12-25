# Chatop

### Introduction

Project Chatop is an open source platform that allow potential tenants to contact the owners of the various properties they wish to rent.

### Project Support Features

- Users can signup and login to their accounts
- Public (non-authenticated) users can register
- Authenticated users can access all causes.

### Installation Guide

- Clone this repository (https://github.com/Sirinemn/P3-Full-Stack-portail-locataire) Front-end with Angular.
- Run npm install to install all dependencies
- Clone this repository (https://github.com/Sirinemn/ChaTop) Back-end with SpringBoot.
- Run mvn spring-boot:run to lunch the project.
- Swagger URL : http://localhost:8080/swagger-ui/index.html/
- SQL script for creating the schema is available ressources/sql/script.sql

### Requirements

- Maven 3.
- JDK 17.

### Usage

- Connect to the API on port 8080.

### API Endpoints

| HTTP Verbs | Endpoints               | Action                                  |
| ---------- | ----------------------- | --------------------------------------- |
| POST       | /auth/register          | To sign up a new user account           |
| POST       | /auth/login             | To login an existing user account       |
| POST       | /api/message            | To create a new message                 |
| POST       | /api/rentals/add        | To create a new rental                  |
| GET        | /api/rentals            | To retrieve all rentals on the platform |
| GET        | /api/rentals/:id        | To retrieve details of a single rental  |
| GET        | /api/auth/user/:id      | To retrive the details of a single user |
| PUT        | /api/rentals/update/:id | To update a single rental               |

### Technologies Used

This project is created with :

- Spring Tool Suite 4.
- Angular 14.

### License

This project is available for use under the Apache 2.0 License.
