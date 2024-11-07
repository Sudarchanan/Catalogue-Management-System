# Catalogue Management System

> **Note:** The source code for this project is located in the **master branch**.

The **Catalogue Management System** is a secure, role-based full-stack application designed to manage and organize catalogues with a responsive interface and a robust backend. The project is built with **React.js** on the frontend, **Spring Boot** with **JWT-based security authentication** on the backend, and **MySQL** as the data storage.

### Features
- **User-friendly Interface**: Built with React.js, providing a seamless and responsive user experience.
- **3-Tier Architecture**: The backend follows a 3-tier architecture with **Model**, **DAO (Data Access Object)**, and **Service** layers, ensuring modularity and efficient data handling.
- **Secure JWT Authentication**: Secured using **JWT (JSON Web Token)** authentication and **HttpSecurity**, providing secure and stateless authorization.
- **Spring Controllers with Swagger**: APIs are managed using Spring Controllers with Swagger configuration for easy API documentation and testing.
- **Role-based Access Control**: Supports three user roles—Admin, Manager, and Customer—each with specific permissions and access controls.

### Role-Based Workflow
1. **Customer**: Browses the catalogue and creates quotations for internet products.
2. **Manager**: Reviews and approves the quotations made by customers.
3. **Admin**: Has full access to manage catalogues, roles, and application settings.

### Tech Stack
- **Frontend**: React.js
- **Backend**: Spring Boot (Java) with JWT-based Security Authentication and Swagger for API documentation
- **Database**: MySQL

This application structure ensures a smooth, secure, and organized process from customer quotations to manager approvals, with robust role-based security and efficient catalogue management.
