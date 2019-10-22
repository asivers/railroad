CREATE DATABASE railways DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

USE railways;

CREATE TABLE `railways`.`stations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `station_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `railways`.`trains` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `railways`.`passengers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `second_name` VARCHAR(45) NOT NULL,
  `birth_date` DATE NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `railways`.`stations_trains` (
  `station_id` INT NOT NULL,
  `train_id` INT NOT NULL,
  `time` TIME NOT NULL,
  PRIMARY KEY (`station_id`, `train_id`));

CREATE TABLE `railways`.`tickets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `train_id` INT NOT NULL,
  `passenger_id` INT NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `railways`.`stations_trains`
ADD CONSTRAINT `fk_stations_trains_station_id`
  FOREIGN KEY (`station_id`)
  REFERENCES `railways`.`stations` (`id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

ALTER TABLE `railways`.`stations_trains`
ADD CONSTRAINT `fk_stations_trains_train_id`
  FOREIGN KEY (`train_id`)
  REFERENCES `railways`.`trains` (`id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

ALTER TABLE `railways`.`tickets`
ADD CONSTRAINT `fk_tickets_train_id`
  FOREIGN KEY (`train_id`)
  REFERENCES `railways`.`trains` (`id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

ALTER TABLE `railways`.`tickets`
ADD CONSTRAINT `fk_tickets_passenger_id`
  FOREIGN KEY (`passenger_id`)
  REFERENCES `railways`.`passengers` (`id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Spb-Balt');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Bronevaya');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Leninskiy');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Dachnoe');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Ulyanka');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Ligovo');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Sosnovaya Polyana');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Sergievo');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Strelna');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Krasnye Zori');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Novy Peterhof');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Stary Peterhof');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Devyatkino');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Lavriki');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Kapitolovo');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Kuzmolovo');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Toksovo');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Kavgolovo');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Oselki');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Gorelovo');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Skachki');
INSERT INTO `railways`.`stations` (`station_name`) VALUES ('Krasnoe selo');

INSERT INTO `railways`.`trains` (`number`) VALUES ('123456');
INSERT INTO `railways`.`trains` (`number`) VALUES ('234567');
INSERT INTO `railways`.`trains` (`number`) VALUES ('345678');
INSERT INTO `railways`.`trains` (`number`) VALUES ('456789');
INSERT INTO `railways`.`trains` (`number`) VALUES ('567890');
INSERT INTO `railways`.`trains` (`number`) VALUES ('987650');
INSERT INTO `railways`.`trains` (`number`) VALUES ('987654');
INSERT INTO `railways`.`trains` (`number`) VALUES ('876543');
INSERT INTO `railways`.`trains` (`number`) VALUES ('765432');
INSERT INTO `railways`.`trains` (`number`) VALUES ('654321');

INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('1', '1', '12:00');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('2', '1', '12:03');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('3', '1', '12:06');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('4', '1', '12:09');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('5', '1', '12:12');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('6', '1', '12:15');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('7', '1', '12:18');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('8', '1', '12:21');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('9', '1', '12:24');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('10', '1', '12:27');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('11', '1', '12:30');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('12', '1', '12:33');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('12', '2', '12:50');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('11', '2', '12:53');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('9', '2', '12:58');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('8', '2', '13:01');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('7', '2', '13:04');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('6', '2', '13:07');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('5', '2', '13:10');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('4', '2', '13:13');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('3', '2', '13:16');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('2', '2', '13:19');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('1', '2', '13:21');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('1', '3', '14:00');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('2', '3', '14:03');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('3', '3', '14:06');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('4', '3', '14:09');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('5', '3', '14:12');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('6', '3', '14:15');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('7', '3', '14:18');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('8', '3', '14:21');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('9', '3', '14:24');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('11', '3', '14:30');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('12', '3', '14:33');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('12', '4', '14:50');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('11', '4', '14:53');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('10', '4', '14:55');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('9', '4', '14:58');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('8', '4', '15:01');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('7', '4', '15:04');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('6', '4', '15:07');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('5', '4', '15:10');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('4', '4', '15:13');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('3', '4', '15:16');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('2', '4', '15:19');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('1', '4', '15:21');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('1', '5', '16:00');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('2', '5', '16:03');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('3', '5', '16:06');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('4', '5', '16:09');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('5', '5', '16:12');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('6', '5', '16:15');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('20', '5', '16:18');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('21', '5', '16:21');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('22', '5', '16:24');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('22', '6', '16:50');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('21', '6', '16:53');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('20', '6', '16:56');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('6', '6', '16:59');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('5', '6', '17:02');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('4', '6', '17:05');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('3', '6', '17:08');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('2', '6', '17:11');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('1', '6', '17:14');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('1', '7', '18:00');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('2', '7', '18:03');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('3', '7', '18:06');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('4', '7', '18:09');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('5', '7', '18:12');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('6', '7', '18:15');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('7', '7', '18:18');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('8', '7', '18:21');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('9', '7', '18:24');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('10', '7', '18:27');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('11', '7', '18:30');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('12', '7', '18:33');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('13', '8', '19:00');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('14', '8', '19:03');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('15', '8', '19:06');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('16', '8', '19:09');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('17', '8', '19:12');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('18', '8', '19:15');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('19', '8', '19:18');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('13', '9', '20:00');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('14', '9', '20:03');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('15', '9', '20:06');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('16', '9', '20:09');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('17', '9', '20:12');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('18', '9', '20:15');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('19', '9', '20:18');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('19', '10', '21:00');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('18', '10', '21:03');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('17', '10', '21:06');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('16', '10', '21:09');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('15', '10', '21:12');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('14', '10', '21:15');
INSERT INTO `railways`.`stations_trains` (`station_id`, `train_id`, `time`) VALUES ('13', '10', '21:18');

INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Harry', 'Potter', '1981-01-01');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Tyrion', 'Lannister', '1970-02-02');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Frodo', 'Baggins', '1982-03-03');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Tony', 'Montana', '1964-04-04');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('John', 'Watson', '1959-05-05');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Rick', 'Sanchez', '1949-06-06');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Eric', 'Cartman', '1996-07-07');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Peter', 'Parker', '1989-08-08');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Anakin', 'Skywalker', '1970-09-09');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Michael', 'Corleone', '1962-10-10');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Sarah', 'Connor', '1970-11-11');
INSERT INTO `railways`.`passengers` (`first_name`, `second_name`, `birth_date`) VALUES ('Jack', 'Sparrow', '1977-12-12');

INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('1', '1');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('1', '2');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('1', '3');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('2', '4');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('3', '5');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('5', '6');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('6', '7');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('7', '8');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('8', '9');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('8', '10');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('10', '11');
INSERT INTO `railways`.`tickets` (`train_id`, `passenger_id`) VALUES ('10', '1');