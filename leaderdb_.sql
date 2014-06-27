# Host: 127.0.0.1:3307  (Version: 5.6.17)
# Date: 2014-06-25 13:35:13
# Generator: MySQL-Front 5.3  (Build 4.118)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "category"
#

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

#
# Data for table "category"
#

INSERT INTO `category` VALUES (1,'Мультимедия',NULL),(2,'Игры',NULL),(3,'Книги',NULL),(4,'Интернет магазин',NULL),(5,'Программы',NULL),(6,'Поиск работы',NULL),(7,'Покупка/продажа',NULL),(9,'Прогноз погоды',NULL),(10,'Клипы',12),(11,'Мкзыка',1),(12,'Фильмы',1),(13,'Сериалы',12);

#
# Structure for table "sites"
#

DROP TABLE IF EXISTS `sites`;
CREATE TABLE `sites` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT 'name',
  `url` varchar(255) NOT NULL DEFAULT '',
  `rating` float(3,2) NOT NULL DEFAULT '0.00',
  `votes` int(11) NOT NULL DEFAULT '0',
  `picture` varchar(255) NOT NULL DEFAULT '',
  `description` text,
  `short_description` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "sites"
#

INSERT INTO `sites` VALUES (1,'ex.ua','ex.ua',3.75,4,'','text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text  text text text text text text text text text text text text  text text te\ndsf\nas\ndf\nsa\ndf\nas\ndfa asdf asdf astext text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text  text text text text text text text text text text text text  text text tetext text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text  text text text text text text text text text text text text  text text tetext text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text  text text text text text text text text text text text text  text text tetext text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text  text text text text text text text text text text text text  text text te','description description description description description description description description description description description description description description description description description description description description description des'),(2,'brb.to','brb.to',0.00,0,'',NULL,''),(3,'vk.com','vk.com',1.00,0,'','asdfdsfgbdfsgb sokplg[sjfdgo ijdsofgp ds','asdfdsfgbdfsgb sokplg[sjfdgo ijdsofgp ds');

#
# Structure for table "category_site"
#

DROP TABLE IF EXISTS `category_site`;
CREATE TABLE `category_site` (
  `category_id` int(11) DEFAULT NULL,
  `site_id` int(11) DEFAULT NULL,
  KEY `category_id` (`category_id`),
  KEY `site_id` (`site_id`),
  CONSTRAINT `category_site_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`Id`),
  CONSTRAINT `category_site_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `sites` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "category_site"
#

INSERT INTO `category_site` VALUES (10,1),(11,1),(12,1),(13,1),(5,1),(3,1);

#
# Structure for table "users"
#

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `ip` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

#
# Data for table "users"
#

INSERT INTO `users` VALUES (32,'127.0.0.1'),(36,'192.168.1.105');

#
# Structure for table "user_site"
#

DROP TABLE IF EXISTS `user_site`;
CREATE TABLE `user_site` (
  `user_id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL,
  `score` int(1) DEFAULT '0',
  UNIQUE KEY `site_id` (`site_id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_site_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`Id`),
  CONSTRAINT `user_site_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `sites` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user_site"
#

INSERT INTO `user_site` VALUES (32,1,3);

#
# Function "hello"
#

DROP FUNCTION IF EXISTS `hello`;
/*insert ignore INTO user_site (user_id,site_id, score) VALUES (32,1,2.5);
update sites set sites.rating=9 where sites.id=1;
select sites.rating from sites where sites.id = 1;*/

/*SELECT 
       IF((select COUNT(*) from user_site where user_site.site_id=1 and user_site.user_id=32)>0,
       select sites.rating from sites where sites.id=1,
       'false');*/
       
       
/*CREATE PROCEDURE simpleproc (OUT param1 INT)
 BEGIN
 SELECT COUNT(*) INTO param1 FROM sites;
END;
select simpleproc(@a);*/


/*CREATE FUNCTION hello (_siteID INT, _userID INT, _score double) RETURNS double
begin
     DECLARE _votes INT;
     DECLARE siteRating double;
     
     select sites.rating, sites.votes into siteRating, _votes from sites where sites.id=_siteID;
     
      IF (select COUNT(*) from user_site where user_site.site_id=_siteID and user_site.user_id=_userID)<1 then
           insert ignore INTO user_site (user_id,site_id, score) VALUES (_userID,_siteID,_score);   
           update sites set sites.rating=(siteRating*_votes+siteRating)/(_votes+1) where sites.id=_siteID;           
      end if;

     RETURN siteRating;
end;*/


CREATE FUNCTION hello (_siteID INT, _userID INT, _score double) RETURNS double deterministic
begin
     DECLARE _votes INT;
     DECLARE siteRating double default 1.0;
     

     SELECT sites.rating, sites.votes INTO siteRating, _votes FROM sites WHERE id = _siteID;
     
     IF (select COUNT(*) from user_site where user_site.site_id=_siteID and user_site.user_id=_userID)<1 then
           insert ignore INTO user_site (user_id,site_id, score) VALUES (_userID,_siteID,_score);   
           set siteRating=(siteRating*_votes+_score)/(_votes+1);
           update sites set sites.rating=siteRating, sites.votes=_votes+1 where sites.id=_siteID;           
      end if;
     
     RETURN siteRating;
end;


SELECT hello(1,32,3.0);

/*
 SELECT somevalue INTO myvar FROM mytable WHERE uid=1;
  SELECT myvar;

*/
