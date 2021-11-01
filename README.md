# Web application from team_02 - back end

## Team members
- Peeter Tarvas
- Annemari Riisimäe
- Kaisa-Mari Veinberg
- Markus Talvik

## How to run locally
- Make sure you have Java 11 or newer, Gradle, Docker and PostgreSQL
- cd to project root
- run: `docker-compose up -d db`
- run: `./gradlew bootRun` (this will take some time because the db has to be created) <- this will run the app
- Wait approximately for 5 minutes for the database to initialise (text "Database has been initialised" will be displayed to the terminal)
- Navigate to `http://localhost:8080/api/stock/{symbol}`

## How to run only with docker

  - cd to project root
    - run: `./gradlew build -x test` (without tests because tests will take a long time to complete)
      - run: `docker image build -t bootdocker:staging .`
        - We usually  run docker-compose.yml file container graphically but you can use:
          - `docker-compose up -d` : for starting all containers

## How to run tests
- Run `docker-compose up -d db`
- Select test module and run tests with coverage
- Because we are using an external API and tests have to run on different threads and database has to be initilised, running test takes time (approximately 5 minutes to run all tests).

### Second part


## Install docker and docker-compose
- install docker engine with this guide 'https://docs.docker.com/engine/install/ubuntu/'
- get docker compose with this 'https://docs.docker.com/compose/install/'


## Connect to server
- run: `ssh ubuntu@13.48.85.253`

## Gitlab runner
- Use class guide `https://olegpahhomov.gitlab.io/guides/production/gitlab_runner/`
- for <> use amd64
- replace heroes with stocks
- add `image: gradle:jdk11` tot the top of the `.gitlab-ci.yml` file
- build stock should inc
- 

## Install java
- run `sudo apt update && apt upgrade`
- run `sudo apt install default-jre && apt install default-jdk`
- verify that java is installed `javac -version` output should be `javac 11.0.11`

## Backend service
- cd /etc/systemd/system/
- sudo touch stocks.service
  - copy following text to stocks.service 
  '[Unit]
    Description=stocks service
    After=network.target

    [Service]
    Type=simple
    User=gitlab-runner
    WorkingDirectory=/home/gitlab-runner/api-deployment
    ExecStart=/usr/bin/java -jar ito0302-2021-back-end-0.0.1.jar
    Restart=on-abort

    [Install]
    WantedBy=multi-user.target'

## service must be restarted
- sudo service stocks restart
