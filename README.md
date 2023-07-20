<H1>Xpress Payment Documentation</H1>

<p>Introduction
This document provides a comprehensive guide to the implementation 
of app user registration, email verification, login, logout , VTU 
functionality. These features are essential for creating a
secure and user-friendly web application for user to purchase airtime 
The implementation adheres to professional best practices and security
standards to ensure a robust and reliable user authentication syste</p>

<H1>Table of Contents</H1>
Technologies used
<br/>
Setup
<br/>
User Registration
<br/>
Email Verification
<br/>
User Login
<br/>
User Logout
<br/>
Security Configuration
<br/>


1. Technologies Used:<br/>Java, MySQL for database storage, JSON Web Tokens (JWT) for secure user authentication
Express Validator for input validation.
2. Setup
   Clone the repository and navigate to the project directory.
   Configure the database connection

3. User Registration
   A user can register on the application by providing a valid email address and a strong password. And other details
4. Email Verification
   After successful registration, a verification token is provided 
5. User Login
   Registered users can log in using their verified email and password.
   Upon successful login, a JSON Web Token (JWT) is generated and sent to the client.
   The JWT is used for subsequent authenticated requests to protected routes.
6. User Logout
   Users can log out of the application, which invalidates the JWT and requires re-authentication.
7. Security Considerations
   Passwords are never stored in plaintext, they are encoded
