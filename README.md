# MovieMatch

## Overview

MovieMatch is a full-stack web application designed to help users find, rate, and manage their movie preferences. Built using Spring Boot, this application allows users to search for movies, add them to their watchlist, rate watched movies, and read reviews from others.

## Features

- **User Authentication**: Sign up and login functionality with hashed passwords using bcrypt.
- **Movie Search**: Search for movies using the TMDB API.
- **Watchlist Management**: Add movies to your watchlist and mark them as watched.
- **Rating and Reviews**: Rate watched movies and read/write reviews.
- **Recommendations**: Get movie recommendations based on your watchlist.
- **Responsive Design**: User-friendly interface with responsive design using CSS and Thymeleaf.

## Project Structure

moviematch
├── src
│ ├── main
│ │ ├── java
│ │ │ └── com
│ │ │ └── krispanko
│ │ │ └── moviematch
│ │ │ ├── config
│ │ │ │ └── WebConfig.java
│ │ │ ├── controller
│ │ │ │ ├── ApiController.java
│ │ │ │ ├── AuthController.java
│ │ │ │ ├── GlobalExceptionHandler.java
│ │ │ │ ├── PageController.java
│ │ │ │ ├── ReviewController.java
│ │ │ │ ├── StaticResourceController.java
│ │ │ │ ├── WatchedMovieController.java
│ │ │ │ └── WatchlistController.java
│ │ │ ├── model
│ │ │ │ ├── Review.java
│ │ │ │ ├── User.java
│ │ │ │ ├── WatchedMovie.java
│ │ │ │ └── Watchlist.java
│ │ │ ├── repository
│ │ │ │ ├── ReviewRepository.java
│ │ │ │ ├── UserRepository.java
│ │ │ │ ├── WatchedMovieRepository.java
│ │ │ │ └── WatchlistRepository.java
│ │ │ ├── security
│ │ │ │ ├── CustomUserDetailsService.java
│ │ │ │ └── SecurityConfig.java
│ │ │ ├── service
│ │ │ │ ├── ReviewService.java
│ │ │ │ ├── UserService.java
│ │ │ │ ├── UserServiceImpl.java
│ │ │ │ └── WatchedMovieService.java
│ │ │ └── MovieMatchApplication.java
│ │ ├── resources
│ │ │ ├── static
│ │ │ │ ├── style.css
│ │ │ │ └── script.js
│ │ │ ├── templates
│ │ │ │ ├── details.html
│ │ │ │ ├── index.html
│ │ │ │ ├── login.html
│ │ │ │ ├── recommendations.html
│ │ │ │ ├── signup.html
│ │ │ │ └── watchlist.html
│ │ │ └── application.properties
│ └── test
│ └── java
│ └── com
│ └── krispanko
│ └── moviematch
│ ├── controller
│ └── service
└── pom.xml

bash
Copy code

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/moviematch.git
   cd moviematch
Set up the database:

# Ensure MySQL is installed and running.
Create a database named moviematch.
Update the application.properties file with your MySQL username and password.
Build and run the application:

bash

mvn clean install
mvn spring-boot:run

# Usage
Home Page: Search for movies using the search bar.
Sign Up / Login: Create an account or log in to access personalized features.
Watchlist: Add movies to your watchlist and mark them as watched.
Recommendations: Get recommendations based on your watchlist.
Reviews: Read and write reviews for movies.

# Configuration
application.properties

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/moviematch
spring.datasource.username=yourusername
spring.datasource.password=yourpass
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Thymeleaf Configuration
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Static Resource Configuration
spring.mvc.static-path-pattern=/static/**
spring.web.resources.static-locations=classpath:/static/

# Server Configuration
server.error.whitelabel.enabled=false
server.error.include-message=always


# TMDB API Key
GET API KEY FROM TMDB

## API Endpoints

# User Authentication
Sign Up: /signup (POST)
Login: /login (POST)
# Movies
Search Movies: /api/movies/search (GET)
Movie Details: /api/movies/{id} (GET)
# Watchlist
Get Watchlist: /api/watchlist (GET)
Add to Watchlist: /api/watchlist (POST)
Remove from Watchlist: /api/watchlist/{id} (DELETE)
# Watched Movies
Get Watched Movies: /api/watched (GET)
Add to Watched Movies: /api/watched (POST)
Remove from Watched Movies: /api/watched/{id} (DELETE)
Rate Watched Movie: /api/watched/rate/{movieId} (POST)
# Reviews
Get Reviews: /api/reviews (GET)
Add Review: /api/reviews (POST)

# Technologies Used
Backend: Spring Boot, Spring Data JPA, Spring Security
Frontend: Thymeleaf, CSS, JavaScript
Database: MySQL
Build Tool: Maven

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgements
The Movie Database (TMDB) API for movie data.
Spring Boot for providing a powerful and easy-to-use framework.
All the instructors and peers who provided guidance and support throughout the project.
Author
Kris Panko
