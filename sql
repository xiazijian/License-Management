数据库名：License

REATE TABLE `license_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `license` varchar(700) DEFAULT NULL,
  `configuration` varchar(700) DEFAULT NULL,
  `isEffect` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci 



CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci



----+----------+----------+-------+
| id | name     | password | level |
+----+----------+----------+-------+
|  1 | 4pdadmin | admin    |     0 |
|  2 | xwy      | 123456   |     1 |

level是0为管理员,为1是普通用户
