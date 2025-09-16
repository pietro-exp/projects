# Bulletin Board System

**Author:** Pietro Mezzatesta  
**Date:** September 2025  

---

## Introduction
The **Bulletin Board System** is a Java application designed using **Object-Oriented Programming (OOP)** principles and the **Model-View-Controller (MVC)** architecture.  
It allows users to publish, search, and manage purchase and sale announcements via both **CLI (Command Line Interface)** and **GUI (Swing)**.

OOP principles applied:
- **Encapsulation:** protects internal data via private fields and controlled public methods.  
- **Inheritance:** `PurchaseAnnouncement` and `SaleAnnouncement` extend the abstract `Announcement` class.  
- **Polymorphism:** subclasses can be treated as `Announcement` objects in generic collections.  
- **Abstraction:** general concepts are modeled in abstract classes, with specialized behavior in subclasses.

---

## Project Architecture
The project is structured into packages for clear responsibility separation and maintainability:

- `domain`: core model classes (`Announcement`, `PurchaseAnnouncement`, `SaleAnnouncement`, `BulletinBoard`, `User`).  
- `exceptions`: custom exceptions for domain-specific errors.  
- `persistence`: handles data saving/loading, authentication, and validations.  
- `interface`: user interfaces (CLI and GUI).  
- `test`: unit tests using JUnit 5.  

This structure follows the **Separation of Concerns**, isolating business logic from the user interface and persistence.

---

## Technologies Used

- **Java 17+**: primary programming language.  
- **Swing**: for graphical user interface (GUI) with CardLayout and modular panels.  
- **CLI**: text-based menu interface.  
- **Java Serialization (Serializable)**: for object persistence (BulletinBoard, User, Announcement).  
- **JUnit 5**: unit testing framework.  
- **SHA-256 hashing with salt**: secure password storage.  
- **UUID**: unique identifiers for announcements.  

These technologies are leveraged consistently with the MVC architecture: the **model** manages data and logic, the **view** (CLI/GUI) handles interaction, and the **persistence** layer ensures saving and loading data.

---

## Core Classes

### Announcement (abstract)
- Unique ID (UUID), author, name, price, keywords  
- Subclasses:  
  - **PurchaseAnnouncement:** no expiration, `isExpired()` always returns false  
  - **SaleAnnouncement:** includes `expirationDate` with validity checks  

### BulletinBoard
- Manages users and announcements  
- Operations: register, authenticate, add/modify/remove announcements, keyword search, remove expired sales  

### User
- Unique email, first name, last name, secure password (hash + salt)  
- Method `checkPassword()` for authentication  

---

## Exception Handling
Custom exceptions for domain errors:
- `AnnouncementNotFoundException`  
- `UserNotFoundException`  
- `UserNotAuthorizedException`  
- `UserAlreadyRegisteredException`  
- `InvalidExpirationDateException`  

These provide clear and controlled error reporting.

---

## Data Persistence
- **Java Serialization** to save `BulletinBoard` and `User` objects  
- Main files: `bulletinboard.ser` and `users.ser`  
- Managed through `BulletinBoardPersistence` with static `save/load` methods  

---

## User Interfaces

### CLI (BulletinBoardCLI)
- Text menu for complete announcement management  
- Validated input and direct interaction with `BulletinBoard` logic  

### GUI (BulletinBoardGUI)
- Swing window with CardLayout  
- Separate panels for add, search, edit, remove announcements  
- Buttons and input fields with event listeners  
- Exception handling via `JOptionPane`  
- Saves data on exit  

---

## Testing
- **JUnit 5**: extensive unit tests for `PurchaseAnnouncement`, `SaleAnnouncement`, `User`, `BulletinBoard`, `BulletinBoardPersistence`  
- Validates inputs, exceptions, searches, and persistence  
- Follows a **TDD approach** for reliability and full coverage  

---

## Future Enhancements
Potential system improvements:
- **SQL database** for scalable persistence (MySQL, SQLite)  
- **Web or mobile interface** via REST API  
- **User roles and permissions** (e.g., administrators)  
- **Notifications and alerts** for expirations or new matching announcements  
- **Advanced search** with filters, full-text search, and sorting  
- **Responsive and enhanced GUI** with images and dynamic layout  
- **Internationalization** (multi-language support)  
- **Logging and configuration** for monitoring and runtime parameters  

Thanks to MVC and OOP design, these extensions can be implemented without altering the core business logic.

---

## Documentation
The project is fully documented using:
- **Javadoc** for all classes and public methods  
- Clear inline comments for critical methods  
- Technical report detailing architecture, model, interfaces, exceptions, persistence, and testing  
- Practical examples in CLI/GUI interfaces  

