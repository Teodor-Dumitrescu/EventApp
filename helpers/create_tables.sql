CREATE TABLE clients (
  client_id int PRIMARY KEY AUTO_INCREMENT,
  username varchar(255),
  password varchar(255),
  name varchar(255),
  age int,
  gender varchar(255),
  address_id int
);

CREATE TABLE organizers (
  organizer_id int PRIMARY KEY AUTO_INCREMENT,
  username varchar(255),
  password varchar(255),
  name varchar(255),
  address_id int
);

CREATE TABLE tickets (
  ticket_id int PRIMARY KEY AUTO_INCREMENT,
  client_id int,
  organizer_id int,
  event_id int,
  event_type int,
  ticket_identifier varchar(255),
  seat varchar(255),
  price float
);

CREATE TABLE music_events (
  event_id int PRIMARY KEY AUTO_INCREMENT,
  organizer_id int,
  address_id int,
  event_type int,
  name varchar(255),
  standard_price float,
  date varchar(255),
  available_tickets_number int,
  music_genre varchar(255),
  formation_name varchar(255)
);

CREATE TABLE sport_events (
  event_id int PRIMARY KEY AUTO_INCREMENT,
  organizer_id int,
  address_id int,
  event_type int,
  name varchar(255),
  standard_price float,
  date varchar(255),
  available_tickets_number int,
  home varchar(255),
  guest varchar(255)
);

CREATE TABLE cultural_events (
  event_id int PRIMARY KEY AUTO_INCREMENT,
  organizer_id int,
  address_id int,
  event_type int,
  name varchar(255),
  standard_price float,
  date varchar(255),
  available_tickets_number int,
  type varchar(255),
  guests varchar(255)
);

CREATE TABLE addresses (
  address_id int PRIMARY KEY AUTO_INCREMENT,
  country varchar(255),
  city varchar(255),
  street varchar(255),
  phone_number varchar(255)
);

ALTER TABLE clients ADD FOREIGN KEY (address_id) REFERENCES addresses (address_id);

ALTER TABLE organizers ADD FOREIGN KEY (address_id) REFERENCES addresses (address_id);

ALTER TABLE tickets ADD FOREIGN KEY (client_id) REFERENCES clients (client_id);

ALTER TABLE tickets ADD FOREIGN KEY (organizer_id) REFERENCES organizers (organizer_id);

ALTER TABLE music_events ADD FOREIGN KEY (organizer_id) REFERENCES organizers (organizer_id);

ALTER TABLE music_events ADD FOREIGN KEY (address_id) REFERENCES addresses (address_id);

ALTER TABLE sport_events ADD FOREIGN KEY (organizer_id) REFERENCES organizers (organizer_id);

ALTER TABLE sport_events ADD FOREIGN KEY (address_id) REFERENCES addresses (address_id);

ALTER TABLE cultural_events ADD FOREIGN KEY (organizer_id) REFERENCES organizers (organizer_id);

ALTER TABLE cultural_events ADD FOREIGN KEY (address_id) REFERENCES addresses (address_id);
