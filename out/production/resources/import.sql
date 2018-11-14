-- user
 insert into user(user_num,device_token,email,name,password,sex,url) values (1,"test","string","string","string",0,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default.png');
 insert into user(user_num,device_token,email,name,password,sex,url) values (2,"test","user1","user1","string",0,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default.png');
 insert into user(user_num,device_token,email,name,password,sex,url) values (3,"test","user2","user2","string",0,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default.png');
 insert into user(user_num,device_token,email,name,password,sex,url) values (4,"test2","user3","user3","string",0,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default.png');

-- seq
 INSERT INTO `book_ex`.`seq` (`seqnum`,`title`, `usernum`) VALUES (1,'TestPlace', '1');
 INSERT INTO `book_ex`.`seq` (`seqnum`,`title`, `usernum`) VALUES (2,'test2', '2');
 INSERT INTO `book_ex`.`seq` (`seqnum`,`title`, `usernum`) VALUES (3,'test3', '3');
 INSERT INTO `book_ex`.`seq` (`seqnum`,`title`, `usernum`) VALUES (4,'test4', '4');
 INSERT INTO `book_ex`.`seq` (`seqnum`,`title`, `usernum`) VALUES (5,'test1-1', '1');

-- day
 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (1,'1', '1');
 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (2,'2', '1');
 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (3,'3', '1');
 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (4,'4', '1');

 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (5,'1', '2');
 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (6,'2', '2');
 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (7,'3', '2');

 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (8,'1', '3');
 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (9,'2', '3');

 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (10,'1', '4');
 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (11,'2', '4');

 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (12,'1', '5');
 INSERT INTO `book_ex`.`day` (`daynum`,`day`, `seqnum`) VALUES (13,'2', '5');

-- schedule
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`, `placenum`) VALUES (1,'1', '1');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`, `placenum`) VALUES (2,'1', '2');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`, `placenum`) VALUES (3,'2', '3');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`, `placenum`) VALUES (4,'2', '4');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`, `placenum`) VALUES (5,'3', '5');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`, `placenum`) VALUES (6,'3', '6');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`, `placenum`) VALUES (7,'3', '7');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`, `placenum`) VALUES (8,'4', '8');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`, `placenum`) VALUES (9,'4', '9');

 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (10,'5',  '9');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (11,'5',  '7');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (12,'6',  '6');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (13,'7',  '5');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (14,'7',  '3');

 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (15,'8',  '2');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (16,'8',  '3');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (17,'8',  '4');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (18,'9',  '5');

 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (19,'10',  '2');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (20,'11',  '3');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (21,'11',  '4');

 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (22,'12',  '10');
 INSERT INTO `book_ex`.`schedule` (`schedulenum`,`daynum`,  `placenum`) VALUES (23,'13',  '11');


--thumb
 INSERT INTO `book_ex`.`thumb` (`thumbnum`, `bucket`) VALUES (1,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png');
 INSERT INTO `book_ex`.`thumb` (`thumbnum`, `bucket`) VALUES (2,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/2018/10/8/test1.jpg');
 INSERT INTO `book_ex`.`thumb` (`thumbnum`, `bucket`) VALUES (3,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/2018/10/8/test2.jpg');

-- review
 INSERT INTO review (`reviewnum`,created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES (1,'2018-09-28 20:30:15',1,1,1,1,1);
 INSERT INTO review (`reviewnum`,created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES (2,'2018-09-28 20:30:15',1,2,2,2,1);
 INSERT INTO review (`reviewnum`,created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES (3,'2018-09-28 20:30:15',1,3,3,3,1);
 INSERT INTO review (`reviewnum`,created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES (4,'2018-09-28 20:30:15',1,4,4,4,1);
 INSERT INTO review (`reviewnum`,created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES (5,'2018-09-28 20:30:15',1,5,1,5,1);

-- details
 INSERT INTO `book_ex`.`details` (`detailsnum`,`content`, `reviewnum`, `schedulenum`) VALUES (1,'좋았음1-1', '1', '1');
 INSERT INTO `book_ex`.`details` (`detailsnum`,`content`, `reviewnum`, `schedulenum`) VALUES (2,'좋았음1-2', '1', '2');
 INSERT INTO `book_ex`.`details` (`detailsnum`,`content`, `reviewnum`, `schedulenum`) VALUES (3,'좋았음1-3', '1', '3');
 INSERT INTO `book_ex`.`details` (`detailsnum`,`content`, `reviewnum`, `schedulenum`) VALUES (4,'좋았음1-4', '1', '5');
 INSERT INTO `book_ex`.`details` (`detailsnum`,`content`, `reviewnum`, `schedulenum`) VALUES (5,'좋았음1-5', '1', '8');

 INSERT INTO `book_ex`.`details` (`detailsnum`,`content`, `reviewnum`, `schedulenum`) VALUES (6,'좋았음2-1', '2', '11');
 INSERT INTO `book_ex`.`details` (`detailsnum`,`content`, `reviewnum`, `schedulenum`) VALUES (7,'좋았음2-2', '2', '14');

 INSERT INTO `book_ex`.`details` (`detailsnum`,`content`, `reviewnum`, `schedulenum`) VALUES (8,'좋았음3-1', '3', '15');

 INSERT INTO `book_ex`.`details` (`detailsnum`,`content`, `reviewnum`, `schedulenum`) VALUES (9,'좋았음5-1', '5', '22');
 INSERT INTO `book_ex`.`details` (`detailsnum`,`content`, `reviewnum`, `schedulenum`) VALUES (10,'좋았음5-2', '5', '23');


-- photo
INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png', '1');
INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png', '2');
INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png', '3');
INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png', '4');


INSERT INTO `book_ex`.`place_thumb` (`thumbnum`,`bucket`) VALUES ('0','https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png');

