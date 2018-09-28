insert into user(device_token,email,name,password,sex) values ("test","string","string","string",0);
insert into user(device_token,email,name,password,sex) values ("test","user1","user1","string",0);
insert into user(device_token,email,name,password,sex) values ("test","user2","user2","string",0);
insert into user(device_token,email,name,password,sex) values ("test","user3","user3","string",0);

insert into place(city,country,latitude,longtitude,price) values("서울","한국",0,0,0);
insert into place(city,country,latitude,longtitude,price) values("서울","한국",0,0,0);
insert into place(city,country,latitude,longtitude,price) values("인천","한국",0,0,0);
insert into place(city,country,latitude,longtitude,price) values("경기","한국",0,0,0);

INSERT INTO `book_ex`.`seq` (`title`, `usernum`) VALUES ('test', '1');
INSERT INTO `book_ex`.`seq` (`title`, `usernum`) VALUES ('test2', '2');
INSERT INTO `book_ex`.`seq` (`title`, `usernum`) VALUES ('test3', '3');
INSERT INTO `book_ex`.`seq` (`title`, `usernum`) VALUES ('test4', '4');

INSERT INTO review (created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES ('2018-09-28 20:30:15',1,1,1,1,1);
INSERT INTO review (created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES ('2018-09-28 20:30:15',1,2,2,1,2);
INSERT INTO review (created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES ('2018-09-28 20:30:15',1,3,3,1,3);
INSERT INTO review (created_date, mlrating, seqnum, usernum,view,thumbnum) VALUES ('2018-09-28 20:30:15',1,4,4,1,4);

INSERT INTO `book_ex`.`schedule` (`day`, `seqnum`, `placenum`) VALUES ('1', '1', '1');
INSERT INTO `book_ex`.`schedule` (`day`, `seqnum`, `placenum`) VALUES ('1', '1', '2');
INSERT INTO `book_ex`.`schedule` (`day`, `seqnum`, `placenum`) VALUES ('2', '1', '3');
INSERT INTO `book_ex`.`schedule` (`day`, `seqnum`, `placenum`) VALUES ('2', '1', '4');

INSERT INTO `book_ex`.`details` (`content`, `reviewnum`, `schedulenum`) VALUES ('좋았음1', '1', '1');
INSERT INTO `book_ex`.`details` (`content`, `reviewnum`, `schedulenum`) VALUES ('좋았음2', '1', '2');
INSERT INTO `book_ex`.`details` (`content`, `reviewnum`, `schedulenum`) VALUES ('좋았음3', '1', '3');
INSERT INTO `book_ex`.`details` (`content`, `reviewnum`, `schedulenum`) VALUES ('좋았음4', '1', '4');


INSERT INTO thumb (`bucket`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/2018/9/28/thumb/1538134248777-a.PNG');
INSERT INTO thumb (`bucket`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/2018/9/28/thumb/1538134248777-a.PNG');
INSERT INTO thumb (`bucket`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/2018/9/28/thumb/1538134248777-a.PNG');
INSERT INTO thumb (`bucket`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/2018/9/28/thumb/1538134248777-a.PNG');

INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/2018/9/28/thumb/1538134248777-a.PNG', '1');
INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/2018/9/28/thumb/1538134248777-a.PNG', '2');
INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/2018/9/28/thumb/1538134248777-a.PNG', '3');
INSERT INTO `book_ex`.`photo` (`bucket`, `detailsnum`) VALUES ('https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/2018/9/28/thumb/1538134248777-a.PNG', '4');
