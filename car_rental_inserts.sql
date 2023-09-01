CREATE DATABASE car_rental;
USE car_rental;

INSERT INTO address
VALUES
(id,'Poland','Warsaw','Horbaczewskiego 1, m. 17','03-001'),
(id,'Germany','Berlin','Unter den Linden Straße 22','04-001'),
(id,'France','Paris','Rue Dubois 3/5','05-001'),
(id,'USA','New York','Wall Street','02-001'),
(id,'Turkey','Istanbul','Aksaray 44','01-001'),
(id,'Poland','Warsaw','Dolna 1, m. 17','09-001'),
(id,'Germany','Berlin','Frankfurter Straße 22','06-001'),
(id,'France','Paris','Rue Polonaise 3/5','07-001'),
(id,'USA','New York','5th Avenue','03-001'),
(id,'Turkey','Istanbul','Topkapi','02-001');

INSERT INTO customer
VALUES
(1,id,'Marek','Turadek','m.t@gmail.com','+48555666777'),
(2,id,'Aga','Berger','a.b.@gmail.com','+36333666777'),
(3,id,'Alan','Nowak','m.t@gmail.com','+24212666777'),
(4,id,'Michał','Fojk','m.t@gmail.com','+41115666777'),
(5,id,'Albert','Einstein','m.t@gmail.com','+38789666777');

INSERT INTO rental
VALUES
(6,id,'CarRentalAAMM','AgaAlanMarekMichał','http://carrentalaamm.pl');

INSERT INTO branch
VALUES
(6,id,1),
(7,id,1),
(8,id,1),
(9,id,1),
(10,id,1);

INSERT INTO car
VALUES
(12004,300,2022,1,id,'suv','Toyota','red','CHR','available'),
(2344,350,2023,2,id,'hatchback','Ford','green','Escort','unavailable'),
(16789,350,2022,3,id,'combi','Skoda','blue','Octavia','available'),
(100562,270,2020,4,id,'van','Citroen','red','Berlingo','unavailable'),
(114908,250,2019,5,id,'suv','Ford','blue','Puma','available');

INSERT INTO employee
VALUES
(5,id,'Marek','manager','Zegarek'),
(2,id,'Aga','employee','Łamaga'),
(4,id,'Alan','manager','Nalany'),
(3,id,'Michał','employee','Wzdychał'),
(1,id,'Albert','employee','Wychodzimy');

INSERT INTO reservation
VALUES
('2022-07-15','2000',1,1,'2022-07-25',id,2,1,'2022-07-20','completed'),
('2022-08-14','3000',2,1,'2022-08-30',id,1,3,'2022-08-22','completed'),
('2023-07-30','1500',3,1,'2023-08-07',id,2,2,'2023-08-04','completed'),
('2023-06-22','1700',3,1,'2023-07-02',id,4,4,'2023-06-23','completed'),
('2023-05-28','2200',5,1,'2023-07-12',id,4,5,'2023-07-11','completed');

INSERT INTO car_rent
VALUES
('2022-07-20',1,id,1,'aaa'),
('2022-08-22',2,id,2,'bbb'),
('2023-08-04',3,id,3,'ccc'),
('2023-06-23',4,id,4,'ddd'),
('2023-07-11',5,id,5,'eee');

INSERT INTO car_return
VALUES
('2022-07-25','100',id,1,'aaa'),
('2022-08-30',0,id,2,'bbb'),
('2023-08-09','450',id,3,'ccc'),
('2023-07-02',0,id,4,'ddd'),
('2023-07-12',0,id,5,'eee');





