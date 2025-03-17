insert into User_table (id,name,email) values (11,'ibra','test@gmail.com');
insert into User_table (id,name,email) values (12,'ahmad','test@gmail.com');
insert into User_table (id,name,email) values (13,'ali','test@gmail.com');

insert into Interests (id,description) values(111,'code');
insert into Interests (id,description) values(112,'cooking');

INSERT INTO user_interests ( interest_id,user_id) VALUES (111, 11); 
INSERT INTO user_interests ( interest_id,user_id) VALUES (111, 12);
INSERT INTO user_interests ( interest_id,user_id) VALUES (112, 11); 
INSERT INTO user_interests ( interest_id,user_id) VALUES (112, 13); 
INSERT INTO user_interests ( interest_id,user_id) VALUES (112, 12); 

