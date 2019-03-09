DROP TABLE IF EXISTS board_permissions;
DROP TABLE IF EXISTS room_permissions;
DROP TABLE IF EXISTS event_tags;
DROP TABLE IF EXISTS event_;
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS doctor;
DROP TABLE IF EXISTS patient;

CREATE TABLE IF NOT EXISTS patient (
	_id INTEGER(11) NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	email VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS doctor (
	_id INTEGER(11) NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE,
	title VARCHAR(50),
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	email VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS room (
	_id INTEGER(11) NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE,
	title VARCHAR(128) NOT NULL,
	doctor INTEGER(11) NOT NULL,
	patient INTEGER(11) NOT NULL,
	activity INTEGER(11) NOT NULL,
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	INDEX ix_patient (patient),
	FOREIGN KEY (patient) REFERENCES patient(_id)
);

CREATE TABLE IF NOT EXISTS board (
	tag VARCHAR(50) NOT NULL PRIMARY KEY UNIQUE
);

CREATE TABLE IF NOT EXISTS event_ (
	_id INTEGER(11) NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE,
	doctor INTEGER(11) NOT NULL,
	room INTEGER(11) NOT NULL,
	contents TEXT NOT NULL,
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	tags VARCHAR(128),
	INDEX ix_room (room),
	FOREIGN KEY (room) REFERENCES room(_id),
	FOREIGN KEY (doctor) REFERENCES doctor(_id)
);

CREATE TABLE IF NOT EXISTS event_tags (
	event_ INTEGER(11) NOT NULL,
	tag VARCHAR(50) NOT NULL,
	PRIMARY KEY (event_, tag),
	INDEX ix_tag (tag),
	FOREIGN KEY (event_) REFERENCES event_(_id),
	FOREIGN KEY (tag) REFERENCES board(tag)
);

CREATE TABLE IF NOT EXISTS room_permissions (
	doctor INTEGER(11) NOT NULL,
	room INTEGER(11) NOT NULL,
	PRIMARY KEY (doctor, room),
	FOREIGN KEY (doctor) REFERENCES doctor(_id),
	FOREIGN KEY (room) REFERENCES room(_id)
);

CREATE TABLE IF NOT EXISTS board_permissions (
  patient INTEGER(11) NOT NULL,
	doctor INTEGER(11) NOT NULL,
	board VARCHAR(50) NOT NULL,
	PRIMARY KEY (patient, doctor, board),
	FOREIGN KEY (patient) REFERENCES patient(_id),
	FOREIGN KEY (doctor) REFERENCES doctor(_id),
	FOREIGN KEY (board) REFERENCES board(tag)
);

CREATE TABLE IF NOT EXISTS room_requests (
	doctor INTEGER(11) NOT NULL,
	room INTEGER(11) NOT NULL,
	PRIMARY KEY (doctor, room),
	FOREIGN KEY (doctor) REFERENCES doctor(_id),
	FOREIGN KEY (room) REFERENCES room(_id)
);

INSERT INTO patient (`_id`, firstname, lastname, email) VALUES
(1, "Samuel", "Sledzieski", "samuel.sledzieski@uconn.edu"),
(2, "Killian", "Greene", "killian.greene@uconn.edu");

INSERT INTO doctor (`_id`, title, firstname, lastname, email) VALUES
(1, "GP", "David", "Mills", "david.mills@uconn.edu"),
(2, "GP", "Laurent", "Michel", "laurent.michel@uconn.edu");

INSERT INTO room (`_id`, title, doctor, patient, activity, create_time) VALUES
(1, "Earache", 1, 1, 0, CURRENT_TIMESTAMP),
(2, "Toothache", 2, 1, 1, CURRENT_TIMESTAMP),
(3, "Footache", 1, 2, 1, CURRENT_TIMESTAMP),
(4, "Headache", 2, 2, 2, CURRENT_TIMESTAMP);

INSERT INTO board (tag) VALUES
("medications"),
("procedures"),
("conditions");

INSERT INTO room_permissions (doctor, room) VALUES
(1, 1),
(1, 3),
(2, 2),
(2, 4);

INSERT INTO board_permissions (patient, doctor, board) VALUES
(1, 1, "medications"),
(1, 1, "procedures"),
(1, 1, "conditions"),
(2, 2, "medications"),
(2, 2, "procedures"),
(2, 2, "conditions");

INSERT INTO event_ (`_id`, doctor, room, contents, create_time, tags) VALUES
(1, 1, 1, "Patient complaining of ear pain", CURRENT_TIMESTAMP, ""),
(2, 1, 1, "Patient prescribed cocaine", CURRENT_TIMESTAMP, "medications"),
(3, 2, 2, "Patient complaining of tooth pain", CURRENT_TIMESTAMP, ""),
(4, 2, 2, "Patient has tooth surgery", CURRENT_TIMESTAMP, "procedures"),
(5, 1, 3, "Patient complaining of foot pain", CURRENT_TIMESTAMP, ""),
(6, 1, 3, "Patient had foot amputated", CURRENT_TIMESTAMP, "procedures"),
(7, 2, 4, "Patient complaining of headaches", CURRENT_TIMESTAMP, ""),
(8, 2, 4, "Patient prescribed cyproheptadine", CURRENT_TIMESTAMP, "medications");

INSERT INTO event_tags (event_, tag) VALUES
(2, "medications"),
(4, "procedures"),
(6, "procedures"),
(8, "medications");