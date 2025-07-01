# RAG Pipeline Backend

## Overview
This project implements a Retrieval-Augmented Generation (RAG) pipeline using Java, Spring Boot, Elasticsearch, and Azure OpenAI. The application allows users to upload PDF documents and query them using natural language prompts. The backend processes the documents, indexes them, and retrieves relevant information based on user queries.

## Project Structure
The project follows a structured approach with the following key components:

- **src/main/java/com/example/rag**: Contains the main application code.
  - **Application.java**: Entry point of the Spring Boot application.
  - **config**: Configuration classes for Elasticsearch, Azure OpenAI, and Spring AI.
  - **controller**: REST controllers for handling document-related requests.
  - **service**: Services for processing documents, generating embeddings, and querying.
  - **repository**: Repository interface for interacting with Elasticsearch.
  - **model**: Data models representing documents and query requests.
  - **util**: Utility methods

- **src/main/resources**: Contains configuration files for the application.
  - **application.yml**: Main configuration settings.
  - **application-local.yml**: Local-specific configuration settings.

- **pom.xml**: Maven configuration file for managing dependencies and build settings.

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven
- Elasticsearch instance
- Azure OpenAI account

### Setup Instructions
1. **Clone the repository**:
   ```
   git clone <repository-url>
   cd rag-pipeline-backend
   ```

2. **Configure application properties**:
   - Update `src/main/resources/application.yml` with your Elasticsearch and Azure OpenAI configurations.

3. **Build the project**:
   ```
   mvn clean install
   ```

4. **Run the application**:
   ```
   mvn spring-boot:run
   ```

### Usage
- Use the REST API endpoints defined in `DocumentController.java` to upload PDF documents and query them.
- Ensure that the documents are properly indexed for effective querying.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.