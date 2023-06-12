# Scheduling Application
This application allows you to manage users, customers, and appointment schedules.

## Dependencies
- JavaFX: Graphics and media packages that allow faster development time and create a cross-platform desktop application.

- JDBC: Java Database Connectivity API was used to connect to a MySQL database instance, increase performance through PreparedStatements, and improve security through SQL query sanitization.

## Development
This application follows the MVC (Model-View-Controller) design pattern for clarity and ease of development.

- The `DAO` package contains all Data Access Objects used for CRUD operations on the database.
- The `Utils` package contains utility classes for database connections and date/time parsing.
- The `controller` package includes all controller classes for managing user interactions and data flow into model objects.
- The `model` package includes all the application's business logic and object models.
- The `view` package includes all JavaFX FXML files for the application’s UI.

## License
This project is licensed under MIT License terms.
