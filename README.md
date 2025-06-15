# PG.Global 🚀

**Enterprise-Grade Microservices-Based Payment Gateway**

PG.Global is a scalable, extensible, and secure microservices-based **Payment Gateway platform** built using **Java Spring Boot**. It supports all major payment modes including **Card**, **UPI**, and **Net Banking**, and includes full transaction lifecycle handling: from initiation and fraud detection to settlement and refunds.

---

## 🔧 Tech Stack

* **Java 17 / Spring Boot 3+**
* **Spring Cloud** (OpenFeign, Eureka, Config)
* **MongoDB / PostgreSQL**
* **Kafka** (for event-driven architecture – optional)
* **Docker + Docker Compose**
* **Lombok**, **MapStruct**, **MongoDb** 

---

## 📦 Microservices Architecture

```
PG.Global/
│
├── merchant-service         # Manages merchant registration, KYC, API keys
├── orchestrator-service     # Entry point; routes payments to correct services
├── card-payment-service     # Handles card payment flow
├── upi-service              # Handles UPI transactions
├── netbanking-service       # Handles net banking transactions
├── transaction-service      # Stores and manages all payment transactions
├── settlement-service       # Processes successful transactions for settlement
├── refund-service           # Handles partial/full refunds
├── fraud-service            # Detects fraudulent transaction patterns
├── gateway-service          # (Optional) API Gateway using Spring Cloud Gateway
└── config-service           # (Optional) Centralized config for all services
```

---

## 🌐 Supported Features

* ✅ Merchant onboarding and KYC
* ✅ Secure API key-based validation
* ✅ Payment routing via orchestrator
* ✅ Support for Card, UPI, Net Banking
* ✅ Centralized transaction tracking
* ✅ Fraud detection engine with pluggable rules
* ✅ Refund processing (full and partial)
* ✅ Merchant settlement engine
* ✅ REST APIs for all operations
* ✅ Postman collection available

---

## 🚀 Getting Started (Local Dev)

### 1. Clone the repo

```bash
git clone https://github.com/your-org/PG.Global.git
cd PG.Global
```

### 2. Start MongoDB or PostgreSQL via Docker (optional)

```bash
docker-compose up -d
```

### 3. Run services (from each module)

```bash
cd merchant-service
./mvnw spring-boot:run
```

(Repeat for orchestrator, transaction, payment-mode services...)

---

## 📫 API Docs (Postman)

A complete Postman collection is available under .

---

## 🔐 Security


---

## 🏁 Roadmap

* [ ] Add Kafka eventing between services (Orchestrator → Transaction → Settlement)
* [ ] Rate-limiting per merchant
* [ ] Dashboard for merchant analytics


---

## 🤝 Contributing

Contributions are welcome! Please raise an issue or feature request before submitting PRs.


