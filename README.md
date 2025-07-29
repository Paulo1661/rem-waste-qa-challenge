# Rem Waste QA Challenge

This project contains automated E2E tests for a fullstack application (Node.js backend + React frontend), using **Serenity BDD** with Cucumber for UI and API testing.

---

## 🧪 Project Structure

```
rem-waste-qa-challenge/
├── backend/       → Node.js API (Express)
├── frontend/      → React application (Vite)
├── test/          → Serenity BDD automated tests
└── README.md      → This file
```

---

## 🚀 Running the Tests

### Prerequisites
- Java 21+
- Node.js 21.7+
- Maven
- Git
- The ports 3000 and 5173 are available

### Steps

```bash
# 1. Clone the project
git clone https://github.com/Paulo1661/rem-waste-qa-challenge.git
cd rem-waste-qa-challenge

# 2. Run script to install dependencies, start backend + frontend and run tests
chmod +x start.sh
./start.sh &




```

The Serenity report will be available at:  
`test/target/site/serenity/index.html`

---

## 🧾 About Serenity BDD

**[Serenity BDD](https://serenity-bdd.github.io/docs/tutorials/first_test)** is an open-source Java framework designed for writing readable and maintainable acceptance and E2E tests. It integrates:

-  **Cucumber** for Gherkin-style scenarios in plain English
-  **Rich reports** with screenshots and test step details
-  Built-in support for REST and UI testing
-  Integration with **JUnit** / **Maven**
- **Screenplay pattern** for readable automation scripts

It’s ideal for acceptance testing because it helps **tell the story** of what your tests are doing.

---

## 🛠️ Tools & Technologies

| Area             | Tool                      |
|------------------|---------------------------|
| E2E Testing      | Serenity BDD + Cucumber   |
| API Testing      | REST Assured via Serenity |
| UI Testing       | Selenium via Serenity     |
| Frontend         | React (Vite)              |
| Backend          | Node.js + Express         |
| CI               | GitHub Actions            |

---

## 📦 CI/CD (GitHub Actions)

A pipeline is provided to run tests on every `push`.  
The Serenity report is uploaded as an **artifact** under the Actions tab.

---

## 👤 Author

Paul Leufang  
Technical Challenge – REM Waste (July 2025)