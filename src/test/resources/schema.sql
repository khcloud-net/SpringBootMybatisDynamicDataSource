
CREATE DATABASE IF NOT EXISTS redis_cache_test CHARACTER SET utf8;
use redis_cache_test;
CREATE TABLE `products` (
                            `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                            `name` varchar(50) DEFAULT NULL,
                            `price` bigint(11) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE DATABASE IF NOT EXISTS redis_cache_test_bak CHARACTER SET utf8;
use redis_cache_test_bak;
CREATE TABLE `products` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `price` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


