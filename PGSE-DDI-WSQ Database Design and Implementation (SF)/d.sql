drop database if exists portal;
CREATE database portal;
use portal;

CREATE TABLE IF NOT EXISTS `portal`.`profile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `dob` DATE NULL,
  `phone` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `portal`.`portal_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` ENUM('SELLER', 'CONSUMER', 'ADMIN') NULL,
  `create_date` TIMESTAMP NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `profile_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  INDEX `fk_portal_user_profile_idx` (`profile_id` ASC) VISIBLE,
  CONSTRAINT `fk_portal_user_profile`
    FOREIGN KEY (`profile_id`)
    REFERENCES `portal`.`profile` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE IF NOT EXISTS `portal`.`catalog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `portal`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `price` VARCHAR(45) NULL,
  `description` TEXT NULL,
  `origin` VARCHAR(45) NULL,
  `colors` VARCHAR(55) NULL,
  PRIMARY KEY (`id`));


CREATE TABLE IF NOT EXISTS `portal`.`cart` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `portal_user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cart_portal_user1_idx` (`portal_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_portal_user1`
    FOREIGN KEY (`portal_user_id`)
    REFERENCES `portal`.`portal_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE IF NOT EXISTS `portal`.`potential_client` (
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`));


CREATE TABLE IF NOT EXISTS `portal`.`order_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` INT NULL,
  `note` TEXT NULL,
  `cart_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_item_cart1_idx` (`cart_id` ASC) VISIBLE,
  INDEX `fk_order_item_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_item_cart1`
    FOREIGN KEY (`cart_id`)
    REFERENCES `portal`.`cart` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `mydb`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE IF NOT EXISTS `portal`.`product_catalog` (
  `product_id` INT NOT NULL,
  `catalog_id` INT NOT NULL,
  PRIMARY KEY (`product_id`, `catalog_id`),
  INDEX `fk_product_has_catalog_catalog1_idx` (`catalog_id` ASC) VISIBLE,
  INDEX `fk_product_has_catalog_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_has_catalog_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `portal`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_catalog_catalog1`
    FOREIGN KEY (`catalog_id`)
    REFERENCES `portal`.`catalog` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO profile(`name`) values('test');
INSERT INTO portal_user (`type`, username, `password`, profile_id) VALUES ('ADMIN', 'admin', '123', 1);

UPDATE `profile` SET `name` = 'Tran Van A', phone='039193910', email='demo@gmail.com' where id = 1; 
select * from portal_user;
INSERT INTO catalog(name) values ('Thoi trang'), ('Giay dep');
INSERT INTO product(name, price, description, origin, colors) 
VALUES('Giay nike air force 1', 300000, 'GIay dep', 'VIETNAM', 'WHITE, BLACK');

UPDATE product set price=90000, origin='CHINA' where id = 1;

INSERT INTO cart(portal_user_id) values(2);
INSERT INTO order_item(amount, note, cart_id, product_id) values(3, 'None', 2, 2);

INSERT INTO portal_user (`type`, username, `password`, profile_id) VALUES ('ADMIN', 'admin', '123', 1);

CREATE USER 'user1'@'localhost' IDENTIFIED BY '123';
GRANT SELECT ON portal.* TO 'user1'@'localhost';

CREATE USER 'user2'@'localhost' IDENTIFIED BY '123';
GRANT ALL ON portal.* TO 'user2'@'localhost';

CREATE INDEX profile_email_idx on profile(email);
CREATE INDEX profile_phone_idx on profile(phone);
CREATE INDEX profile_dob_idx on profile(dob);


CREATE INDEX portaluser_username_idx on portal_user(username);

CREATE INDEX product_price_idx on product(price);
INSERT INTO portal_user(type, username, password, profile_id) values('SELLER','u1', '1', 1), ('SELLER', 'u3','1', 3), ('SELLER', 'u2','1', 2);