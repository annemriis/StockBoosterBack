# Web application from team_02 - back end

## Team members
- Peeter Tarvas
- Annemari Riisimäe
- Kaisa-Mari Veinberg
- Markus Talvik

## How to run locally
- Make sure you have Java 11 or newer, Gradle, Docker and PostgreSQL
- cd to project root
- run Dockerfile and docker-compose.yml db
- run the application
- Wait approximately for 5 minutes for the database to initialise (text "Database has been initialised" will be displayed to the terminal)
- Navigate to `http://localhost:8080/api/stock/{symbol}`
