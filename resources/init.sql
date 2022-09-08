CREATE TABLE IF NOT EXISTS user (
    id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    cardNumber VARCHAR(255) NOT NULL,
    pin INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS theatre (
    name VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY,
    location VARCHAR(255) NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS movie (
    movieName VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY,
    releaseDate DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS theatre_movie (
    theatre VARCHAR(255) NOT NULL,
    movieName VARCHAR(255) NOT NULL,
    PRIMARY KEY (theatre, movieName),
    FOREIGN KEY (theatre) REFERENCES theatre(name),
    FOREIGN KEY (movieName) REFERENCES movie(movieName)
);

CREATE TABLE IF NOT EXISTS showtime (
    showtime VARCHAR(255) NOT NULL,
    movieName VARCHAR(255) NOT NULL,
    theatre VARCHAR(255) NOT NULL,
    showDate DATE NOT NULL,
    seats INT NOT NULL,
    PRIMARY KEY (showtime, movieName, theatre, showDate),
    FOREIGN KEY (movieName) REFERENCES movie(movieName),
    FOREIGN KEY (theatre) REFERENCES theatre(name),
    INDEX (showDate)
);

CREATE TABLE IF NOT EXISTS seat (
    seatNumber INT NOT NULL,
	showtime VARCHAR(255) NOT NULL,
	occupied BOOLEAN,
    priority BOOLEAN,
    PRIMARY KEY (seatNumber, showtime)
);

CREATE TABLE IF NOT EXISTS news (
    movieName VARCHAR(255) NOT NULL PRIMARY KEY,
    message VARCHAR(255) NOT NULL,
    FOREIGN KEY (movieName) REFERENCES movie(movieName)
);

CREATE TABLE IF NOT EXISTS coupon (
    code INT NOT NULL UNIQUE PRIMARY KEY,
    amount INT NOT NULL,
    expiration DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket (
    ticketId INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
    seatNumber INT NOT NULL,
    movieName VARCHAR(255) NOT NULL,
    theatre VARCHAR(255) NOT NULL,
    showtime VARCHAR(255) NOT NULL,
	showDate DATE NOT NULL,
    priority BOOLEAN,
    FOREIGN KEY (seatNumber) REFERENCES seat(seatNumber),
    FOREIGN KEY (movieName) REFERENCES showtime(movieName),
    FOREIGN KEY (theatre) REFERENCES showtime(theatre),
    FOREIGN KEY (showtime) REFERENCES showtime(showtime),
	FOREIGN KEY (showDate) REFERENCES showtime(showDate)
);

CREATE TABLE IF NOT EXISTS transaction (
    transactionId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    cardNumber VARCHAR(255) NOT NULL,
    pin INT NOT NULL
);

CREATE TABLE IF NOT EXISTS confirmation (
    email VARCHAR(255) NOT NULL,
    transactionId INT NOT NULL,
    message VARCHAR(255),
    PRIMARY KEY (email, transactionId),
    FOREIGN KEY (transactionId) REFERENCES transaction(transactionId)
);

CREATE TABLE IF NOT EXISTS cancellation (
    ticketId INT NOT NULL PRIMARY KEY
);