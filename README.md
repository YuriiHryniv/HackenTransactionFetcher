# Blockchain Transaction Service

This project is designed to connect to an Ethereum Virtual Machine (EVM) node and fetch real-time transactions, store them in a PostgreSQL database, and provide a RESTful service to search the stored transactions. The service is built using **Spring Boot**, **Spring Data JPA**, and **PostgreSQL** with optional **Full-Text Search** support for fast querying.

## Features

1. **Connect to EVM Node**
   - The service connects to an EVM node (such as Ethereum, Arbitrum, BSC, Optimism, Polygon, zkSync) using public node [Grove](https://portal.grove.city)
   - It fetches real-time transactions from the blockchain network.
   
2. **Store Transactions in PostgreSQL**
   - The transactions are stored in a PostgreSQL database.
   
3. **Pause and Resume Importing**
   - The service can be stopped and restarted without losing transaction data.
   - After pausing, it continues fetching transactions where it left off, ensuring no gaps in the transaction range.

4. **REST API for Querying Stored Transactions**
   - A RESTful API to query transactions by parameters such as `type`, `value`, `blockHash`, `hash`, etc.
   - Supports optional Full-Text Search over transaction fields for fast and flexible search capabilities.
   
5. **Optional Features**
   - **API Documentation**: Integrated with **Swagger** for interactive API documentation.
   - **Metrics**: Collects metrics for monitoring the system's performance.

## Technologies Used

- **Java 23**
- **Spring Boot 3.3.5**: Provides the backbone for the application.
- **Spring Data JPA**: For managing transaction data with PostgreSQL.
- **PostgreSQL**: For transaction storage with optional full-text search using **tsvector**.
- **Spring Security**: Securing the API with configurable access.
- **Swagger/OpenAPI**: For API documentation.
- **Liquibase**: For managing database schema migrations.
- **Spring Cloud OpenFeign**: For connecting to remote EVM node APIs.
- **MapStruct**: For simplifying object mapping.
  
### Query Transactions

- `/api/v1/transactions/search`: Search stored transactions by parameters such as `blockHash`, `fromAddress`, `toAddress`, `hash`, etc.
  - **Optional**: Use `fullSearch` parameter for full-text search support across fields. (Do not forget to use the script which is not included in liquibase: src/main/resources/db/optional/transaction_full_text_support.sql)

### API Documentation

- `/swagger-ui/index.html`: Access interactive API documentation.

### Metrics

- `/actuator/metrics`: View service metrics.

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/blockchain-transaction-service.git
   
