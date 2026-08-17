# AppChat

<p align="center">
  <img src="assets/appchat-logo.png" alt="AppChat logo" width="128">
</p>

> A straightforward desktop chat app, inspired by WhatsApp, for staying in touch with your contacts and groups.

![Java](https://img.shields.io/badge/Java-21-blue)
![Maven](https://img.shields.io/badge/Maven-3-red)
![Swing](https://img.shields.io/badge/UI-Swing-orange)
![TDS](https://img.shields.io/badge/TDS-Persistence-purple)
![University of Murcia](https://img.shields.io/badge/University%20of%20Murcia-E03B23?style=flat&logo=graduation-cap&logoColor=white)

## About

AppChat is a desktop chat application inspired by popular apps like WhatsApp. Its purpose is to make it easy to stay in touch with the people around you: it brings your contacts and groups together in one place and lets you exchange messages simply and naturally. An optional Premium subscription adds a few extras for regular users.

## Demo

Login, open the main window (chats on the left, current chat on the right) and send messages.

<p align="center">
  <video src="https://github.com/user-attachments/assets/812f0e06-9e84-40ac-8831-4c17fb2d3a58"
    controls width="800"></video>
</p>

## Project structure

```
appchat/
├── .mvn/                      # Maven wrapper config
├── assets/                    # Image assets (logo, etc.)
├── docs/                      # Additional documentation
├── lib/                       # Local libraries installed into the Maven repository
├── src/                       # Source code
├── .gitignore                 # Files and folders ignored by Git
├── README.md                  # Main documentation
├── ServidorPersistenciaH2.jar # RMI persistence server
└── pom.xml                    # Maven build configuration
```

## Requirements

- **Java 21+** — check with `java --version`
- **Maven 3+** — check with `mvn --version`

## Installation

```bash
# Clone the repository
git clone https://github.com/ibracb/appchat.git
cd appchat
```

## Setting up dependencies

Two libraries must be installed into the local Maven repository before compiling. Run the following commands from the project root:

```bash
mvn install:install-file -Dfile=lib/chatWindowLib.jar -DgroupId=tds -DartifactId=chat-window -Dversion="1.0.0" -Dpackaging=jar -DgeneratePom=true
```

```bash
mvn install:install-file -Dfile=lib/DriverPersistencia.jar -DpomFile=lib/driverPersistencia-2.0.pom
```

## Starting the persistence server

AppChat needs an RMI persistence server for the database. In another terminal, run:

```bash
java -jar ServidorPersistenciaH2.jar
```

The server keeps running in the foreground. Leave it open while you use the application.

## Running

The application needs **two terminals open at the same time**:

1. **Terminal 1** — the persistence server (previous section), which must be left running.
2. **Terminal 2** — the application:

```bash
mvn compile exec:java -Dexec.mainClass="umu.tds.apps.app.AppChat"
```

## Test users

On startup, the application loads a set of example users, contacts, groups and messages:

|Name|Phone|Password|
|------|--------|----------|
|maria bm|212121212|m|
|ibra cb|313131313|i|
|maria bm2|121212121|m|
|ibra cb2|131313131|i|
|laura cc|171717171|l|
|maria jose tr|999999999|mj|
|alex ll|777777777|a|
|jorge sr|666666666|j|

## Documentation

This README covers installation and running. For the complete technical documentation, see [`docs/`](docs/):

- [User stories](docs/01-user-stories.md) — user stories and acceptance criteria.
- [Class diagram](docs/02-class-diagram.md) — the class diagram of the model.
- [Sequence diagram](docs/03-sequence-diagram.md) — the sequence diagram (flow of adding a contact to a group).
- [Architecture](docs/04-architecture.md) — the layered architecture and the package structure.
- [Design patterns](docs/05-design-patterns.md) — the design patterns used.

## Academic context

- **Subject:** Software Development Technologies
- **Degree:** BSc in Computer Engineering
- **University:** University of Murcia
- **Year:** 2024–2025

## Authors

- **Ibrahim Cherif Barry** - [ibracb](https://github.com/ibracb)
- **María Ballester Martínez** - [mariaballesteer](https://github.com/mariaballesteer)