# Gestion d'Argent

Application de gestion budgétaire personnelle : suivi de comptes, transactions, et génération de rapports PDF. Projet d'apprentissage pour appliquer SOLID, Clean Code, l'architecture Hexagonale et le DDD, avec une montée en complexité progressive (tests, Docker, CI/CD, Kubernetes).

## Stack technique

- **Backend** : Spring Boot (Java)
- **Base de données** : MongoDB
- **Frontend** : LitJS / PolymerJS
- **Génération PDF** : OpenPDF (ou iText)

## Architecture

Le projet suit une **architecture Hexagonale (Ports & Adapters)** :

- **`domain/`** — Logique métier pure. Aucune dépendance à Spring, MongoDB ou HTTP.
    - Entités : `Account`, `Transaction`
    - Services métier (use cases)
    - **`domain/port/in/`** — Interfaces des cas d'usage (ce que l'appli permet de faire)
    - **`domain/port/out/`** — Interfaces des besoins techniques (persistance, génération de rapport...)
- **`adapter/in/`** — Points d'entrée : contrôleurs REST
- **`adapter/out/`** — Implémentations techniques : repository MongoDB, générateur PDF

Principes appliqués :
- **SOLID** sur chaque classe (voir `docs/solid.md` si besoin de rappel)
- **DIP** (Dependency Inversion) : le domaine dépend d'abstractions (Ports), jamais de détails techniques
- **DDD** : vocabulaire métier explicite (`Account`, `Transaction`, pas `AccountEntity` générique)

## Fonctionnalités (v1)

- [ ] Création de compte
- [ ] Enregistrement d'une transaction (dépense / revenu)
- [ ] Calcul du solde
- [ ] Catégorisation des dépenses
- [ ] Génération d'un rapport mensuel en PDF

## Structure de dossiers

```
src/main/java/com/example/gestionargent/
├── domain
│   ├── Account.java
│   ├── Transaction.java
│   ├── AccountService.java
│   └── port
│       ├── in
│       │   ├── RecordTransactionUseCase.java
│       │   └── GenerateMonthlyReportUseCase.java
│       └── out
│           ├── AccountRepository.java
│           ├── TransactionRepository.java
│           └── ReportGenerator.java
└── adapter
    ├── in
    │   └── web
    │       ├── TransactionController.java
    │       └── ReportController.java
    └── out
        ├── persistence
        │   ├── MongoAccountRepository.java
        │   └── MongoTransactionRepository.java
        └── report
            └── PdfReportGenerator.java
```

## Roadmap d'évolution

| Phase | Contenu |
|---|---|
| 1 | SOLID + Hexagonal + Clean Code (fondations) |
| 2 | Tests unitaires (domaine) + tests d'intégration (Testcontainers) |
| 3 | Docker + docker-compose (API + MongoDB) |
| 4 | CI/CD (GitHub Actions) |
| 5 | Observabilité (Spring Actuator, Prometheus, Grafana) |
| 6 | Kubernetes (déploiement des conteneurs) |

**Pistes d'évolution supplémentaires** (à prioriser plus tard) :
- DDD tactique avancé (Aggregates, Value Objects, Domain Events)
- CQRS (séparation lecture / écriture)
- Event-driven (RabbitMQ / Kafka)
- Sécurité (Spring Security + JWT)
- Frontend LitJS consommant l'API
- Documentation API (OpenAPI / Swagger)

## Lancer le projet

```bash
# À compléter au fur et à mesure du projet
```

### Frontend (LitJS + Vite)

Les sources sont dans `frontend/` ; le build est généré dans `src/main/resources/static/` et servi par Spring Boot sur http://localhost:8080.

```bash
cd frontend
npm install
npm run build
```

## Notes de conception

- Le domaine ne doit jamais importer une classe de `adapter/` ou une annotation Spring liée à l'infrastructure (`@Repository`, `MongoTemplate`, etc.).
- Un changement de technologie (ex : MongoDB → PostgreSQL) ne doit impacter que le dossier `adapter/out/persistence/`, jamais `domain/`.