# URL Shortener

A simple URL shortening service built with Java and Spring Boot. This application allows users to shorten long URLs and redirect to the original URL when the shortened URL is accessed.

## Features

- URL shortening via POST request
- Redirection via shortened hash
- MongoDB storage
- Unique hash generation with collision handling

🛠️ Tech Stack

- Spring Boot 3

- MongoDB (Database)

- SHA-256 (Short hash generation)

- Gradle

📦 Key Dependencies
- spring-boot-starter-web

- spring-boot-starter-data-mongodb

- spring-dotenv (For environment variables)



## Getting Started

### Prerequisites

- Java 21
- Gradle
- MongoDB (local or online instance)

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Saularch97/url-shortener.git
   cd url-shortener
   ```

2. **Configure MongoDB:**

    - **Local MongoDB:** Ensure MongoDB is installed and running on `mongodb://localhost:27017`.
    - **Online MongoDB (e.g., MongoDB Atlas):** Obtain your connection string, such as `mongodb+srv://<username>:<password>@cluster0.mongodb.net/<dbname>?retryWrites=true&w=majority`.

   Update the `application.properties` file with your MongoDB connection details:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/urlshortener
   ```

   Replace the URI with your actual connection string if using an online instance.

3. **Build the project:**
   ```bash
   ./gradlew build
   ```

4. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```

   The application will start on `http://localhost:8080`.


🧪  **Run unit tests**

   ```bash
   ./gradlew bootRun
   ```

## API Endpoints

### Shorten a URL

- **Endpoint:** `POST /shorten`
- **Request Body:**
  ```json
  {
    "url": "https://www.example.com/very-long-url/38912381239j21392j193j138j23912391dj1cj1ncd19wjncd91cn9dcj1wncj19j"
  }
  ```
- **Response:**
  ```json
  {
    "shortCode": "http://localhost:8080/xxx.com/INFRl"
  }
  ```

### Redirect to Original URL

- **Endpoint:** `GET /{shortCode}`
- **Behavior:** Redirects to the original long URL associated with the provided short code.
