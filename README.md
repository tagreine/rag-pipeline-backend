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
  - **application-dev.yml**: Local-specific configuration settings.

- **pom.xml**: Maven configuration file for managing dependencies and build settings.

## Infrastructure (Docker Compose)

This project provides a `docker-compose.yml` for local development infrastructure:

- **Elasticsearch**: Full-text search and vector storage.
- **Dejavu**: Web UI for browsing and managing Elasticsearch data.
- **Azurite**: Local emulator for Azure Blob Storage.

### Starting the Infrastructure

1. **Start all services:**
   ```bash
   docker-compose up -d
   ```

2. **Elasticsearch**
   - Runs on [http://localhost:9200](http://localhost:9200)
   - No authentication (security disabled for local use)

3. **Dejavu (Elasticsearch UI)**
   - Access at [http://localhost:1358](http://localhost:1358)
   - Use this to browse, search, and manage your Elasticsearch indices and documents.
   - **Connect to Elasticsearch:**  
     - URL: `http://elasticsearch:9200` (from inside Docker)  
     - Or: `http://localhost:9200` (from your browser)

4. **Azurite (Azure Blob Storage Emulator)**
   - Blob service runs on `http://localhost:10000`
   - Use [Azure Storage Explorer](https://azure.microsoft.com/en-us/products/storage/storage-explorer/) to browse and manage blobs:
     - Add a new connection in Storage Explorer:
       - Select "Attach to a local emulator"
       - Azurite will appear as a local storage account

### Stopping the Infrastructure

```bash
docker-compose down
```

### Notes

- Ensure Docker is running before starting the services.
- Data for Elasticsearch and Azurite is persisted in Docker volumes (`elasticsearch-data`, `azurite-data`).

## Environment Variable Management

This project uses environment variables for sensitive configuration (such as API keys and connection strings) to keep secrets out of version control and make local development easier.

### Recommended Setup for Local Development

1. **Create a `.env` file in the project root**  
   Add your secrets and environment variables, for example:
   ```
   AZURERITE_ACCOUNT_KEY=your_azurite_key
   AZURE_OPENAI_KEY=your_openai_key
   AZURE_OPENAI_ENDPOINT=https://YOUR_RESOURCE_NAME.openai.azure.com/
   AZURE_OPENAI_EMBEDDING_DEPLOYMENT=YOUR_DEPLOYMENT_NAME
   ```

2. **Create a `.envrc` file in the project root**  
   Add the following line:
   ```
   dotenv
   ```
   This tells [direnv](https://direnv.net/) to load variables from `.env` automatically.

3. **Install and enable [direnv](https://direnv.net/):**
   - Install:  
     ```bash
     sudo apt install direnv
     ```
   - Hook into your shell by adding to your `~/.bashrc` or `~/.zshrc`:
     ```bash
     eval "$(direnv hook bash)"   # or zsh
     ```
   - Reload your shell:
     ```bash
     source ~/.bashrc
     # or
     source ~/.zshrc
     ```
   - Allow direnv in your project directory:
     ```bash
     direnv allow
     ```

4. **Verify your environment variables are loaded:**
   ```bash
   echo $AZURERITE_ACCOUNT_KEY
   ```

### Why use this approach?

- **Secrets are not committed to git**: `.env`, `.envrc`, and other secret files are listed in `.gitignore`.
- **Easy onboarding**: New developers can copy `.env.example` (if provided) and add their own secrets.
- **Automatic loading**: With direnv, variables are set automatically when you enter the project directory.

### For Production/Deployment

- Use environment variables, Kubernetes Secrets, or a secrets manager (like Azure Key Vault) to provide sensitive configuration at runtime.
- Never commit real secrets to the repository.

## Configuration

Spring's `@Value` annotation (e.g. `@Value("${azure.storage.account-name}")`) reads values from your configuration files:

- `src/main/resources/application.yml` or `application.properties`
- Or from environment variables (if referenced with `${VARNAME}`)

For example, in your `application.yml` or `application-dev.yml`:
```yaml
azure:
  storage:
    account-name: devstoreaccount1
    account-key: ${AZURERITE_ACCOUNT_KEY}
    endpoint: http://127.0.0.1:10000/devstoreaccount1
```
Spring will inject these values into the beans at runtime.

---

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven
- Docker (for infrastructure)
- Elasticsearch instance (provided via Docker Compose)
- Azure OpenAI account

### Setup Instructions
1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd rag-pipeline-backend
   ```

2. **Configure application properties**:
   - Update `src/main/resources/application.yml` with your Elasticsearch and Azure OpenAI configurations.

3. **Build the project**:
   ```bash
   mvn clean install
   ```

4. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

### Usage
- Use the REST API endpoints defined in `DocumentController.java` to upload PDF documents and query them.
- Ensure that the documents are properly indexed for effective querying.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.