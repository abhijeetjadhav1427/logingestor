# Assignment
## Folder Structure
```
project-root/
│
├── logs
|
├── docker-compose.yml
│
├── logingestor
│ └── ... # Backend code
│
└── webui/
└── ... # Frontend code
```

## APIs

### /uploadlogs
- **Method:** POST
- **Endpoint:** `http://localhost:3000/uploadlogs`
- **Description:** Accepts a multipart file upload.
- `logs`: Sample log file in the following format:
    ```
    debug,Connection established,server-1234,2023-11-20T10:30:00Z,abc-xyz-123,span-456,5e5342f,{"parentResourceId":"server-0987"}
    error,Failed to connect to DB,server-5678,2023-11-20T10:35:00Z,pqr-uvw-456,span-789,5e5342f,{"parentResourceId":"server-0987"}
    warning,High CPU usage,server-9012,2023-11-20T10:40:00Z,lmn-opq-789,span-123,5e5342f,{"parentResourceId": "server-0987"}
    ```

### /logs/search

- **Method:** GET
- **Endpoint:** `http://localhost:3000/logs/search`
- **Description:** Accepts request parameters for searching logs.
- **Example:** You can make a GET request to this endpoint with appropriate request parameters to search logs.

# Usecase Diagram
![Image Description](https://drive.google.com/uc?id=1MIDqTl_X6AGEm1m04J6GpZ3dQRAbJIsn)

## Backend UML Class Diagram
![Backend Class Diagram](https://drive.google.com/uc?id=1iGG7GAarqafZ9j94gDOPu213j4TyAMOn)
The above diagram illustrates the class structure of the backend components.

## Tech Stack

- Spring Boot
- React JS
- Kafka
- Elasticsearch

## Setup

### Kafka & Elasticsearch

Ensure Docker is installed.

1. Run the docker-compose.yml file to launch Kafka, Elasticsearch, and Zookeeper.

    ```bash
    docker-compose -f docker-compose.yml up -d
    ```

    This command starts the containers in detached mode.

2. To stop the containers:

    ```bash
    docker-compose -f docker-compose.yml down
    ```
3. Verify Elasticsearch is running by visiting [http://localhost:9200/](http://localhost:9200/).
### Backend Setup

The backend code is located in the `logingestor` folder.

#### Running the Application Locally

1. Run the `com.logingestor.LogingestorApplication` class from Eclipse or Spring Tool Suite or any of your IDE.

### Frontend Setup

The UI code resides in the `webui` folder.

#### Installing Dependencies

1. Navigate to the `webui` folder in the terminal.

    ```bash
    cd webui
    ```

2. Install necessary packages.

    ```bash
    npm install
    ```

#### Running the React Application

Once dependencies are installed:

```bash
npm start
