insert into user(device_token,email,name,password,sex,url) values ("test","string","string","string",0,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default.png');
insert into user(device_token,email,name,password,sex,url) values ("test","user1","user1","string",0,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default.png');
insert into user(device_token,email,name,password,sex,url) values ("test","user2","user2","string",0,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default.png');


INSERT INTO `book_ex`.`seq` (`title`, `usernum`) VALUES ('test', '1');
INSERT INTO `book_ex`.`seq` (`title`, `usernum`) VALUES ('test2', '2');
INSERT INTO `book_ex`.`seq` (`title`, `usernum`) VALUES ('test3', '3');


INSERT INTO `book_ex`.`thumb` (`thumbnum`, `bucket`) VALUES (1,'https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png');


 INSERT INTO `book_ex`.`schedule` (`daynum`, `placenum`) VALUES ('1', '3');
 INSERT INTO `book_ex`.`schedule` (`daynum`, `placenum`) VALUES ('1', '4');
 INSERT INTO `book_ex`.`schedule` (`daynum`, `placenum`) VALUES ('2', '5');
 INSERT INTO `book_ex`.`schedule` (`daynum`, `placenum`) VALUES ('2', '6');

 INSERT INTO `book_ex`.`schedule` (`daynum`,  `placenum`) VALUES ('1',  '4');
 INSERT INTO `book_ex`.`schedule` (`daynum`,  `placenum`) VALUES ('1',  '5');
 INSERT INTO `book_ex`.`schedule` (`daynum`,  `placenum`) VALUES ('2',  '6');
 INSERT INTO `book_ex`.`schedule` (`daynum`,  `placenum`) VALUES ('2',  '7');

 INSERT INTO `book_ex`.`schedule` (`daynum`,  `placenum`) VALUES ('1',  '6');
 INSERT INTO `book_ex`.`schedule` (`daynum`,  `placenum`) VALUES ('1',  '7');
 INSERT INTO `book_ex`.`schedule` (`daynum`,  `placenum`) VALUES ('2',  '8');
 INSERT INTO `book_ex`.`schedule` (`daynum`,  `placenum`) VALUES ('2',  '9');


 INSERT INTO review (created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES ('2018-09-28 20:30:15',1,1,1,1,1);
 INSERT INTO review (created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES ('2018-09-28 20:30:15',1,2,2,0,1);


 INSERT INTO `book_ex`.`details` (`content`, `reviewnum`, `schedulenum`) VALUES ('좋았음1', '1', '1');
 INSERT INTO `book_ex`.`details` (`content`, `reviewnum`, `schedulenum`) VALUES ('좋았음2', '1', '2');
 INSERT INTO `book_ex`.`details` (`content`, `reviewnum`, `schedulenum`) VALUES ('좋았음3', '1', '3');
 INSERT INTO `book_ex`.`details` (`content`, `reviewnum`, `schedulenum`) VALUES ('좋았음4', '2', '5');


INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png', '1');
INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png', '2');
INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png', '3');
INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png', '4');


INSERT INTO `book_ex`.`place_thumb` (`bucket`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default2.png');
