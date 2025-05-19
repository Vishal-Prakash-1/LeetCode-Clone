# LeetCode Clone

A coding practice platform inspired by LeetCode. This application enables users to solve programming problems in an interactive and scalable environment using Docker, Kafka, and a microservice architecture.

---

## 🛠 Tech Stack

- **Frontend**: Next.js (React framework)
- **Backend Services**:
  - `executor-service` – Code execution engine
  - `api-service` – RESTful APIs for problem management and submissions
- **Messaging Queue**: Apache Kafka
- **Database**: PostgreSQL
- **Containerization**: Docker

---

## 🚀 How to Run the Application

### 1. Start Docker Engine

Ensure Docker Desktop is running. Then, build and run:

```bash
docker build -f Dockerfile.java .
docker build -f Dockerfile.python .
```

### 2. Start Kafka and Zookeeper

Navigate to your Kafka installation folder and run:

```bash
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

Then, in a new terminal:

```bash
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

### 3. Start PostgreSQL

Ensure PostgreSQL (pgAdmin) is running with the appropriate database set up.

### 4. Run Backend Services

Navigate to each service and run the main Java file (`App.java`):

```bash
cd executor-service
# Run App.java

cd ../api-service
# Run App.java
```

### 5. Run Frontend (UI)

Navigate to the `ui` folder and run:

```bash
npm install
npm run dev
```

---

## 🧪 Folder Structure

```
LeetCode-Clone/
│
├── api-service/           # API microservice
├── executor-service/      # Code execution microservice
├── ui/                    # React frontend
├── Dockerfile.java        # Dockerfile for Java execution environment
├── Dockerfile.python      # Dockerfile for Python execution environment
└── README.md              # Project documentation
```

---

## 📌 Notes

- Make sure Kafka and Zookeeper are properly configured for Windows.
- PostgreSQL must be running before launching backend services.
- Update environment variables or database credentials as required in each service config.



