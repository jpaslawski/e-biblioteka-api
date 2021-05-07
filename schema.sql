DROP TABLE IF EXISTS book__book_category;
DROP TABLE IF EXISTS book_category;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_contact;

create table book
(
    book_id       int auto_increment
        primary key,
    book_name     varchar(500)  not null,
    book_author   varchar(100)  not null,
    book_quantity int default 0 not null,
    book_image    varchar(200)  null
);

create table book__book_category
(
    book_id          int not null,
    book_category_id int not null,
    primary key (book_id, book_category_id)
);

create table book_category
(
    category_id   int auto_increment
        primary key,
    category_name varchar(30) not null
);

create table user
(
    user_id        int auto_increment
        primary key,
    user_firstName varchar(30)   not null,
    user_lastName  varchar(30)   not null,
    user_email     varchar(40)   not null,
    user_password  varchar(16)   not null,
    user_role      int default 0 null
);

create table reservation
(
    reservation_id     int auto_increment
        primary key,
    reservation_date   date          not null,
    reservation_status int default 0 not null,
    reservation_book   int           not null,
    reservation_user   int           not null,
    constraint reservation_book_fk
        foreign key (reservation_book) references book (book_id),
    constraint reservation_user_fk
        foreign key (reservation_user) references user (user_id)
);

create table user_contact
(
    contact_id          int auto_increment
        primary key,
    contact_address     varchar(40) not null,
    contact_city        varchar(30) not null,
    contact_zipCode     varchar(6)  not null,
    contact_phoneNumber mediumtext  not null,
    contact_owner       int         not null
);

INSERT INTO app_db.book (book_id, book_name, book_author, book_quantity, book_image) VALUES (1, 'Matematyka dyskretna', 'Kenneth A. Ross, Charles R. B. Wright', 3, 'https://media.merlin.pl/media/300x452/000/003/779/56ba5df566b01.jpg');
INSERT INTO app_db.book (book_id, book_name, book_author, book_quantity, book_image) VALUES (2, 'Spring w akcji', 'Craig Walls', 2, 'https://static01.helion.com.pl/global/okladki/326x466/sprwa4.jpg');
INSERT INTO app_db.book (book_id, book_name, book_author, book_quantity, book_image) VALUES (3, 'Spring w praktyce', 'Willie Wheeler, Joshua White', 2, 'https://static01.helion.com.pl/global/okladki/326x466/spripr.jpg');
INSERT INTO app_db.book (book_id, book_name, book_author, book_quantity, book_image) VALUES (4, 'React Native w akcji: twórz aplikacje na iOS i Android w JavaScripcie', 'Nader Dabit', 2, 'https://cf2-taniaksiazka.statiki.pl/images/large/556/9788301210243.jpg');
INSERT INTO app_db.book (book_id, book_name, book_author, book_quantity, book_image) VALUES (5, 'Matematyka dla studentów ekonomii, finansów i zarzadzania', 'Barbara Batóg', 2, 'https://ecsmedia.pl/c/matematyka-dla-studentow-ekonomii-finansow-i-zarzadzania-b-iext56891287.jpg');
INSERT INTO app_db.book (book_id, book_name, book_author, book_quantity, book_image) VALUES (6, 'C# 5.0 : leksykon kieszonkowy', 'Joseph Albahari, Ben Albahari', 2, 'https://cdn-lubimyczytac.pl/upload/books/4842000/4842582/651249-352x500.jpg');
INSERT INTO app_db.book (book_id, book_name, book_author, book_quantity, book_image) VALUES (7, 'C# 6.0 i MVC 5 : tworzenie nowoczesnych portali internetowych', 'Krzysztof Żydzik, Tomasz Rak', 1, 'https://static01.helion.com.pl/global/okladki/326x466/c6mvc5_3.png');
INSERT INTO app_db.book (book_id, book_name, book_author, book_quantity, book_image) VALUES (8, 'Programowanie w Ruby : od podstaw', 'Peter Cooper', 2, 'https://static01.helion.com.pl/global/okladki/326x466/prubpo.jpg');
INSERT INTO app_db.book (book_id, book_name, book_author, book_quantity, book_image) VALUES (9, 'Analiza finansowa przedsiębiorstwa : ocena sprawozdań finansowych, analiza wskaźnikowa', 'Franciszek Bławat', 3, 'https://s.znak.com.pl//files/covers/card/f1/T275478.jpg');
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (1, 1);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (1, 2);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (2, 3);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (2, 4);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (2, 5);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (3, 3);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (3, 4);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (3, 5);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (4, 3);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (4, 7);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (4, 8);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (4, 9);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (4, 10);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (5, 2);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (5, 11);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (5, 12);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (5, 13);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (6, 3);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (6, 14);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (7, 3);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (7, 14);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (8, 3);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (8, 15);
INSERT INTO app_db.book__book_category (book_id, book_category_id) VALUES (9, 12);
INSERT INTO app_db.book_category (category_id, category_name) VALUES (1, 'Matematyka dyskretna');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (2, 'Matematyka');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (3, 'Programowanie');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (4, 'Java');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (5, 'Spring');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (7, 'Android');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (8, 'React Native');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (9, 'iOS');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (10, 'JavaScript');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (11, 'Ekonomia');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (12, 'Finanse');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (13, 'Zarzadzanie');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (14, 'C#');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (15, 'Ruby');
INSERT INTO app_db.book_category (category_id, category_name) VALUES (16, 'Systemy operacyjne');
INSERT INTO app_db.user (user_id, user_firstName, user_lastName, user_email, user_password, user_role) VALUES (1, 'admin', 'admin', 'admin', 'admin', 2);
INSERT INTO app_db.user (user_id, user_firstName, user_lastName, user_email, user_password, user_role) VALUES (2, 'Marian', 'Kowalski', 'kowalski@gmail.com', 'kowalski', 0);
INSERT INTO app_db.user_contact (contact_id, contact_address, contact_city, contact_zipCode, contact_phoneNumber, contact_owner) VALUES (1, 'Długa 3', 'Warszawa', '22222', '123456789', 1);
INSERT INTO app_db.user_contact (contact_id, contact_address, contact_city, contact_zipCode, contact_phoneNumber, contact_owner) VALUES (2, 'Słoneczna 21', 'Warszawa', '22222', '444555666', 2);