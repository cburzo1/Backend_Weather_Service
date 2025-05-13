🌤️ Backend Weather Service
This backend weather service fetches current weather data and forecasts from OpenWeatherMap, stores them in a database, and provides endpoints to access this information.

📌 Project Overview
This application:

Retrieves current weather and forecast data for predefined cities.

Stores the data in a relational database.

Provides endpoints to access the stored weather information.

🛠 Technologies Used
Java 17

Spring Boot

Spring Data JPA

MySQL (or any other relational database)

RestTemplate (for HTTP requests)

Lombok

SLF4J (for logging)

Maven (for build and dependency management)

💻 Setup Instructions
1. Clone the Repository
bash
Copy
Edit
git clone https://github.com/cburzo1/Backend_Weather_Service.git
cd Backend_Weather_Service
2. Configure the Application
a. Add Your OpenWeatherMap API Key
Sign up at OpenWeatherMap to obtain a free API key.

Create an application.yml file in the src/main/resources directory with the following content:

yaml
Copy
Edit
openweathermap:
  api:
    key: YOUR_API_KEY_HERE
Replace YOUR_API_KEY_HERE with your actual API key.

Note: Ensure that application.yml is included in .gitignore to prevent committing sensitive information.

b. Configure Database Connection
In the same application.yml file, add your database configuration:

yaml
Copy
Edit
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database_name
    username: your_db_username
    password: your_db_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
Replace the placeholders with your actual database details.

3. Build the Project
Ensure you have Maven installed. Then, build the project:

bash
Copy
Edit
mvn clean install
🚀 Running the Application Locally
After configuring the application and building it:

bash
Copy
Edit
mvn spring-boot:run
The application will start on http://localhost:8081.

🔁 Git Workflow Tips
Clone the repository:

bash
Copy
Edit
git clone https://github.com/cburzo1/Backend_Weather_Service.git
Create a new branch for your feature or fix:

bash
Copy
Edit
git checkout -b feature/your-feature-name
Commit your changes:

bash
Copy
Edit
git add .
git commit -m "Add your message here"
Push to your branch:

bash
Copy
Edit
git push origin feature/your-feature-name
Pull the latest changes:

bash
Copy
Edit
git pull origin main
🔍 Future Enhancements
Implement unit and integration tests.

Add Swagger for API documentation.

Deploy the application to a cloud platform.

Implement user authentication and authorization.

📄 License
This project is licensed under the MIT License. See the LICENSE file for details.
