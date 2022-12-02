DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS ( 
    userId          varchar(12)		NOT NULL,
	password		varchar(12)		NOT NULL,
	name			varchar(20)		NOT NULL,
	email			varchar(50),	
  	
	PRIMARY KEY               (userId)
);

INSERT INTO USERS VALUES('admin', 'password', '자바지기', 'admin@slipp.net');

DROP TABLE IF EXISTS QUESTION;

CREATE TABLE QUESTION (
    id 			bigint				auto_increment,
    writer				varchar(30)			NOT NULL,
    title				varchar(50)			NOT NULL,
    contents			varchar(5000)		NOT NULL,
    answerCount         bigint              NOT NULL DEFAULT 0,
    createdAt			timestamp			NOT NULL,
    updatedAt			timestamp			NOT NULL,

    PRIMARY KEY               (id)
);

DROP TABLE IF EXISTS ANSWER;

CREATE TABLE ANSWER (
     id 			bigint				auto_increment,
     writer				varchar(30)			NOT NULL,
     contents			varchar(5000)		NOT NULL,
     createdAt			timestamp			NOT NULL,
     updatedAt			timestamp			NOT NULL,
     questionId			bigint				NOT NULL,

     PRIMARY KEY         (id)
);