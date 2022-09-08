# ensf480-project

### Group
- Kameshwara Sekar
- Radu Schirliu
- Madhu Selvaraj
- Muskan Sarvesh

## Building
The project requires MySQL Connector 8.0.19 in order to build.

## Running
To run the project, run the `TicketReserverationSystem` class with no arguments.

To setup the database connection, the following environment variables need to be set: `ENSF_DB_URL, ENSF_DB_USER, ENSF_DB_PASSWORD`.  
The environment variables should look something along the lines of:
```shell
ENSF_DB_URL=jdbc:mysql://localhost/ensf_db
ENSF_DB_USER=root
ENSF_DB_PASSWORD=myPass
```

## Seeding
If the database is empty, running the program will seed it with some sample data, including
- Sample theatre
- Sample movies (current and future realease)
- Sample user (username: user, password: password)
- Sample seats
- Sample showtimes