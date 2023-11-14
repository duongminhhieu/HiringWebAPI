
HiringWebAPI is a simple Spring Boot application for managing a list of candidates and related recruitment information. 

## Website: https://jorehcmus.netlify.app/
## Installation

To set up the project on your local machine, you will need Java JDK and Apache Maven installed. Then, follow these steps:

1. Clone the project from GitHub: git clone https://github.com/SETQT/HiringWebAPI/
2. Navigate to the project directory: cd HiringAPI
3. Run the application with the following command: mvn spring-boot:run

The application will run at `http://localhost:8080`.

## Usage

This project provides a simple REST API for managing candidate information, employer activities. You can use HTTP requests to interact with the API, for example:

- Get a list of all candidates: `GET /candidate/getAll`
- Get candidate information: `GET /candidate/myInfo`
- Add a new job posting as an employer: `POST /employer/addJobPosting`
- Get a list of all companies: `GET /company/getAll`
- Search for job listings by text and address: `GET /job/search?text={search-text}&address={search-address}`
- Submit a CV for a candidate with ID 1: `POST /candidate/submitCV/1`


## Project Structure

The project is organized into various packages, each serving a specific purpose:

- `controller`: Contains the API endpoints and request handling.
- `model`: Defines the data models or entities used in the application.
- `repository`: Manages data access and database interactions.
- `service`: Implements business logic and orchestrates the operations.
- `security`: Handles security-related configurations and components.
- `DTO`: Houses Data Transfer Objects (DTOs) for data exchange between layers.
- `Configs`: Holds configuration classes for various components.
- `Threads`: Includes classes related to multithreading and background tasks.
- `NotificationSSE`: Contains classes for Server-Sent Events (SSE) to enable real-time notifications.








