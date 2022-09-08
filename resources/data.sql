INSERT INTO user (name, email, username, password, cardNumber, pin)
VALUES ("Test User", "test@user.com", "user", "password", "12345678", "12345");

INSERT INTO theatre (name, location, capacity) VALUES ("Cineplex", "Calgary", 100);

INSERT INTO movie (movieName, releaseDate) VALUES ("Avengers", "2020-11-25");
INSERT INTO movie (movieName, releaseDate) VALUES ("Mean Girls", "1978-3-4");

INSERT INTO movie (movieName, releaseDate) VALUES ("Spider Man", "2021-05-03");
INSERT INTO news (movieName, message) VALUES ("Spider Man", "Spider man will be released in a few months!!!");

INSERT INTO theatre_movie (theatre, movieName) VALUES ("Cineplex", "Avengers");
INSERT INTO theatre_movie (theatre, movieName) VALUES ("Cineplex", "Spider Man");
INSERT INTO theatre_movie (theatre, movieName) VALUES ("Cineplex", "Mean Girls");

INSERT INTO showtime (showtime, movieName, theatre, showDate, seats) VALUES ("6:00 AM", "Mean Girls", "Cineplex", "2020-12-28", 10);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (1, "6:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (2, "6:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (3, "6:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (4, "6:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (5, "6:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (6, "6:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (7, "6:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (8, "6:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (9, "6:00 AM", true, true);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (10, "6:00 AM", true, true);

INSERT INTO showtime (showtime, movieName, theatre, showDate, seats) VALUES ("9:00 AM", "Avengers", "Cineplex", "2020-12-28", 10);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (1, "9:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (2, "9:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (3, "9:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (4, "9:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (5, "9:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (6, "9:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (7, "9:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (8, "9:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (9, "9:00 AM", false, true);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (10, "9:00 AM", false, true);

INSERT INTO showtime (showtime, movieName, theatre, showDate, seats) VALUES ("11:00 AM", "Avengers", "Cineplex", "2020-12-28", 10);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (1, "11:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (2, "11:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (3, "11:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (4, "11:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (5, "11:00 AM", true, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (6, "11:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (7, "11:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (8, "11:00 AM", false, false);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (9, "11:00 AM", false, true);
INSERT INTO seat (seatNumber, showTime, occupied, priority) VALUES (10, "11:00 AM", false, true);

