# Minecraft Community Management System

A project for organizing and analyzing data from a Minecraft community using **MySQL, Java, Spring Boot, and C#**.

The idea came from a pretty practical problem: a Minecraft server generates a lot of information through plugins and logs, but this data is usually spread across different files and SQLite databases. This project brings that information together into a relational database, making it easier to query, analyze, and manage the community.

---

## 🎯 What does it do?

The system collects data from a **Paper Minecraft server** and organizes information such as:

* Registered players
* Player sessions and access history
* Violations detected by GrimAC
* Anti-cheat check types
* OP accounts
* Moderation-related information

The data is then made available through a REST API, which is consumed by a desktop application for viewing the results.

The main idea is simple: **turn scattered server data into something that can actually be queried and analyzed.**

---

## 🧩 Project Structure

```text
SQLite (AuthMe + GrimAC)
          │
          │ ETL Java/JDBC
          ▼
       MySQL
          │
          │ Spring Boot
          ▼
       REST API
          │
          │ HTTP
          ▼
   GrimDashboard
      C# / WinForms
```

### Technologies

* **MySQL** — main database
* **SQLite** — source databases used by the plugins
* **Java + JDBC** — data import / ETL
* **Spring Boot** — REST API
* **C# + WinForms** — desktop dashboard
* **Maven** — backend build and dependency management

---

## 📂 Where does the data come from?

The data used by the project comes directly from the server files.

| Source                              | Data used                                         |
| ----------------------------------- | ------------------------------------------------- |
| `plugins/AuthMe/authme.db`          | Players and registration dates                    |
| `plugins/GrimAC/data/history.v1.db` | Sessions, violations, checks, and tracked players |
| `banned-players.json`               | Manual bans                                       |
| `banned-ips.json`                   | IP bans                                           |
| `ops.json`                          | OP accounts                                       |

`whitelist.json` is not part of the project because the server does not use a whitelist.

---

## 🔄 ETL

The data is not simply copied from one database to another.

The project has a **Java/JDBC ETL process** that reads the SQLite databases used by the plugins, selects the relevant information, and inserts it into the MySQL database according to the project's own schema.

Main file:

```text
fica/ImportarDados.java
```

---

## 🗄️ Database

The main database schema is located at:

```text
database/SchermaComunidadeMinecraft.sql
```

The structure was designed around the main entities of the project:

* Players
* Sessions
* Violations
* Check types
* Relationships between these entities

The goal is to avoid depending directly on the internal structure of the plugins and instead have a database designed specifically for analysis.

---

## 📊 Current Results

After importing the real server data:

| Table               | Records |
| ------------------- | ------: |
| `tipos_verificacao` |      74 |
| `jogadores`         |       7 |
| `sessoes`           |      67 |
| `violacoes`         |   3,769 |

One of the most interesting results came from analyzing violations by player.

One player who was already suspected by the server administration showed a significant number of violations. At the same time, another player showed a relevant pattern that had not previously attracted the administration's attention.

In other words, putting the data into a structured and queryable format made it possible to notice something that was much harder to identify from the original data sources.

The timeline analysis also revealed sequences of violations of the same type occurring within individual sessions. This does **not** automatically mean that the player was using cheats. Network issues, anti-cheat behavior, or false positives can also produce these patterns.

---

## 🔐 Privacy

Since the project uses real data from a Minecraft server, some information was intentionally excluded.

* Players are identified only by **nickname**.
* Password hashes are not imported.
* IP addresses are not imported.
* Email addresses are not imported.
* The data is used for analyzing the community and the system.

Only the information necessary for the project is kept in the research database.

---

## ⚠️ Limitations

It's important not to draw conclusions that go beyond what the data supports.

Currently, the project is based on **a single server** and a relatively small number of players.

Some limitations include:

* Only 7 registered players were present in the analyzed period.
* No manual bans were recorded during the analyzed period.
* AuthMe does not provide the complete session history, so session data mainly comes from GrimAC.
* A violation detected by an anti-cheat does not necessarily mean that a player was actually cheating.

Because of this, the results are mainly useful for showing how structuring server data can improve community analysis, rather than claiming that the same patterns apply to Minecraft communities in general.

---

## 🚀 How to Run

### Requirements

* Java 17+
* Maven
* MySQL Server
* .NET 8 SDK
* Visual Studio (for the WinForms application)

### 1. Create the database

Run:

```text
database/SchermaComunidadeMinecraft.sql
```

using MySQL Workbench or another MySQL client.

### 2. Import the data

Configure the paths to the SQLite databases in:

```text
fica/ImportarDados.java
```

Then run the following command inside the `fica` directory:

```bash
mvn clean compile exec:java
```

### 3. Configure the API

Edit:

```text
backend/src/main/resources/application.properties
```

and configure the MySQL connection.

### 4. Start the backend

Inside the `backend` directory:

```bash
mvn clean package -DskipTests
```

Then:

```bash
java -jar target/comunidade-minecraft-api-1.0.0.jar
```

### 5. Run the dashboard

Open:

```text
GrimDashboard
```

in Visual Studio.

With the API running, select a report from the ComboBox and click **Load**.

---

## 📁 Project Structure

```text
.
├── backend/                 # Spring Boot REST API
├── database/                # MySQL database scripts
├── fica/                    # ETL / data import
└── GrimDashboard/           # C# WinForms dashboard
```

---

## 📚 References

* ELMASRI, R.; NAVATHE, S. B. *Fundamentals of Database Systems*. Pearson.
* SILBERSCHATZ, A.; KORTH, H. F.; SUDARSHAN, S. *Database System Concepts*. McGraw-Hill.
* RUNESON, P.; HÖST, M. *Guidelines for conducting and reporting case study research in software engineering*. Empirical Software Engineering, 2009. DOI: 10.1007/s10664-008-9102-8.
* TELLIS, W. M. *Application of a Case Study Methodology*. The Qualitative Report, 1997. DOI: 10.46743/2160-3715/1997.2015.
* TANG, D. et al. *Modeling the Data Provenance of Relational Databases Supporting Full-Featured SQL and Procedural Languages*. Applied Sciences, 2023. DOI: 10.3390/app13010064.

