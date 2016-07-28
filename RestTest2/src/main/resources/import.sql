INSERT INTO parkings (Id, Name, Tariff, Cars_limit) VALUES (1,'Парковка №1', 100.00, 30);
INSERT INTO cars (Id, Number, Model) VALUES (1, 'A235AB90', 'UAZ');
INSERT INTO carsonparkings (Id, Car_id, Parking_id, Beg_time, End_time) VALUES(1,1,1,CURRENT_TIMESTAMP(),NULL);
