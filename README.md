# Blockbuster

Blockbuster is a RESTful web service for managing property bookings and blocks. It provides endpoints to create, update, and delete bookings and blocks. This project is implemented in Java using the Spring Boot framework.

## Technologies Used
- Java 11+
- Spring Boot
- In-memory volatile database (H2)

## Getting Started
Follow the steps below to set up and run the Blockbuster project locally.

### Prerequisites
- Java 11+ installed
- Maven installed

### Installation
1. Clone this GitHub repository:
   ```shell
   git clone https://github.com/thimico/blockbuster.git
   ```

2. Navigate to the project directory:
   ```shell
   cd blockbuster
   ```

3. Build the project using Maven:
   ```shell
   mvn clean install
   ```

4. Run the application:
   ```shell
   java -jar target/blockbuster-0.0.1-SNAPSHOT.jar
   ```

The Blockbuster service should now be running locally.

## API Endpoints

### Block Controller

#### Create Block
- **Endpoint**: `POST /blocks`
- **Description**: Creates a new block for a property.
- **Example Request**:
  ```http
  POST /blocks
  Content-Type: application/json
  
  {
    "startDate": "2023-10-10",
    "endDate": "2023-10-15"
  }
  ```
- **Example Response** (HTTP 201 Created):
  ```json
  {
    "id": "1",
    "startDate": "2023-10-10",
    "endDate": "2023-10-15"
  }
  ```

#### Delete Block
- **Endpoint**: `DELETE /blocks/{blockId}`
- **Description**: Deletes the block with the specified `blockId`.
- **Example Request**:
  ```http
  DELETE /blocks/1
  ```
- **Example Response** (HTTP 204 No Content): No response body.

#### Update Block
- **Endpoint**: `PUT /blocks/{blockId}`
- **Description**: Updates the block with the specified `blockId`.
- **Example Request**:
  ```http
  PUT /blocks/1
  Content-Type: application/json
  
  {
    "startDate": "2023-10-11",
    "endDate": "2023-10-16"
  }
  ```
- **Example Response** (HTTP 200 OK):
  ```json
  {
    "id": "1",
    "startDate": "2023-10-11",
    "endDate": "2023-10-16"
  }
  ```

### Booking Controller

#### Create Booking
- **Endpoint**: `POST /bookings`
- **Description**: Creates a new booking for a property.
- **Example Request**:
  ```http
  POST /bookings
  Content-Type: application/json
  
  {
    "startDate": "2023-11-01",
    "endDate": "2023-11-05",
    "guest": {
      "name": "John Doe",
      "contactInfo": "john.doe@example.com"
    },
    "property": {
      "id": "123",
      "name": "Luxury Villa",
      "location": "Paradise Beach"
    }
  }
  ```
- **Example Response** (HTTP 201 Created):
  ```json
  {
    "id": "1",
    "startDate": "2023-11-01",
    "endDate": "2023-11-05",
    "guest": {
      "name": "John Doe",
      "contactInfo": "john.doe@example.com"
    },
    "property": {
      "id": "123",
      "name": "Luxury Villa",
      "location": "Paradise Beach"
    }
  }
  ```

#### Delete Booking
- **Endpoint**: `DELETE /bookings/{bookingId}`
- **Description**: Deletes the booking with the specified `bookingId`.
- **Example Request**:
  ```http
  DELETE /bookings/1
  ```
- **Example Response** (HTTP 204 No Content): No response body.

#### Update Booking
- **Endpoint**: `PUT /bookings/{bookingId}`
- **Description**: Updates the booking with the specified `bookingId`.
- **Example Request**:
  ```http
  PUT /bookings/1
  Content-Type: application/json
  
  {
    "startDate": "2023-11-02",
    "endDate": "2023-11-06",
    "guest": {
      "name": "Jane Smith",
      "contactInfo": "jane.smith@example.com"
    },
    "property": {
      "id": "123",
      "name": "Luxury Villa",
      "location": "Paradise Beach"
    }
  }
  ```
- **Example Response** (HTTP 200 OK):
  ```json
  {
    "id": "1",
    "startDate": "2023-11-02",
    "endDate": "2023-11-06",
    "guest": {
      "name": "Jane Smith",
      "contactInfo": "jane.smith@example.com"
    },
    "property": {
      "id": "123",
      "name": "Luxury Villa",
      "location": "Paradise Beach"
    }
  }
  ```

These are the RESTful endpoints for managing blocks and bookings in the Blockbuster project. You can use tools like `curl` or API testing tools like Postman or Insomnia to make requests to these endpoints and interact with the application.

## Author
- Thiago Oliveira

## License
This project is licensed under the MIT License