# PG.Global 🚀

**Enterprise-Grade Microservices-Based Payment Gateway**

PG.Global is a scalable, extensible, and secure microservices-based **Payment Gateway platform** built using **Java Spring Boot**. It supports all major payment modes including **Card**, **UPI**, and **Net Banking**, and includes full transaction lifecycle handling: from initiation and fraud detection to settlement and refunds.

---

## 🔧 Tech Stack

* **Java 17 / Spring Boot 3+**
* **Spring Cloud** (OpenFeign, Eureka, Config)
* **MongoDB**
* **Kafka** (for event-driven architecture)
* **Docker + Kubernetes**
* **Lombok**, **MapStruct**, **MongoDb** 

---

## 📦 Microservices Architecture

```
PG.Global/
│
├── PG-Microservices/             # Core payment microservices
│   ├── merchant-service          # Handles merchant onboarding, KYC, and API key management
│   ├── orchestrator-service      # Acts as an entry point and routes payment requests
│   ├── card-payment-service      # Processes card-based payments (credit/debit)
│   ├── upi-service               # Handles UPI payment transactions
│   ├── netbanking-service        # Manages net banking payment flow
│   ├── transaction-service       # Records, tracks, and queries all transactions
│   ├── settlement-service        # Performs fund settlements post successful payments
│   ├── refund-service            # Manages full and partial refunds
│   ├── fraud-service             # Detects and flags potential fraudulent transactions
│   ├── gateway-service           # [Optional] Spring Cloud Gateway for centralized routing
│   └── config-service            # [Optional] Centralized configuration using Spring Cloud Config
│
└── devops/                       # DevOps-related files (Docker, Kubernetes, CI/CD)
    ├── Dockerfile                # Dockerfile file for create docker image
    ├── k8s/                      # Kubernetes manifests (Deployments, Services, Ingress)
    ├── Jenkinsfile / GitHub Actions # CI/CD pipeline definitions
    └── helm/                     # Helm charts for deployment packaging

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
git@github.com:NomadicSatyam/PG.Global.git

cd PG.Global
```

### 2. Start Kafka , MongoDB or PostgreSQL via Docker (optional)

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


