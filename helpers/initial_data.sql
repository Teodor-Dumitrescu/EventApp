
/*date admin*/
INSERT INTO addresses (country, city, street, phone_number) VALUES ('country_admin','city_admin','street_admin','phone_Admin');

INSERT INTO clients (username, password, name, age, gender, address_id)
         VALUES ('client_admin','123','Client admin',25,'Male',1);

INSERT INTO organizers (username, password, name, address_id)
     VALUES ('org_admin','123','Organizator admin',1);


INSERT INTO music_events (organizer_id, address_id, event_type, name, standard_price, date, available_tickets_number, music_genre, formation_name)
     VALUES (1,1,1,'event_admin',0,'0-0-0000',1,'','');


INSERT INTO tickets (client_id, organizer_id, event_id, event_type, ticket_identifier, seat, price) 
    VALUES (1,1,1,1,'admin_ticket','',0);



/*clienti*/
INSERT INTO addresses (country, city, street, phone_number) VALUES ('Romania','Bucuresti','Unirii','0741256');
INSERT INTO addresses (country, city, street, phone_number) VALUES ('Romania','Brasov','Central','07412582');

INSERT INTO clients (username, password, name, age, gender, address_id)
         VALUES ('client_a','123','John Doe',25,'Male',3);

INSERT INTO clients (username, password, name, age, gender, address_id)
        VALUES ('client_b','123','Jack Reacher',25,'Male',2); 



/*organizatori*/
INSERT INTO addresses (country, city, street, phone_number) VALUES ('Romania','Timisoara','Splaiul Nou','152648');
INSERT INTO addresses (country, city, street, phone_number) VALUES ('Romania','Craiova','Cantacuzino','8695215');

INSERT INTO organizers (username, password, name, address_id)
     VALUES ('org_a','123','Roton Music',4);

INSERT INTO organizers (username, password, name, address_id)
     VALUES ('org_b','123','Art Leader',5);




/*evenimente culturale*/
INSERT INTO addresses (country, city, street, phone_number) VALUES ('Anglia','Londra','London Square','86582');
INSERT INTO addresses (country, city, street, phone_number) VALUES ('Germania','Munchen','Heist Bahn','89625');

INSERT INTO cultural_events (organizer_id, address_id, event_type, name, standard_price, date, available_tickets_number, type, guests)
     VALUES (2,6,3,'Lacul lebedelor',155.34,'12-5-2021',10,'Teatru','Teatrul din Moscova');

INSERT INTO cultural_events (organizer_id, address_id, event_type, name, standard_price, date, available_tickets_number, type, guests)
     VALUES (3,7,3,'O srisoare Pierduta',95.34,'5-9-2020',100,'Teatru','Teatrul din Bucuresti');



/*evenimente musicale*/
INSERT INTO addresses (country, city, street, phone_number) VALUES ('Italia','Milano','Bieno Venturi','262365');
INSERT INTO addresses (country, city, street, phone_number) VALUES ('SUA','New York','Piccolino','15426');

INSERT INTO music_events (organizer_id, address_id, event_type, name, standard_price, date, available_tickets_number, music_genre, formation_name)
    VALUES (2,8,1,'Summer Festival',50.65,'10-6-2020',5,'chill','Bona Ventura');

INSERT INTO music_events (organizer_id, address_id, event_type, name, standard_price, date, available_tickets_number, music_genre, formation_name)
    VALUES (3,9,1,'Winter Festival',55.75,'10-12-2020',3,'rock','Hard Heat');



/*evenimente sportive*/
INSERT INTO addresses (country, city, street, phone_number) VALUES ('Germania','Munchen','Heist Bahn','89625');
INSERT INTO addresses (country, city, street, phone_number) VALUES ('Italia','Milan','Biena tuti','521368');


INSERT INTO sport_events (organizer_id, address_id, event_type, name, standard_price, date, available_tickets_number, home, guest)
    VALUES (3,10,2,'Bundesliga',124.05,'10-10-2020',2,'Bayern','Borrusia');

INSERT INTO sport_events (organizer_id, address_id, event_type, name, standard_price, date, available_tickets_number, home, guest)
    VALUES (3,11,2,'Serie A',104.05,'10-12-2020',6,'Milan','Juventus');



/*bilete*/
INSERT INTO tickets (client_id, organizer_id, event_id, event_type, ticket_identifier, seat, price) 
    VALUES (2,3,2,2,'SPO-MI-JUV-07','A-45',104.05);

INSERT INTO tickets (client_id, organizer_id, event_id, event_type, ticket_identifier, seat, price) 
    VALUES (2,2,2,1,'BON-VEN','BV-42',50.65);

INSERT INTO tickets (client_id, organizer_id, event_id, event_type, ticket_identifier, seat, price) 
    VALUES (3,2,2,3,'LA-LEB','X-12',155.34);

INSERT INTO tickets (client_id, organizer_id, event_id, event_type, ticket_identifier, seat, price) 
    VALUES (3,2,3,1,'WIN-FES','XY-05',55.75);

INSERT INTO tickets (client_id, organizer_id, event_id, event_type, ticket_identifier, seat, price) 
    VALUES (3,3,2,2,'SPO-MI-JUV-09','B-32',104.05);