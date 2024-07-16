
DROP TABLE IF EXISTS portal1;
create database portal1;
use portal1;

CREATE TABLE `portal_user` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `type` enum('SELLER','USER','ADMIN') DEFAULT NULL,
   `create_date` timestamp NULL DEFAULT NULL,
   `username` varchar(45) NOT NULL,
   `password` varchar(255) NOT NULL,
   `profile_id` bigint NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `username_UNIQUE` (`username`),
   KEY `fk_portal_user_profile_idx` (`profile_id`),
   CONSTRAINT `fk_portal_user_profile` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


CREATE TABLE `profile` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `name` varchar(100) DEFAULT NULL,
   `dob` date DEFAULT NULL,
   `phone` varchar(45) DEFAULT NULL,
   `email` varchar(255) DEFAULT NULL,
   `address` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product_catalog` (
  `product_id` bigint NOT NULL AUTO_INCREMENT,
  `seller_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text,
  `price` decimal(10,2) NOT NULL,
  `stock_quantity` int NOT NULL,
  `category` varchar(100),
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `image_file_name` VARCHAR(255), -- This is the newly added column
  PRIMARY KEY (`product_id`),
  CONSTRAINT `fk_product_seller` FOREIGN KEY (`seller_id`) REFERENCES `portal_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `shopping_cart` (
  `cart_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cart_id`),
  CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `portal_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cart_items` (
  `item_id` bigint NOT NULL AUTO_INCREMENT,
  `cart_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `add_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`item_id`),
  CONSTRAINT `fk_item_cart` FOREIGN KEY (`cart_id`) REFERENCES `shopping_cart` (`cart_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_item_product` FOREIGN KEY (`product_id`) REFERENCES `product_catalog` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE product_catalog
ADD COLUMN image_file_name VARCHAR(255);

ALTER TABLE product_catalog
DROP COLUMN imagefilename;

