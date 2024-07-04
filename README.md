# BookMyShow_Spring

## Project Description

BookMyShow_Spring is a Spring Boot application designed to manage the backend operations for a movie booking platform. The application integrates with Swagger UI for API documentation and testing, and includes email functionality for notifications and confirmations. MySQL is used as the database, with JPA and Hibernate handling data persistence.

## Features

- Movie and show management
- Booking management
- Integration with Swagger UI for API documentation
- Email notifications for booking confirmations
- Data persistence using JPA and Hibernate
- MySQL as the database

## Technologies Used

- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Swagger UI
- JavaMailSender (for email integration)

## Prerequisites

Make sure you have the following installed:

- Java 11 or higher
- Maven
- MySQL
- Postman (for testing API endpoints easily)

## Getting Started

### Clone the Repository

```
git clone https://github.com/your-username/bookMyShowBackend.git
cd bookMyShow_Spring
```

## Configure the Database

1. **Create a MySQL database named cinemaDb.**

2. **Update the `application.properties` file located in `src/main/resources` with your MySQL database configuration:**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cinemaDb?createTableIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
## Configure Email Properties

Add your email configuration in the `application.properties` file:

```properties
spring.mail.host=smtp.your-email-provider.com 
spring.mail.port=587
spring.mail.username=your_email@example.com
spring.mail.password=your_email_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## Build and Run the Application
```
mvn clean install
mvn spring-boot:run
```

## Access Swagger UI
Once the application is running, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

## API Endpoints
The API endpoints are documented and can be tested using the Swagger UI interface. Some key endpoints include:

## User Controller

### Update User
**PUT** `/user/update`

- **Description**: Updates user information.
- **Request Body**: 
  - `UpdateUserRequest` object
- **Response**: Success or failure message.

### Send Code for Update
**POST** `/user/update/send-code`

- **Description**: Sends a verification code for updating user information.
- **Request Body**: 
  - User details
- **Response**: Success or failure message.

### Send Code for Deletion
**POST** `/user/delete/send-code`

- **Description**: Sends a verification code for user deletion.
- **Request Body**: 
  - User details
- **Response**: Success or failure message.

### Add User
**POST** `/user/add`

- **Description**: Adds a new user.
- **Request Body**: 
  - `AddUserRequest` object
- **Response**: Success or failure message.

### Delete User
**DELETE** `/user/delete`

- **Description**: Deletes a user.
- **Request Parameters**: 
  - `userID` (String)
- **Response**: Success or failure message.

## Theatre Controller

### Update Theatre
**PUT** `/theatre/update`

- **Description**: Updates theatre information.
- **Request Body**: 
  - `UpdateTheatreRequest` object
- **Response**: Success or failure message.

### Associate Seats with Theatre
**PUT** `/theatre/associate-seats`

- **Description**: Associates seats with a theatre.
- **Request Body**: 
  - `AddTheatreSeatRequest` object
- **Response**: Success or failure message.

### Add Theatre
**POST** `/theatre/add`

- **Description**: Adds a new theatre.
- **Request Body**: 
  - `AddTheatreRequest` object
- **Response**: Success or failure message.

### Get Theatre Revenues
**GET** `/theatre/revenues`

- **Description**: Retrieves theatre revenues.
- **Response**: Theatre revenue details.

### Get Movies Playing in Theatre
**GET** `/theatre/movies`

- **Description**: Retrieves movies playing in a theatre.
- **Response**: List of movies.

### Get List of Theatres
**GET** `/theatre/movies-list`

- **Description**: Retrieves a list of theatres.
- **Response**: List of theatres.

### Get Theatre Cities
**GET** `/theatre/city`

- **Description**: Retrieves cities with theatres.
- **Response**: List of cities.

### Delete Theatre
**DELETE** `/theatre/delete`

- **Description**: Deletes a theatre.
- **Request Parameters**: 
  - `theatreID` (String)
- **Response**: Success or failure message.

## Show Controller

### Update Show
**PUT** `/show/update`

- **Description**: Updates show information.
- **Request Body**: 
  - `UpdateShowRequest` object
- **Response**: Success or failure message.

### Add Show
**POST** `/show/add`

- **Description**: Adds a new show.
- **Request Body**: 
  - `AddShowRequest` object
- **Response**: Success or failure message.

### Get Total Collection for a Show
**GET** `/show/total-collection`

- **Description**: Retrieves total collection for a show.
- **Response**: Total collection amount.

### Get Theatres with Shows
**GET** `/show/theatres`

- **Description**: Retrieves theatres with shows.
- **Response**: List of theatres with shows.

### Get List of Shows
**GET** `/show/theatre-list`

- **Description**: Retrieves a list of shows.
- **Response**: List of shows.

### Get Show by ID
**GET** `/show/show-id`

- **Description**: Retrieves show details by ID.
- **Response**: Show details.

### Get Remaining Seats for a Show
**GET** `/show/seats-remaining`

- **Description**: Retrieves remaining seats for a show.
- **Response**: Number of remaining seats.

### Get List of Shows by Date, City, and Theatres
**GET** `/show/date-city-theatres-list`

- **Description**: Retrieves shows by date, city, and theatres.
- **Response**: List of shows.

### Get Collection Date for a Show
**GET** `/show/collection-date`

- **Description**: Retrieves collection date for a show.
- **Response**: Collection date.

### Get List of Shows by City and Theatres
**GET** `/show/city-theatres-list`

- **Description**: Retrieves shows by city and theatres.
- **Response**: List of shows.

### Delete Show
**DELETE** `/show/delete`

- **Description**: Deletes a show.
- **Request Parameters**: 
  - `showID` (String)
- **Response**: Success or failure message.

## Movie Controller

### Update Movie
**PUT** `/movie/update`

- **Description**: Updates movie information.
- **Request Body**: 
  - `UpdateMovieRequest` object
- **Response**: Success or failure message.

### Add Movie
**POST** `/movie/add`

- **Description**: Adds a new movie.
- **Request Body**: 
  - `AddMovieRequest` object
- **Response**: Success or failure message.

### Get Theatres Playing a Movie
**GET** `/movie/theatres`

- **Description**: Retrieves theatres playing a movie.
- **Response**: List of theatres.

### Get Movie Release Date
**GET** `/movie/date`

- **Description**: Retrieves movie release date.
- **Response**: Movie release date.

### Delete Movie
**DELETE** `/movie/delete`

- **Description**: Deletes a movie.
- **Request Parameters**: 
  - `movieID` (String)
- **Response**: Success or failure message.

## Ticket Controller

### Book Ticket
**POST** `/ticket/book-ticket`

- **Description**: Books a ticket for a show.
- **Request Body**: 
  - `BookTicketRequest` object
- **Response**: Success or failure message.

### View Ticket
**GET** `/ticket/view-ticket`

- **Description**: Views booked tickets.
- **Response**: Booked ticket details.

### Cancel Ticket
**DELETE** `/ticket/cancel-ticket`

- **Description**: Cancels a booked ticket.
- **Request Parameters**: 
  - `ticketID` (String)
- **Response**: Success or failure message.

## Email Notifications
The application sends email notifications for booking confirmations. Ensure the email configurations are correctly set in the application.properties file for this feature to work.

## Database Schema
The application uses JPA and Hibernate to manage the database schema. Tables include:

- Movie - Stores movie information
- Show - Stores show details
- ShowSeat - Stores seatType details and status
- Theater - Stores theater details
- TheaterSeat - Stores theater seats
- Ticket - Stores information of tickets
- User - Stores user information
